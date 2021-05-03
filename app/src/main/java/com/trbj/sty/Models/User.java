package com.trbj.sty.Models;

public class User {

    public String name;
    public String photo;
    public String email;
    public String provider;
    public String uid;


    public User(){
    }

    public User(String name, String photo, String email, String provider, String uid) {
        this.name = name;
        this.photo = photo;
        this.email = email;
        this.provider = provider;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
