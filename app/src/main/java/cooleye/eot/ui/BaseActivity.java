package cooleye.eot.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by cool on 16-6-13.
 */
public class BaseActivity extends AppCompatActivity {

    public void toActivity(Class<? extends Activity> clazz) {
        toActivity(new Intent(this, clazz));
    }

    public void toActivity(Intent intent) {
        startActivity(intent);
    }

    public void toActivityAndFinish(Class<? extends Activity> clazz) {
        toActivityAndFinish(new Intent(this, clazz));
    }

    public void toActivityAndFinish(Intent intent) {
        startActivity(intent);
        finish();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.inject(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.inject(this);
    }
}
