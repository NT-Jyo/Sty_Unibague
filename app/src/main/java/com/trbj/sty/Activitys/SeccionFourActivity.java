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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.trbj.sty.Models.Topics.Introduction;
import com.trbj.sty.Models.Topics.Links;
import com.trbj.sty.Models.Topics.Seccion;
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

    FloatingActionButton floatingActionButton_seccion_four;

    private String link1;
    private String link2;

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
        floatingActionButton_seccion_four=findViewById(R.id.material_floating_seccion_four);
        sharedPreferenceSubjectsUser = new SharedPreferenceSubjectsUser(this);

        material_button_seccion_four_continueB.setOnClickListener(this);
        floatingActionButton_seccion_four.setOnClickListener(this);

        text_view_seccion_four_name1_linkB.setOnClickListener(this);
        text_view_seccion_four_name2_linkB.setOnClickListener(this);

        setTitle(sharedPreferenceSubjectsUser.getNameSubject());

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
                                link1=links.getLink1();
                                link2=links.getLink2();
                                System.out.println("LOS CALCULOS SON LOS SIGUIENTES"+document.getData().size());
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void loadDataState(){
        firebaseFirestoreB.collection("Aula").document(loadIdTeacher()).collection("Subjects").document(loadIdSubjects()).collection("topics").document(loadIdTopic()).collection("Seccion5")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().size()==0){
                                loadDataNoContent();
                            }else{
                                loadDataFiveSeccion();
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    private void loadDataFiveSeccion(){
        Intent intent= new Intent(SeccionFourActivity.this,SeccionFiveActivity.class);
        startActivity(intent);
    }
    private void loadDataNoContent(){
        Intent intent = new Intent( SeccionFourActivity.this, NoContentActivity.class);
        startActivity(intent);
    }

    private void loadLink1(){
        Uri uri= Uri.parse(link1);
        Intent intent = new Intent( Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

    private void loadLink2(){
        Uri uri= Uri.parse(link2);
        Intent intent = new Intent( Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

    private void loadAnnotations(){
        Intent intent = new Intent(SeccionFourActivity.this, AnnotationsNoteActivity.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.material_button_seccion_four_continue:
                loadDataState();
                break;
            case R.id.text_view_seccion_four_name1_link:
                loadLink1();
                break;
            case R.id.text_view_seccion_four_name2_link:
                loadLink2();
                break;
            case R.id.material_floating_seccion_four:
                loadAnnotations();
                break;
        }
    }
}