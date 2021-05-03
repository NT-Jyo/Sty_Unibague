package com.trbj.sty.Activitys;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.trbj.sty.Models.Topics.Introduction;
import com.trbj.sty.Models.Topics.Links;
import com.trbj.sty.R;
import com.trbj.sty.Shareds.SharedPreferenceSubjectsUser;

public class SeccionFourActivity extends AppCompatActivity implements  View.OnClickListener {

    FirebaseAuth firebaseAuthB;
    FirebaseCrashlytics firebaseCrashlyticsB;
    FirebaseFirestore firebaseFirestoreB;
    SharedPreferenceSubjectsUser sharedPreferenceSubjectsUser;

    TextView text_view_seccion_four_titleB;
    TextView text_view_seccion_four_name1_linkB;
    TextView text_view_seccion_four_name2_linkB;

    MaterialButton material_button_seccion_four_continueB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seccion_four);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuthB = FirebaseAuth.getInstance();
        firebaseFirestoreB= FirebaseFirestore.getInstance();
        firebaseCrashlyticsB= FirebaseCrashlytics.getInstance();

        text_view_seccion_four_titleB=findViewById(R.id.text_view_seccion_four_title);
        text_view_seccion_four_name1_linkB=findViewById(R.id.text_view_seccion_four_name1_link);
        text_view_seccion_four_name2_linkB=findViewById(R.id.text_view_seccion_four_name2_link);

        material_button_seccion_four_continueB=findViewById(R.id.material_button_seccion_four_continue);

        sharedPreferenceSubjectsUser = new SharedPreferenceSubjectsUser(this);

        material_button_seccion_four_continueB.setOnClickListener(this);

        setTitle(sharedPreferenceSubjectsUser.getNameSubject());

        loadData();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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


    @Override
    public void onStop() {
        super.onStop();
    }

    private void loadData(){
        firebaseFirestoreB.collection("Aula").document(loadIdTeacher()).collection("Subjects").document(loadIdSubjects()).collection("topics").document(loadIdTopic()).collection("Seccion4")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d("TAG", document.getId() + " => " + document.getData());
                                Links links= document.toObject(Links.class);
                                text_view_seccion_four_titleB.setText(links.getTitle());
                                text_view_seccion_four_name1_linkB.setText(links.getNamelink1());
                                text_view_seccion_four_name2_linkB.setText(links.getNamelink2());
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    private void loadDataFourSeccion(){
        Intent intent= new Intent(SeccionFourActivity.this,SeccionFiveActivity.class);
        startActivity(intent);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.material_button_seccion_four_continue:
                loadDataFourSeccion();
                break;
        }
    }
}