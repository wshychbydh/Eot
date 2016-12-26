package cooleye.eot.language.english.model;

import cooleye.eot.language.english.util.WordType;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by cool on 16-6-13.
 */
public class Word extends RealmObject {
    @PrimaryKey
    String mId;    //编号(唯一)
    String mSort;   //A-Z的类别
    int mShowTimes;  //展示的次数
    int mCheckTimes; //查看次数
    int mWrongTimes; //总计错误次数
    String mFriendlyId; //相关的单词的id,用逗号隔开，本来用int数组的，但是好像realm不支持
    boolean mIsCollect; //是否收藏
    int mType = WordType.STUDY;
    String mEnglish;
    String mChinese;
    String mDescription; //描述
    RealmList<Resource> mSource; //短语/文章/音频/图片等

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getSort() {
        return mSort;
    }

    public void setSort(String sort) {
        mSort = sort;
    }

    public int getShowTimes() {
        return mShowTimes;
    }

    public void setShowTimes(int showTimes) {
        mShowTimes = showTimes;
    }

    public int getCheckTimes() {
        return mCheckTimes;
    }

    public void setCheckTimes(int checkTimes) {
        mCheckTimes = checkTimes;
    }

    public int getWrongTimes() {
        return mWrongTimes;
    }

    public void setWrongTimes(int wrongTimes) {
        mWrongTimes = wrongTimes;
    }

    public String getFriendlyId() {
        return mFriendlyId;
    }

    public void setFriendlyId(String friendlyId) {
        mFriendlyId = friendlyId;
    }

    public boolean isCollect() {
        return mIsCollect;
    }

    public void setCollect(boolean collect) {
        mIsCollect = collect;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public String getEnglish() {
        return mEnglish;
    }

    public void setEnglish(String english) {
        mEnglish = english;
    }

    public String getChinese() {
        return mChinese;
    }

    public void setChinese(String chinese) {
        mChinese = chinese;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public RealmList<Resource> getSource() {
        return mSource;
    }

    public void setSource(RealmList<Resource> source) {
        mSource = source;
    }

    @Override
    public String toString() {
        return "Word{" +
                "mId='" + mId + '\'' +
                ", mSort='" + mSort + '\'' +
                ", mShowTimes=" + mShowTimes +
                ", mCheckTimes=" + mCheckTimes +
                ", mWrongTimes=" + mWrongTimes +
                ", mFriendlyId='" + mFriendlyId + '\'' +
                ", mIsCollect=" + mIsCollect +
                ", mType=" + mType +
                ", mEnglish='" + mEnglish + '\'' +
                ", mChinese='" + mChinese + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mSource=" + mSource +
                '}';
    }
}
