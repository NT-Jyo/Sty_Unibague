package com.trbj.sty.Models;

public class Subjects {
    private String nameSubject;
    private int comments;
    private int likes;
    private int notlikes;
    private int students;
    private String photoURL;
    private int themes;

    public Subjects(){

    }

    public Subjects(String nameSubject, int comments, int likes, int notlikes, int students, String photoURL, int themes) {
        this.nameSubject = nameSubject;
        this.comments = comments;
        this.likes = likes;
        this.notlikes = notlikes;
        this.students = students;
        this.photoURL = photoURL;
        this.themes = themes;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getNotlikes() {
        return notlikes;
    }

    public void setNotlikes(int notlikes) {
        this.notlikes = notlikes;
    }

    public int getStudents() {
        return students;
    }

    public void setStudents(int students) {
        this.students = students;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public int getThemes() {
        return themes;
    }

    public void setThemes(int themes) {
        this.themes = themes;
    }
}
