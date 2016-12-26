package cooleye.eot.language.english.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.UUID;

import cooleye.eot.language.english.db.WordSharedUtil;
import cooleye.eot.language.english.model.Word;
import cooleye.eot.util.AsyncTaskUtil;
import io.realm.Realm;

/**
 * Created by cool on 2015/11/5.
 */
public class WordAssetsUtils {

    public static void readSource(final Context context, final int wordType,
                                  final IReadCallback callback) {
        new AsyncTaskUtil<>(new AsyncTaskUtil.IAsyncTaskCallback<Boolean>() {
            @Override
            public Boolean asyncMethod() {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(context
                            .getAssets().open(wordType == WordType.EMPHASIS ? WordSharedUtil
                                    .EMPHASIS_PATH : WordSharedUtil.WORD_PATH)));
                    if (wordType == WordType.EMPHASIS) {
                        readWords(br, realm);
                    } else {
                        readLines(br, realm);
                    }
                    realm.commitTransaction();
                    return true;
                } catch (IOException e) {
                    realm.cancelTransaction();
                    e.printStackTrace();
                    return false;
                } finally {
                    System.gc();
                }
            }

            @Override
            public void syncMethod(Boolean result) {
                if (callback != null) {
                    if (result) {
                        callback.onFinished();
                    } else {
                        callback.onFailed();
                    }
                }
            }

        }).start();
    }

    /**
     * @param br
     * @param realm
     * @throws IOException
     */
    private static void readLines(BufferedReader br, Realm realm) throws IOException {
        String str;
        while ((str = br.readLine()) != null) {
            if (str.length() > 1) {
                StringTokenizer tokenizer = new StringTokenizer(str, "\\|");
                if (tokenizer.hasMoreTokens()) {
                    createWord(realm, tokenizer, WordType.STUDY);
                }
            }
        }
    }

    /**
     * 填充
     * <p/>
     * line rule:
     * english,chinese,description
     *
     * @param realm
     * @param tokenizer
     */
    private static void createWord(Realm realm, StringTokenizer tokenizer, int type) {
        Word word = realm.createObject(Word.class);
        word.setId(UUID.randomUUID().toString());
        word.setType(type);
        if (tokenizer.hasMoreTokens()) {
            String english = tokenizer.nextToken().trim();
            word.setEnglish(english);
            if (english.length() > 1) {
                word.setSort(english.substring(0, 1).toUpperCase());
            }
        }
        if (tokenizer.hasMoreTokens()) {
            word.setChinese(tokenizer.nextToken().trim());
        }
        if (tokenizer.hasMoreTokens()) {
            word.setDescription(tokenizer.nextToken());
        }
    }

    /**
     * @param br
     * @param realm
     * @throws IOException
     */
    private static void readWords(BufferedReader br, Realm realm) throws IOException {
        String str;
        StringBuilder sb = new StringBuilder();
        while ((str = br.readLine()) != null) {
            if (str.length() > 1) {
                if (WordSplitPolicy.BR.equals(str) || str.contains(WordSplitPolicy.IMAGINARY_LINE)
                        || str.contains(WordSplitPolicy.STARTS)) {
                    if (sb.length() > 0) {
                        createWord(realm, new StringTokenizer(sb.toString(), "\\|"), WordType
                                .EMPHASIS);
                        sb = new StringBuilder();
                    }
                } else {
                    sb.append(str);
                    if (sb.length() == str.length()) {
                        sb.append("|");
                    }
                    sb.append("\n");
                }
            }
        }
    }

}