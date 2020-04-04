package com.example.mynoteenglish.model;

import java.io.Serializable;

public class classNoteMain implements Serializable {
    private  String mID;
    private  String mName;
    private  String mContent;
    private  String mDateCreate;
    private  String mDateUpdate;
    private String mTagName;
    private String mFavorite;

    public classNoteMain(String mID, String mName, String mContent, String mDateCreate, String mDateUpdate, String mTagName, String mFavorite) {
        this.mID = mID;
        this.mName = mName;
        this.mContent = mContent;
        this.mDateCreate = mDateCreate;
        this.mDateUpdate = mDateUpdate;
        this.mTagName = mTagName;
        this.mFavorite = mFavorite;
    }

    public classNoteMain(String mName, String mContent, String mDateCreate, String mDateUpdate, String mTagName, String mFavorite) {
        this.mName = mName;
        this.mContent = mContent;
        this.mDateCreate = mDateCreate;
        this.mDateUpdate = mDateUpdate;
        this.mTagName = mTagName;
        this.mFavorite = mFavorite;
    }

    public classNoteMain() {
    }

    public String getmID() {
        return mID;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmDateCreate() {
        return mDateCreate;
    }

    public void setmDateCreate(String mDateCreate) {
        this.mDateCreate = mDateCreate;
    }

    public String getmDateUpdate() {
        return mDateUpdate;
    }

    public void setmDateUpdate(String mDateUpdate) {
        this.mDateUpdate = mDateUpdate;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmTagName() {
        return mTagName;
    }

    public void setmTagName(String mTagName) {
        this.mTagName = mTagName;
    }

    public String getmFavorite() {
        return mFavorite;
    }

    public void setmFavorite(String mFavorite) {
        this.mFavorite = mFavorite;
    }
}
