/**
 * created by jiang, 12/3/15
 * Copyright (c) 2015, jyuesong@gmail.com All Rights Reserved.
 * *                #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #                                                   #
 */

package cooleye.eot.language.english.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import cooleye.eot.R;
import cooleye.eot.adapter.BaseSwipeAdapter;
import cooleye.eot.language.english.model.Word;
import cooleye.eot.ui.BaseViewHolder;
import cooleye.eot.ui.widget.SwipeItemLayout;
import cooleye.eot.util.ViewUtils;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by jiang on 12/3/15.
 * 根据当前权限进行判断相关的滑动逻辑
 */
public class WordAdapter extends BaseSwipeAdapter<WordAdapter.ItemViewHolder>
        implements RealmChangeListener<RealmResults<Word>> {
    /**
     * 当前处于打开状态的item
     */
    private List<SwipeItemLayout> mOpenedSwipeItem = new ArrayList<>();

    private RealmResults<Word> mWordList;

    private Context mContext;
    private boolean mSwipeAble = true;
    private CheckType mCheckType = CheckType.ALL;
    private int mMaxHeight;
    private SparseIntArray mHeightArray;

    public WordAdapter(@NonNull Context context, @NonNull RealmResults<Word> mLists) {
        this.mWordList = mLists;
        this.mContext = context;
        mHeightArray = new SparseIntArray();
        mLists.addChangeListener(this);
    }

    public void setCheckType(CheckType checkType) {
        this.mCheckType = checkType;
        mHeightArray.clear();
        notifyDataSetChanged();
    }

    public void setSwipeAble(boolean swipeAble) {
        this.mSwipeAble = swipeAble;
        notifyDataSetChanged();
    }

    @Override
    public WordAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.word_item, parent, false);
        return new WordAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WordAdapter.ItemViewHolder holder, final int position) {
        holder.mSwipeItemLayout.setSwipeAble(mSwipeAble);
        holder.mSwipeItemLayout.setDelegate(this);
        Word word = mWordList.get(position);
        holder.mWordTextView.setText(word.getEnglish());
        holder.mChineseTextView.setText(word.getChinese());
        holder.mCollectTextView.setText(mContext.getString(mWordList.get(position).isCollect() ?
                R.string.collect_cancel : R.string.collect));
        holder.mCollectTextView.setTag(position);
        holder.mCollectTextView.setOnClickListener(this);
        holder.mDeleteTextView.setTag(position);
        holder.mDeleteTextView.setOnClickListener(this);

        holder.mWordTextView.setVisibility(mCheckType == CheckType.CHINESE ? View.GONE : View
                .VISIBLE);
        holder.mChineseTextView.setVisibility(mCheckType == CheckType.ENGLISH ? View.GONE : View
                .VISIBLE);
        if (mHeightArray.indexOfKey(position) < 0) {
            ViewUtils.measureView(holder.mItemView);
            int height = holder.mItemView
                    .getMeasuredHeight();
            mHeightArray.put(position, height);
        }
        holder.mCollectTextView.setHeight(mHeightArray.get(position));
        holder.mDeleteTextView.setHeight(mHeightArray.get(position));
    }

    @Override
    public void onSwipeItemLayoutOpened(SwipeItemLayout swipeItemLayout) {
        super.onSwipeItemLayoutOpened(swipeItemLayout);
        mOpenedSwipeItem.add(swipeItemLayout);
    }

    @Override
    public void onSwipeItemLayoutClosed(SwipeItemLayout swipeItemLayout) {
        mOpenedSwipeItem.remove(swipeItemLayout);
    }

    @Override
    public void onClick(View v) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Word word = mWordList.get((int) v.getTag());
        if (v.getId() == R.id.word_delete) {
            word.deleteFromRealm();
        } else if (v.getId() == R.id.word_collect) {
            word.setCollect(!word.isCollect());
        }
        realm.commitTransaction();
        closeOpenedSwipeItemLayoutWithAnim();
        if (v.getId() == R.id.word_delete) {
            mHeightArray.clear();
            notifyDataSetChanged();
        } else if (v.getId() == R.id.word_collect) {
            ((TextView) v).setText(mContext.getString(word.isCollect() ?
                    R.string.collect_cancel : R.string.collect));
        }
    }


    @Override
    public int getItemCount() {
        if (mWordList != null) {
            return mWordList.size();
        }
        return 0;
    }

    @Override
    public long getHeaderId(int position) {
        Character c = mWordList.get(position).getSort().charAt(0);
        return c;

    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.word_header, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView textView = (TextView) holder.itemView;
        textView.setText(mWordList.get(position).getSort());
    }


    public int getPositionForSection(char section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = mWordList.get(i).getSort();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;

    }

    @Override
    public void closeOpenedSwipeItemLayoutWithAnim() {
        for (SwipeItemLayout sil : mOpenedSwipeItem) {
            sil.closeWithAnim();
        }
        mOpenedSwipeItem.clear();
    }

    @Override
    public void onChange(RealmResults<Word> element) {
      //  mHeightArray.clear();
      //  notifyDataSetChanged();
    }

    public enum CheckType {
        ALL, ENGLISH, CHINESE
    }

    public class ItemViewHolder extends BaseViewHolder {

        @InjectView(R.id.word_swipe_layout)
        SwipeItemLayout mSwipeItemLayout;

        @InjectView(R.id.word_textview)
        TextView mWordTextView;

        @InjectView(R.id.chinese_textview)
        TextView mChineseTextView;

        @InjectView(R.id.word_collect)
        TextView mCollectTextView;

        @InjectView(R.id.word_delete)
        TextView mDeleteTextView;

        View mItemView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
        }
    }
}
