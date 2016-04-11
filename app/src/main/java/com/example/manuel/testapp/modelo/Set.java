package com.example.manuel.testapp.modelo;

import java.util.ArrayList;

/**
 * Created by Manuel on 07/04/2016.
 */
public class Set {

    private String mUid;
    private String mTitle;
    private int mNumFilms;
    private String body;
    private ArrayList<String> mImages;
    private ArrayList<Episode> mEpisodes;


    public Set() {
        mImages = new ArrayList<>();
        mEpisodes = new ArrayList<>();
    }

    public Set(String body, ArrayList<Episode> episodes, ArrayList<String> images, int numFilms, String title, String uid) {
        this.body = body;
        mEpisodes = episodes;
        mImages = images;
        mNumFilms = numFilms;
        mTitle = title;
        mUid = uid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ArrayList<Episode> getEpisodes() {
        return mEpisodes;
    }

    public void setEpisodes(ArrayList<Episode> episodes) {
        mEpisodes = episodes;
    }

    public ArrayList<String> getImages() {
        return mImages;
    }

    public void setImages(ArrayList<String> images) {
        mImages = images;
    }

    public int getNumFilms() {
        return mNumFilms;
    }

    public void setNumFilms(int numFilms) {
        mNumFilms = numFilms;
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
