package com.trbj.sty.Models;

public class SolveQuestion {
    private double qualification;
    private String question;
    private String solveQuestion;
    private String email;
    private Long date;
    private String name;

    public SolveQuestion(){

    }

    public SolveQuestion(double qualification, String question, String solveQuestion, String email, Long date, String name) {
        this.qualification = qualification;
        this.question = question;
        this.solveQuestion = solveQuestion;
        this.email = email;
        this.date = date;
        this.name = name;
    }

    public double getQualification() {
        return qualification;
    }

    public void setQualification(double qualification) {
        this.qualification = qualification;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getSolveQuestion() {
        return solveQuestion;
    }

    public void setSolveQuestion(String solveQuestion) {
        this.solveQuestion = solveQuestion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
