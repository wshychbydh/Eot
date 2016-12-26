package com.example;

/**
 * Created by cool on 16-9-20.
 */
public class Mp3Info {
    private String mMusicName;
    private String mArtist;
    private String mAlbum;
    private String mTime;
    private String mComment;

    public String getAlbum() {
        return mAlbum;
    }

    public void setAlbum(String album) {
        mAlbum = album;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public String getMusicName() {
        return mMusicName;
    }

    public void setMusicName(String musicName) {
        mMusicName = musicName;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    @Override
    public String toString() {
        return "Mp3Info{" +
                "mAlbum='" + mAlbum + '\'' +
                ", mMusicName='" + mMusicName + '\'' +
                ", mArtist='" + mArtist + '\'' +
                ", mTime='" + mTime + '\'' +
                ", mComment='" + mComment + '\'' +
                '}';
    }
}
