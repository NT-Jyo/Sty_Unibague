package com.trbj.sty.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.trbj.sty.R;
import com.trbj.sty.Shareds.SharedPreferenceSubjectsUser;
import com.trbj.sty.Shareds.SharedPreferenceUserData;

public class AnnotationsNoteActivity extends AppCompatActivity {

    SharedPreferenceUserData sharedPreferenceUserData;
    SharedPreferenceSubjectsUser sharedPreferenceSubjectsUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotations_note);

        sharedPreferenceUserData = new SharedPreferenceUserData(this);
        sharedPreferenceSubjectsUser = new SharedPreferenceSubjectsUser(this);
        loadData();
    }



    private String emailUser(){
        String emailUser=sharedPreferenceUserData.getEmailUser();
        return emailUser;
    }

    private String loadNameSubject(){
        String nameSubject=sharedPreferenceSubjectsUser.getNameTopic();
        return nameSubject;
    }


    private void loadData(){
        System.out.println("El nombre de usuario"+ emailUser()+ "EL nombre del tema es "+ loadNameSubject());
    }
}