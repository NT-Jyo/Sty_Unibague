package com.trbj.sty.Models.Topics;

public class Links {
    private String link1;
    private String link2;
    private String namelink1;
    private String namelink2;
    private String title;

    public Links(){

    }

    public Links(String link1, String link2, String namelink1, String namelink2, String title) {
        this.link1 = link1;
        this.link2 = link2;
        this.namelink1 = namelink1;
        this.namelink2 = namelink2;
        this.title = title;
    }

    public String getLink1() {
        return link1;
    }

    public void setLink1(String link1) {
        this.link1 = link1;
    }

    public String getLink2() {
        return link2;
    }

    public void setLink2(String link2) {
        this.link2 = link2;
    }

    public String getNamelink1() {
        return namelink1;
    }

    public void setNamelink1(String namelink1) {
        this.namelink1 = namelink1;
    }

    public String getNamelink2() {
        return namelink2;
    }

    public void setNamelink2(String namelink2) {
        this.namelink2 = namelink2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
