package com.trbj.sty.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.trbj.sty.Models.Enrolled;
import com.trbj.sty.Models.Progress;
import com.trbj.sty.Models.UserSubjects;
import com.trbj.sty.R;
import com.trbj.sty.Shareds.SharedPreferenceSubjectsUser;
import com.trbj.sty.Shareds.SharedPreferenceUserData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ConfirmRegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    SharedPreferenceUserData sharedPreferenceUserData;
    SharedPreferenceSubjectsUser sharedPreferenceSubjectsUser;

    FirebaseAuth firebaseAuthB;
    FirebaseFirestore firebaseFirestoreB;
    FirebaseCrashlytics firebaseCrashlyticsB;

    MaterialButton material_button_confirm_registrationB;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_registration);

        firebaseAuthB = FirebaseAuth.getInstance();
        firebaseFirestoreB = FirebaseFirestore.getInstance();
        firebaseCrashlyticsB= FirebaseCrashlytics.getInstance();

        lottieAnimationView=(LottieAnimationView)findViewById(R.id.lottie_animation_register);

        sharedPreferenceUserData = new SharedPreferenceUserData(this);
        sharedPreferenceSubjectsUser = new SharedPreferenceSubjectsUser(this);

        material_button_confirm_registrationB = findViewById(R.id.material_button_confirm_registration);
        material_button_confirm_registrationB.setOnClickListener(this);
        loadAnimateGender();
    }



    private void loadAnimateGender(){
        if(sharedPreferenceSubjectsUser.getGenderTeacher()==1){
            lottieAnimationView.setAnimation("at_hanatachi_sticker.json");
        }if(sharedPreferenceSubjectsUser.getGenderTeacher()==0){
            lottieAnimationView.setRepeatCount(3);
            lottieAnimationView.setAnimation("34105_anityanochki_at_russia2d_sticker_2.json");
        }

    }

    private void saveDataCourse(){

        String[] idUser = getEmail().split("\\@");

        UserSubjects userSubjects = new UserSubjects(getIdSubject(),getTeacher(),getNameSubject(),false);

        if (idUser[1].equals("estudiantesunibague.edu.co") || idUser[1].equals("unibague.edu.co")) {
            firebaseFirestoreB.collection("Unibague").document(getEmail()).collection("Course").document(getIdSubject()).set(userSubjects);
            saveDataRegister(idUser[1]);
            createProgress(idUser[1]);
        } else {
            firebaseFirestoreB.collection("Usuario").document(getEmail()).collection("Course").document(getIdSubject()).set(userSubjects);
            saveDataRegister(idUser[1]);
            createProgress(idUser[1]);
        }
    }

    private void saveDataRegister(String domain){
        Enrolled enrolled = new Enrolled(date(),nameUser(),getPhotoUrl(),"@"+domain);
        firebaseFirestoreB.collection("Aula").document(getTeacher()).collection("Subjects").document(getIdSubject()).collection("enrolled").document(getEmail()).set(enrolled).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ConfirmRegistrationActivity.this,"Registro exitoso!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String nameUser(){
        String nameUser = sharedPreferenceUserData.getNameUser();
        return nameUser;
    }

    private Long date() {
        Date date = new Date();
        Long Time=date.getTime();
        return Time;
    }

    private String getPhotoUrl(){
        String photoUrl = sharedPreferenceUserData.getPhotoUser();
        return  photoUrl;
    }

    private String getIdSubject(){
       String idSubject = sharedPreferenceSubjectsUser.getSubjectsUser();
       return idSubject;
    }

    private String getTeacher(){
        String emailTeacher = sharedPreferenceSubjectsUser.getTeacherUser();
        return emailTeacher;
    }

    private String getNameSubject(){
        String nameSubject = sharedPreferenceSubjectsUser.getNameSubject();
        return nameSubject;
    }

    private String getEmail(){
        String email = sharedPreferenceUserData.getEmailUser();
        return email;
    }

    private void createProgress(String domain){
        Progress progress= new Progress(date(),"@"+domain,0,nameUser(),getPhotoUrl());
        firebaseFirestoreB.collection("Aula").document(getTeacher()).collection("Subjects").document(getIdSubject()).collection("progress").document(getEmail()).set(progress);

    }

    private void getStudentsCount(){
        firebaseFirestoreB.collection("Aula").document(getTeacher()).collection("Subjects").document(getIdSubject()).collection("enrolled")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                             uploadStudentsCount(task.getResult().size());
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void uploadStudentsCount(int students){
        DocumentReference documentReference= firebaseFirestoreB.collection("Aula").document(getTeacher()).collection("Subjects").document(getIdSubject());
        documentReference.update("students",students);
    }

    private void loadData(){
        saveDataCourse();
        getStudentsCount();
        Intent intent = new Intent(ConfirmRegistrationActivity.this, CoursesActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.material_button_confirm_registration:
                loadData();
            break;
        }
    }
}