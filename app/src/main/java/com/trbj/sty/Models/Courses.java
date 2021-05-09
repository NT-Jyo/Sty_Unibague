package com.trbj.sty.Models;

public class Courses {
    private String idSubject;
    private String idTeacher;
    private boolean liked;
    private String nameSubject;

    public Courses(){}

    public Courses(String idSubject, String idTeacher, boolean liked, String nameSubject) {
        this.idSubject = idSubject;
        this.idTeacher = idTeacher;
        this.liked = liked;
        this.nameSubject = nameSubject;
    }

    public String getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(String idSubject) {
        this.idSubject = idSubject;
    }

    public String getIdTeacher() {
        return idTeacher;
    }

    public void setIdTeacher(String idTeacher) {
        this.idTeacher = idTeacher;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }
}
