package cooleye.eot.language.english.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

import cooleye.eot.R;
import cooleye.eot.language.english.util.WordAssetsUtils;
import cooleye.eot.language.english.util.IReadCallback;
import cooleye.eot.language.english.util.WordType;
import cooleye.eot.util.ICallback;
import cooleye.eot.util.ToastUtils;

/**
 * Created by cool on 16-6-13.
 */
public class WordSharedUtil {

    public final static String WORD_PATH = "english/word.txt";
    public final static String EMPHASIS_PATH = "english/emphasis.txt";

    private final static String WORD_SHARE = "word_share";
    private final static String WORD_STUDY_READ = "word_study_read";
    private final static String WORD_EMPHASIS_READ = "word_emphasis_read";

    private Context mContext;
    private IReadCallback mIReadCallback;
    private volatile int mReadCount;   //总共需要读取的数量
    private volatile boolean mReadStatus;  //true表示至少有一条读取成功,反之表示全部读取失败

    private ICallback<Pair<Integer, Boolean>> mCallback = new ICallback<Pair<Integer,
            Boolean>>() {
        @Override
        public void callback(Pair<Integer, Boolean> pair) {
            --mReadCount;
            if (pair.second) {
                mReadStatus = true;
                SharedPreferences shared = mContext.getSharedPreferences(WORD_SHARE, Context
                        .MODE_PRIVATE);
                shared.edit().putBoolean(pair.first == WordType.EMPHASIS ? WORD_EMPHASIS_READ :
                                WORD_STUDY_READ
                        , true).apply();
            } else {
                mReadStatus |= mReadStatus;
                ToastUtils.showToast(mContext, pair.first == WordType.EMPHASIS ?
                        mContext.getString(R.string.word_emphasis_read_failed) :
                        mContext.getString(R.string.word_study_read_failed));
            }
            if (mIReadCallback != null) {
                if (mReadCount == 0) {
                    if (mReadStatus) {
                        mIReadCallback.onFinished();
                    } else {
                        mIReadCallback.onFailed();
                    }
                }
            }
        }
    };

    public WordSharedUtil(Context context, IReadCallback callback) {
        mContext = context;
        this.mIReadCallback = callback;
    }

    public final void readSource(final int wordType) {
        SharedPreferences shared = mContext.getSharedPreferences(WORD_SHARE, Context
                .MODE_PRIVATE);
        boolean studyRead = shared.getBoolean(WORD_STUDY_READ, false);
        boolean emphasisRead = shared.getBoolean(WORD_EMPHASIS_READ, false);

        if (wordType == WordType.ALL) {
            if (!studyRead) {
                readStudySource();
            }
            if (!emphasisRead) {
                readEmphasisSource();
            }
            if (studyRead && emphasisRead && mIReadCallback != null) {
                mIReadCallback.onFinished();
            }
        } else {
            if (wordType == WordType.STUDY) {
                if (!studyRead) {
                    readStudySource();
                    return;
                }
            } else if (wordType == WordType.EMPHASIS) {
                if (!emphasisRead) {
                    readEmphasisSource();
                    return;
                }
            }
            if (mIReadCallback != null) {
                mIReadCallback.onFinished();
            }
        }
    }

    private void readStudySource() {
        mReadCount++;
        WordAssetsUtils.readSource(mContext, WordType.STUDY, new IReadCallback() {
            @Override
            public void onFinished() {
                mCallback.callback(Pair.create(WordType.STUDY, true));
            }

            @Override
            public void onFailed() {
                mCallback.callback(Pair.create(WordType.STUDY, false));
            }
        });
    }

    private void readEmphasisSource() {
        mReadCount++;
        WordAssetsUtils.readSource(mContext, WordType.EMPHASIS, new IReadCallback() {
            @Override
            public void onFinished() {
                mCallback.callback(Pair.create(WordType.EMPHASIS, true));
            }

            @Override
            public void onFailed() {
                mCallback.callback(Pair.create(WordType.EMPHASIS, false));
            }
        });
    }

}
