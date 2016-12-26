package cooleye.eot.media.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.InjectView;
import cooleye.eot.R;
import cooleye.eot.media.adapter.VideoAdapter;
import cooleye.eot.media.model.Video;
import cooleye.eot.media.util.VideoProvider;
import cooleye.eot.ui.BaseActivity;
import cooleye.eot.ui.widget.DividerDecoration;

/**
 * Created by cool on 16-9-18.
 */
public class LocalMediaActivity extends BaseActivity {

    @InjectView(R.id.local_media_recyclerview)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_media);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerDecoration(this));
        mRecyclerView.setAdapter(new VideoAdapter(this, getVideos()));
    }

    private List<Video> getVideos() {
        return new VideoProvider(this).getMedias();
    }
}
