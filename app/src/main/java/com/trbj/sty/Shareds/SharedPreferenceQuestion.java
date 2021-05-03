package com.trbj.sty.Shareds;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceQuestion {

    SharedPreferences sharedPreferences;

    public SharedPreferenceQuestion(Context context){
        sharedPreferences = context.getSharedPreferences("UserQuestion",Context.MODE_PRIVATE);
    }

    public void setDataIdQuestion(String question){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("questionIdUser", question);
        editor.commit();
    }

    public void setDataQuestion(String solve){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("questionUser", solve);
        editor.commit();
    }

    public String getQuestionIdUser(){
        String nameUser=sharedPreferences.getString("questionIdUser","(~o.o)~");
        return nameUser;
    }

    public String getQuestionUser(){
        String nameUser=sharedPreferences.getString("questionUser","(~o.o)~");
        return nameUser;
    }
}
