package com.example;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.AbstractID3v2;
import org.farng.mp3.id3.ID3v1;
import org.farng.mp3.lyrics3.AbstractLyrics3;

import java.io.IOException;

public class TestMP3 {

    /**
     * @param args
     */
    public static void main(String[] args) {

        try {

            //MP3File file = new MP3File("c:\\TDDOWNLOAD\\shuangjiegun.mp3");//1,2  
            MP3File file = new MP3File("/home/cool/cool/share/files/天下");//1,lyrics
            // MP3File file = new MP3File("/home/zhubin/Music/1.mp3");//1,lyrics
            AbstractID3v2 id3v2 = file.getID3v2Tag();
            ID3v1 id3v1 = file.getID3v1Tag();
            System.out.println(System.getProperty("file.encoding"));
            if (id3v2 != null) {

                System.out.println("id3v2");

                System.out.println("name " + id3v2.getAlbumTitle());//专辑名
                System.out.println("name2 " + id3v2.getSongTitle());//歌曲名
                System.out.println("name3 " + id3v2.getLeadArtist());//歌手

            }
            if (id3v1 != null) {
                System.out.println("id3v1");

                System.out.println("name " + id3v1.getAlbumTitle());
                System.out.println("name2 " + id3v1.getSongTitle());
                System.out.println("name3 " + id3v1.getLeadArtist());

            }

            AbstractLyrics3 lrc3Tag = file.getLyrics3Tag();
            if (lrc3Tag != null) {
                String lyrics = lrc3Tag.getSongLyric();
                System.out.println(lyrics);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TagException e) {
            e.printStackTrace();
        }

        System.out.println("over");

    }


}  