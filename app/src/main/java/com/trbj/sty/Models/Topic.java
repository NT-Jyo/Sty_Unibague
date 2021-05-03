package com.trbj.sty.Models;

public class Topic {
    private String name;
    private Long date;

    public Topic(){

    }

    public Topic(String name, Long date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
