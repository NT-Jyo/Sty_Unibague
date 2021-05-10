package com.trbj.sty.Activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.trbj.sty.Models.Topics.Seccion;
import com.trbj.sty.R;
import com.trbj.sty.Shareds.SharedPreferenceSubjectsUser;
import com.trbj.sty.Shareds.SharedPreferenceUserData;

public class SeccionOneActivity extends AppCompatActivity implements  View.OnClickListener{

    FirebaseAuth firebaseAuthB;
    FirebaseCrashlytics firebaseCrashlyticsB;
    FirebaseFirestore firebaseFirestoreB;

    SharedPreferenceUserData sharedPreferenceUserData;
    SharedPreferenceSubjectsUser sharedPreferenceSubjectsUser;

    ImageView image_view_seccion_oneB;
    TextView text_view_title_seccion_oneB;
    TextView text_view_description_seccion_oneB;

    MaterialButton material_button_seccion_one_continueB;
    MaterialButton material_button_seccion_one_see_moreB;
    MaterialButton material_button_seccion_one_linkB;

    FloatingActionButton floatingActionButton_seccion_one;

    private String seeMore;
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seccion_one);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuthB = FirebaseAuth.getInstance();
        firebaseFirestoreB= FirebaseFirestore.getInstance();
        firebaseCrashlyticsB= FirebaseCrashlytics.getInstance();

        image_view_seccion_oneB=findViewById(R.id.image_view_seccion_one);
        text_view_title_seccion_oneB=findViewById(R.id.text_view_title_seccion_one);
        text_view_description_seccion_oneB=findViewById(R.id.text_view_description_seccion_one);

        material_button_seccion_one_continueB=findViewById(R.id.material_button_seccion_one_continue);
        material_button_seccion_one_see_moreB=findViewById(R.id.material_button_seccion_one_see_more);
        material_button_seccion_one_linkB=findViewById(R.id.material_button_seccion_one_link);

        floatingActionButton_seccion_one=findViewById(R.id.material_floating_seccion_one);

        sharedPreferenceUserData = new SharedPreferenceUserData(this);
        sharedPreferenceSubjectsUser = new SharedPreferenceSubjectsUser(this);

        setTitle(sharedPreferenceSubjectsUser.getNameSubject());

        material_button_seccion_one_continueB.setOnClickListener(this);
        material_button_seccion_one_see_moreB.setOnClickListener(this);
        material_button_seccion_one_linkB.setOnClickListener(this);

        floatingActionButton_seccion_one.setOnClickListener(this);

       loadData();


    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = firebaseAuthB.getCurrentUser();
        if(firebaseUser != null){
            firebaseUser.reload();
        }

    }

    private String loadIdTeacher(){
        String loadIdTeacher= sharedPreferenceSubjectsUser.getTeacherUser();
        return loadIdTeacher;

    }
    private String loadIdSubjects(){
        String loadIdSubjects= sharedPreferenceSubjectsUser.getSubjectsUser();
        return loadIdSubjects;
    }

    private String loadIdTopic(){
        String loadIdTopic= sharedPreferenceSubjectsUser.getTopicsUser();
        return loadIdTopic;
    }

    private String loadNameTopic(){
        String loadNameTopic= sharedPreferenceSubjectsUser.getNameTopic();
        return loadNameTopic;
    }


    private String emailUser(){
        String emailUser=sharedPreferenceUserData.getEmailUser();
        return emailUser;
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    private void loadData(){
        firebaseFirestoreB.collection("Aula").document(loadIdTeacher()).collection("Subjects").document(loadIdSubjects()).collection("topics").document(loadIdTopic()).collection("Seccion1")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                Seccion seccion= document.toObject(Seccion.class);
                                Glide.with(SeccionOneActivity.this).load(seccion.getPicture()).into(image_view_seccion_oneB);
                                text_view_title_seccion_oneB.setText(seccion.getTitle());
                                text_view_description_seccion_oneB.setText(seccion.getDescription());

                                seeMore= seccion.getVideo();
                                link=seccion.getLink();
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    private void loadDataState(){
        firebaseFirestoreB.collection("Aula").document(loadIdTeacher()).collection("Subjects").document(loadIdSubjects()).collection("topics").document(loadIdTopic()).collection("Seccion2")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                Seccion seccion= document.toObject(Seccion.class);
                                if(seccion.isContent()==false){
                                    loadDataNoContent();
                                }else{
                                    loadDataSecondSeccion();
                                }
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    private void loadDataSecondSeccion(){
        Intent intent = new Intent( SeccionOneActivity.this, SeccionTwoActivity.class);
        startActivity(intent);
    }

    private void loadDataNoContent(){
        Intent intent = new Intent( SeccionOneActivity.this, NoContentActivity.class);
        startActivity(intent);
    }

    private void loadSeeMore(){
        Uri uri= Uri.parse(seeMore);
        Intent intent = new Intent( Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

    private void loadLink(){
        Uri uri= Uri.parse(link);
        Intent intent = new Intent( Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

    private void loadAnnotations(){
        Intent intent = new Intent(SeccionOneActivity.this, AnnotationsNoteActivity.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.material_button_seccion_one_continue:
                loadDataState();
                break;

            case R.id.material_button_seccion_one_see_more:
                loadSeeMore();
                break;

            case R.id.material_button_seccion_one_link:
                loadLink();
                break;
            case R.id.material_floating_seccion_one:
                loadAnnotations();
                break;

        }
    }
}