package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

/**
 * Created by cool on 16-9-20.
 */
public class Music {

    public static void main(String[] args) {
        new Music().getMusic("music");
    }

    public List<File> getMusic(String folderPath) {
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            for (File file : files) {
                System.out.println(getMusicInfo(file));
            }
        }
        return null;
    }

    /**
     * 获取MP3文件信息
     *
     * @param musicFile MP3文件对象
     */
    private Mp3Info getMusicInfo(File musicFile) {

        try {
            Mp3Info mp3Info = new Mp3Info();
            RandomAccessFile randomAccessFile = new RandomAccessFile(musicFile, "r");
            byte[] buffer = new byte[128];
            randomAccessFile.seek(randomAccessFile.length() - 128);
            randomAccessFile.read(buffer);
            if (buffer.length == 128) {
                String tag = new String(buffer, 0, 3);
                // 只有前三个字节是TAG才处理后面的字节
                if (tag.equalsIgnoreCase("TAG")) {
                    // 歌曲名
                    String songName = new String(buffer, 3, 30).trim();
                    // 艺术家
                    String artist = new String(buffer, 33, 30).trim();
                    // 所属唱片
                    String album = new String(buffer, 63, 30).trim();
                    // 发行年
                    String year = new String(buffer, 93, 4).trim();
                    // 备注
                    String comment = new String(buffer, 97, 28).trim();
                    System.out.println(songName + "\t" + artist + "\t" + album
                            + "\t" + year + "\t" + comment);
                    mp3Info.setAlbum(album);
                    mp3Info.setArtist(artist);
                    mp3Info.setComment(comment);
                    mp3Info.setMusicName(songName);
                    mp3Info.setTime(year);
                } else {
                    System.out.println("无效的歌曲信息...");
                }
            }

            return mp3Info;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
