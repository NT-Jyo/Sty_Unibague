package com.trbj.sty.Models;

public class Enrolled {
    private Long date;
    private String name;
    private String photoUrl;
    private String domain;

    public Enrolled(Long date) {
        this.date = date;
    }

    public Enrolled(Long date, String name, String photoUrl, String domain) {
        this.date = date;
        this.name = name;
        this.photoUrl = photoUrl;
        this.domain = domain;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
