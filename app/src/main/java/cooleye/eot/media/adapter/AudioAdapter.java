package cooleye.eot.media.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cooleye.eot.R;
import cooleye.eot.media.model.Audio;
import cooleye.eot.media.util.MediaUtils;

/**
 * Created by cool on 16-9-18.
 */
public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.VideoHolder> {

    private Activity mActivity;
    private List<Audio> mAudioList;

    public AudioAdapter(Activity activity, List<Audio> audioList) {
        mActivity = activity;
        mAudioList = audioList;
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mActivity).inflate(R.layout.item_audio, null);
        return new VideoHolder(v);
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        Audio audio = mAudioList.get(position);
        holder.mName.setText(audio.getDisplayName());
        holder.mSize.setText(audio.getTitle()+"   "+MediaUtils.formatSize(audio.getSize()));
        holder.mDuration.setText(MediaUtils.formatDuration(audio.getDuration()));
        holder.mLayout.setTag(audio);
    }

    @Override
    public int getItemCount() {
        if (mAudioList != null) {
            return mAudioList.size();
        }
        return 0;
    }

    public class VideoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @InjectView(R.id.item_audio)
        RelativeLayout mLayout;
        @InjectView(R.id.audio_name)
        TextView mName;
        @InjectView(R.id.audio_size)
        TextView mSize;
        @InjectView(R.id.audio_duration)
        TextView mDuration;

        public VideoHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            mLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Audio audio = (Audio) v.getTag();
            if (audio == null) {
                return;
            }
            playAudio(audio.getPath());
        }

        private void playAudio(String audioPath) {
            Intent intent_music = new Intent(Intent.ACTION_PICK);
            intent_music.setDataAndType(Uri.parse(audioPath), "audio/*");
           // intent_music.setDataAndType(Uri.EMPTY,"vnd.android.cursor.dir/playlist");
            intent_music.putExtra("withtabs", true); // 显示tab选项卡
            intent_music.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            Intent j =Intent.createChooser(intent_music, "Choose an application to open with:");
            if (j == intent_music) {
                mActivity.startActivity(j);
            } else {
                Intent intent = new Intent("android.intent.action.MUSIC_PLAYER");
                mActivity.startActivity(intent);
            }
        }
    }
}
