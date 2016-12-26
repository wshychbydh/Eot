package cooleye.eot.media.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import butterknife.InjectView;
import cooleye.eot.R;
import cooleye.eot.media.adapter.AudioAdapter;
import cooleye.eot.media.model.Audio;
import cooleye.eot.media.util.AudioProvider;
import cooleye.eot.ui.BaseActivity;
import cooleye.eot.ui.widget.DividerDecoration;
import cooleye.eot.util.AsyncTaskUtil;

/**
 * Created by cool on 16-9-18.
 */
public class AudioMediaActivity extends BaseActivity {

    @InjectView(R.id.local_media_recyclerview)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_media);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerDecoration(this));
        mRecyclerView.setAdapter(new AudioAdapter(this, getAudios()));
    }

    private void modifyMp3Name() {
        new AsyncTaskUtil<>(new AsyncTaskUtil.IAsyncTaskCallback<Object>() {
            @Override
            public Object asyncMethod() {
                List<Audio> audios = getAudios();
                for (Audio audio : audios) {
                    File file = new File(audio.getPath());
                    if (!file.getName().equals(audio.getTitle())) {
                        File newFile = new File(file.getParentFile(), mp3NameRule(audio.getArtist
                                (), audio.getTitle(), audio.getAlbum()));
                        try {
                            newFile.createNewFile();
                            boolean b = file.renameTo(newFile);
                            System.out.println(file.getName() + " --rename-- >" + b);
                            if (b) {
                                file.delete();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return null;
            }

            @Override
            public void syncMethod(Object result) {

            }
        }).start();
    }

    private String mp3NameRule(String artist, String name, String album) {
        return artist + "-" + name + "-" + album;
    }


    private void getAudioInfo(String audioPath) throws IOException {
        byte[] buf = new byte[128];//初始化标签信息的byte数组

        RandomAccessFile raf = new RandomAccessFile(new File(audioPath), "r");//随机读写方式打开MP3文件

        raf.seek(raf.length() - 128);//移动到文件MP3末尾

        raf.read(buf);//读取标签信息

        raf.close();//关闭文件

        System.out.println("all--->" + new String(buf, 0, buf.length));

        if (buf.length != 128) {//数据长度是否合法
            System.out.println(audioPath + "-->MP3标签信息数据长度不合法!");
        }

        if (!"TAG".equalsIgnoreCase(new String(buf, 0, 3))) {//标签头是否存在
            System.out.println(audioPath + "-->MP3标签信息数据格式不正确!");
        }

        String SongName = new String(buf, 3, 30, "utf-8").trim();//歌曲名称

        String Artist = new String(buf, 33, 30, "utf-8").trim();//歌手名字

        String Album = new String(buf, 63, 30, "utf-8").trim();//专辑名称

        String Year = new String(buf, 93, 4, "utf-8").trim();//出品年份

        String Comment = new String(buf, 97, 28, "utf-8").trim();//备注信息

        System.out.println("歌曲名称 : " + SongName);
        System.out.println("歌手名字 : " + Artist);
        System.out.println("专辑名称 : " + Album);
        System.out.println("出品年份 : " + Year);
        System.out.println("备注信息 : " + Comment);
    }

    private List<Audio> getAudios() {
        return new AudioProvider(this).getMedias();
    }
}
