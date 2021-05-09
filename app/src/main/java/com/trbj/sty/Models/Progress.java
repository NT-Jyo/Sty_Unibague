package com.trbj.sty.Models;

public class Progress {
    private long date;
    private String domain;
    private int progress;
    private String nameUser;
    private String photoUrl;

    public Progress(){

    }

    public Progress(long date, String domain, int progress, String nameUser, String photoUrl) {
        this.date = date;
        this.domain = domain;
        this.progress = progress;
        this.nameUser = nameUser;
        this.photoUrl = photoUrl;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
