package cooleye.eot.media.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cooleye.eot.R;
import cooleye.eot.ui.BaseActivity;

public class MediaActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
    }

    public void audioMedia(View v) {
        startActivity(new Intent(this, AudioMediaActivity.class));
    }
    public void streamMedia(View v) {
        startActivity(new Intent(this, StreamMediaActivity.class));
    }

    public void localMedia(View v) {
        startActivity(new Intent(this, LocalMediaActivity.class));
    }
}
