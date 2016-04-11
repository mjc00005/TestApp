package com.example.manuel.testapp.modelo;

/**
 * Created by Manuel on 07/04/2016.
 */
public class Episode {
    private String mUid;
    private String mTitle;
    private String mSynopsis;

    public Episode() {
    }

    public Episode(String synopsis, String title, String uid) {
        mSynopsis = synopsis;
        mTitle = title;
        mUid = uid;
    }

    public String getSynopsis() {
        return mSynopsis;
    }

    public void setSynopsis(String synopsis) {
        mSynopsis = synopsis;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }
}
