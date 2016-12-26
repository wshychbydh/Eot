package cooleye.eot.ui;

import android.app.Application;

import cooleye.eot.db.RealmHelper;

/**
 * Created by cool on 16-6-13.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RealmHelper.configuration(getApplicationContext());
    }
}
