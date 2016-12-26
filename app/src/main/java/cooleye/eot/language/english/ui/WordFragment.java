package cooleye.eot.language.english.ui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import cooleye.eot.R;
import cooleye.eot.language.english.adapter.WordAdapter;
import cooleye.eot.language.english.model.Word;
import cooleye.eot.ui.BaseFragment;
import cooleye.eot.ui.widget.DividerDecoration;
import cooleye.eot.ui.widget.SideBar;
import cooleye.eot.ui.widget.TouchableRecyclerView;
import cooleye.eot.ui.widget.stickyview.StickyRecyclerHeadersDecoration;
import cooleye.eot.util.ICallback;
import io.realm.RealmResults;

/**
 * Created by cool on 16-6-13.
 */
public class WordFragment extends BaseFragment implements ICallback<RealmResults<Word>> {

    @InjectView(R.id.word_recyclerview)
    TouchableRecyclerView mRecyclerView;
    @InjectView(R.id.progress_layout)
    FrameLayout mProgressView;
    @InjectView(R.id.word_sidebar)
    SideBar mSideBar;
    @InjectView(R.id.word_dialog)
    TextView mDialogTextView;

    private Context mContext;
    private WordAdapter mWordAdapter;

    @Override
    protected int getViewId() {
        return R.layout.word_fragment;
    }

    @Override
    public void onAttach(Context context) {
        mContext = context;
        if (context instanceof WordBaseActivity) {
            ((WordBaseActivity) context).mWordCallback = this;
        }
        super.onAttach(context);
    }

    @Override
    public void callback(RealmResults<Word> words) {
        mWordAdapter = new WordAdapter(mContext, words);
        mRecyclerView.setAdapter(mWordAdapter);
        mSideBar.setTextView(mDialogTextView);
        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                mWordAdapter.closeOpenedSwipeItemLayoutWithAnim();
                int position = mWordAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mRecyclerView.scrollToPosition(position);
                }
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
                .VERTICAL, false));

        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration
                (mWordAdapter);
        mRecyclerView.addItemDecoration(headersDecor);
        mRecyclerView.addItemDecoration(new DividerDecoration(mContext));

        //   setTouchHelper();
        mWordAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                headersDecor.invalidateHeaders();
            }
        });

        mProgressView.setVisibility(View.GONE);
    }

    @OnCheckedChanged({R.id.word_showall, R.id.word_english_only, R.id.word_chinese_only})
    void OnCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked && mWordAdapter != null) {
            WordAdapter.CheckType checkType;
            if (compoundButton.getId() == R.id.word_english_only) {
                checkType = WordAdapter.CheckType.ENGLISH;
            } else if (compoundButton.getId() == R.id.word_chinese_only) {
                checkType = WordAdapter.CheckType.CHINESE;
            } else {
                checkType = WordAdapter.CheckType.ALL;
            }
            mWordAdapter.setCheckType(checkType);
        }
    }
}
