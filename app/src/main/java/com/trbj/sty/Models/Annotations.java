package com.trbj.sty.Models;

public class Annotations {

    private String Title;
    private String date;
    private String Description;
    private String photoURL;
    private String keywords;
    private String examples;
    private String resume;
    private String subjectName;

    public Annotations(){
    }

    public Annotations(String title, String date, String description, String photoURL, String keywords, String examples, String resume, String subjectName) {
        Title = title;
        this.date = date;
        Description = description;
        this.photoURL = photoURL;
        this.keywords = keywords;
        this.examples = examples;
        this.resume = resume;
        this.subjectName = subjectName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getExamples() {
        return examples;
    }

    public void setExamples(String examples) {
        this.examples = examples;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
