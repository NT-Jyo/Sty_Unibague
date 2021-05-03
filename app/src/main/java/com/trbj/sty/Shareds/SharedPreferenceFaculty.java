package com.trbj.sty.Shareds;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceFaculty {

    SharedPreferences sharedPreferences;

    public SharedPreferenceFaculty(Context context){
        sharedPreferences = context.getSharedPreferences("UserFaculty",Context.MODE_PRIVATE);
    }

    public void setDataUser(String name){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("facultyUser", name);
        editor.commit();
    }

    public String getFacultyUser(){
        String nameUser=sharedPreferences.getString("facultyUser","");
        return nameUser;
    }
}
