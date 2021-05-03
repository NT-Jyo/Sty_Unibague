package com.trbj.sty.Models.Topics;

public class Introduction {
    private String description;
    private String picture;

    public Introduction(){

    }

    public Introduction(String description, String picture) {
        this.description = description;
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
