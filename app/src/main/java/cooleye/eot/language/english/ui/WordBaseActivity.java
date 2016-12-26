package cooleye.eot.language.english.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import cooleye.eot.R;
import cooleye.eot.language.english.db.WordSharedUtil;
import cooleye.eot.language.english.model.Word;
import cooleye.eot.language.english.util.IReadCallback;
import cooleye.eot.language.english.util.WordType;
import cooleye.eot.ui.BaseActivity;
import cooleye.eot.util.ICallback;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by cool on 16-6-22.
 */
public abstract class WordBaseActivity extends BaseActivity {

    public ICallback<RealmResults<Word>> mWordCallback;
    protected WordFragment mWordFragment;
    private int mWordType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        addWordFragment();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        init();
    }

    private void init() {
        mWordType = getWordType();
        new WordSharedUtil(this, new IReadCallback() {
            @Override
            public void onFinished() {
                if (mWordCallback != null) {
                    RealmResults<Word> words = getWordsBySorts();
                    System.out.println(words);
                    mWordCallback.callback(words);
                }
            }

            @Override
            public void onFailed() {
            }
        }).readSource(mWordType);
    }

    /**
     * @return
     * @see WordType
     */
    protected abstract int getWordType();

    /**
     * 对结果进行筛选排序
     *
     * @param result
     * @return
     */
    protected RealmResults<Word> sorts(RealmResults<Word> result) {
        return result.sort("mSort");
    }

    /**
     * 对结果进行筛选过滤
     *
     * @param query
     * @return
     */
    protected RealmQuery<Word> getQuery(RealmQuery<Word> query) {
        query.equalTo("mType", mWordType);
        return query;
    }

    private RealmResults<Word> getWordsBySorts() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Word> query = getQuery(realm.where(Word.class));
        RealmResults<Word> result = query.findAll();
        return sorts(result);
    }

    private void addWordFragment() {
        if (mWordFragment == null) {
            mWordFragment = new WordFragment();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mWordFragment.isAdded()) {
            transaction.show(mWordFragment);
        } else {
            transaction.add(R.id.word_container, mWordFragment);
        }
        transaction.commit();
    }
}
