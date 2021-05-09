package com.trbj.sty.Models;

public class UserSubjects {

    private String idSubject;
    private String idTeacher;
    private String nameSubject;
    private boolean liked;

    public UserSubjects(){}

    public UserSubjects(String idSubject, String idTeacher, String nameSubject, boolean liked) {
        this.idSubject = idSubject;
        this.idTeacher = idTeacher;
        this.nameSubject = nameSubject;
        this.liked = liked;
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

    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
