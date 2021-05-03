package com.trbj.sty.Models;

public class Teacher {
    private String subjects;
    private String description;
    private String email;
    private String displayName;
    private String program;
    private String faculty;
    private long date;
    private String photoURL;
    private String rol;
    private boolean state;
    private String uid;

    public Teacher() {
    }

    public Teacher(String subjects, String description, String email, String displayName, String program, String faculty, long date, String photoURL, String rol, boolean state, String uid) {
        this.subjects = subjects;
        this.description = description;
        this.email = email;
        this.displayName = displayName;
        this.program = program;
        this.faculty = faculty;
        this.date = date;
        this.photoURL = photoURL;
        this.rol = rol;
        this.state = state;
        this.uid = uid;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

