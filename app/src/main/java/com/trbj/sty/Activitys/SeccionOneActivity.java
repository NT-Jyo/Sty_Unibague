package com.trbj.sty.Activitys;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.trbj.sty.Models.Topics.Introduction;
import com.trbj.sty.Models.Topics.Seccion;
import com.trbj.sty.R;
import com.trbj.sty.Shareds.SharedPreferenceSubjectsUser;

public class SeccionOneActivity extends AppCompatActivity implements  View.OnClickListener{

    FirebaseAuth firebaseAuthB;
    FirebaseCrashlytics firebaseCrashlyticsB;
    FirebaseFirestore firebaseFirestoreB;
    SharedPreferenceSubjectsUser sharedPreferenceSubjectsUser;

    ImageView image_view_seccion_oneB;
    TextView text_view_title_seccion_oneB;
    TextView text_view_description_seccion_oneB;

    MaterialButton material_button_seccion_one_continueB;


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

        sharedPreferenceSubjectsUser = new SharedPreferenceSubjectsUser(this);

        setTitle(sharedPreferenceSubjectsUser.getNameSubject());

        material_button_seccion_one_continueB.setOnClickListener(this);

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





    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.material_button_seccion_one_continue:
                loadDataSecondSeccion();
                break;
        }
    }
}