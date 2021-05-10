package com.trbj.sty.Shareds;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceSubjectsUser {
    SharedPreferences sharedPreferences;

    public SharedPreferenceSubjectsUser(Context context){
        sharedPreferences = context.getSharedPreferences("UserSubjects",Context.MODE_PRIVATE);
    }

    public void setDataUserTeacher(String idTeacher){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("idTeacher", idTeacher);
        editor.commit();
    }

    public void setGenderTeacher(int idTeacher){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("genderTeacher", idTeacher);
        editor.commit();
    }


    public void setDataUserSubjects(String idSubjects){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("idSubjects", idSubjects);
        editor.commit();
    }

    public void setDataUserTopics(String idTopics){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("idTopics", idTopics);
        editor.commit();
    }

    public void setNameTopic(String nameTopic){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nameTopic", nameTopic);
        editor.commit();
    }

    public void setNameSubject(String nameSubject){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nameSubject", nameSubject);
        editor.commit();
    }

    public int getGenderTeacher(){
        int idTeacher=sharedPreferences.getInt("genderTeacher",0);
        return idTeacher;
    }

    public String getNameSubject(){
        String idTeacher=sharedPreferences.getString("nameSubject","");
        return idTeacher;
    }
    public String getTeacherUser(){
        String idTeacher=sharedPreferences.getString("idTeacher","");
        return idTeacher;
    }

    public String getSubjectsUser(){
        String idSubjects=sharedPreferences.getString("idSubjects","");
        return idSubjects;
    }

    public String getTopicsUser(){
        String idTopics=sharedPreferences.getString("idTopics","");
        return idTopics;
    }

    public String getNameTopic(){
        String idTopics=sharedPreferences.getString("nameTopic","");
        return idTopics;
    }

}
