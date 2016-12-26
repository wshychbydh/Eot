package cooleye.eot.media.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cooleye.eot.R;
import cooleye.eot.media.downloader.ImageLoader;
import cooleye.eot.media.model.Video;
import cooleye.eot.media.util.MediaUtils;

/**
 * Created by cool on 16-9-18.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {

    private Activity mActivity;
    private List<Video> mVideoList;
    private ImageLoader mImageLoader;

    public VideoAdapter(Activity activity, List<Video> videoList) {
        mActivity = activity;
        mVideoList = videoList;
        mImageLoader = new ImageLoader();
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mActivity).inflate(R.layout.item_video, null);
        return new VideoHolder(v);
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        Video video = mVideoList.get(position);
        mImageLoader.load(holder.mImageView, video.getPath());
      //  holder.mImageView.setImageBitmap(MediaUtils.getVideoFirstFrame(video.getPath(), 420, 420));
        holder.mName.setText(video.getDisplayName());
        holder.mSize.setText(MediaUtils.formatSize(video.getSize()));
        holder.mDuration.setText(MediaUtils.formatDuration(video.getDuration()));
        holder.mLayout.setTag(video);
    }

    @Override
    public int getItemCount() {
        if (mVideoList != null) {
            return mVideoList.size();
        }
        return 0;
    }

    public class VideoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @InjectView(R.id.item_video)
        RelativeLayout mLayout;
        @InjectView(R.id.video_icon)
        ImageView mImageView;
        @InjectView(R.id.video_name)
        TextView mName;
        @InjectView(R.id.video_size)
        TextView mSize;
        @InjectView(R.id.video_duration)
        TextView mDuration;

        public VideoHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            mLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Video video = (Video) v.getTag();
            if (video == null) {
                return;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(video.getPath()), video.getMimeType());
            mActivity.startActivity(intent);
        }
    }
}
