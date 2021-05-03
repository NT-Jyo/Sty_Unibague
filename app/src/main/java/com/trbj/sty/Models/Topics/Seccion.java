package com.trbj.sty.Models.Topics;

public class Seccion {

    private String contents;
    private String description;
    private String link;
    private String picture;
    private String title;
    private String video;

    public Seccion(){

    }
    public Seccion(String contents, String description, String link, String picture, String title, String video) {
        this.contents = contents;
        this.description = description;
        this.link = link;
        this.picture = picture;
        this.title = title;
        this.video = video;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
