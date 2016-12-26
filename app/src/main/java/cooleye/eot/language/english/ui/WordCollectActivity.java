package cooleye.eot.language.english.ui;

import cooleye.eot.language.english.model.Word;
import cooleye.eot.language.english.util.WordType;
import io.realm.RealmQuery;

/**
 * Created by cool on 16-6-13.
 */
public class WordCollectActivity extends WordBaseActivity {

    @Override
    protected int getWordType() {
        return WordType.ALL;
    }

    @Override
    protected RealmQuery<Word> getQuery(RealmQuery<Word> query) {
        return query.equalTo("mIsCollect", true);
    }
}
