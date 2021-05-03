package com.trbj.sty.Models;

public class Subjects {
    private String nameSubject;
    private double comments;
    private double likes;
    private double notlikes;
    private double students;
    private String photoURL;
    private double themes;

    public Subjects(){

    }

    public Subjects(String nameSubject, double comments, double likes, double notlikes, double students, String photoURL, double themes) {
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

    public double getComments() {
        return comments;
    }

    public void setComments(double comments) {
        this.comments = comments;
    }

    public double getLikes() {
        return likes;
    }

    public void setLikes(double likes) {
        this.likes = likes;
    }

    public double getNotlikes() {
        return notlikes;
    }

    public void setNotlikes(double notlikes) {
        this.notlikes = notlikes;
    }

    public double getStudents() {
        return students;
    }

    public void setStudents(double students) {
        this.students = students;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public double getThemes() {
        return themes;
    }

    public void setThemes(double themes) {
        this.themes = themes;
    }
}
