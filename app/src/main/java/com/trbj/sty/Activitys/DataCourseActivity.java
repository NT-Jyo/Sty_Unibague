package com.trbj.sty.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.trbj.sty.Adapters.RecycleViewAdapterAnnotationCourse;
import com.trbj.sty.Models.Courses;
import com.trbj.sty.Models.DialogLike;
import com.trbj.sty.R;
import com.trbj.sty.Shareds.SharedPreferenceSubjectsUser;
import com.trbj.sty.Shareds.SharedPreferenceUserData;

public class DataCourseActivity extends AppCompatActivity implements View.OnClickListener, DialogLike.DialogResult {

    FirebaseFirestore firebaseFirestoreB;
    FirebaseAuth firebaseAuthB;
    FirebaseCrashlytics firebaseCrashlyticsB;

    TextView text_view_sentence_userB;
    TextView text_view_sentence_authorB;
    MaterialButton material_button_data_course;

    SharedPreferenceSubjectsUser sharedPreferenceSubjectsUser;
    SharedPreferenceUserData sharedPreferenceUserData;

    LottieAnimationView lottieAnimationView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_course);


        sharedPreferenceUserData= new SharedPreferenceUserData(this);
        sharedPreferenceSubjectsUser = new SharedPreferenceSubjectsUser(this);
        context= this;

        firebaseAuthB =FirebaseAuth.getInstance();
        firebaseFirestoreB= FirebaseFirestore.getInstance();
        firebaseCrashlyticsB=FirebaseCrashlytics.getInstance();

        text_view_sentence_userB =findViewById(R.id.text_view_sentence_user);
        text_view_sentence_authorB=findViewById(R.id.text_view_sentence_author);
        material_button_data_course=findViewById(R.id.material_button_data_course);

        lottieAnimationView =findViewById(R.id.lottie_animation_data_course_dialog);

        material_button_data_course.setOnClickListener(this);
        lottieAnimationView.setOnClickListener(this);

        loadSentence(loadAuthorEmail());
    }


    private String loadAuthorEmail(){
        String emailAuthor = sharedPreferenceSubjectsUser.getTeacherUser();
        return emailAuthor;
    }

    private String loadIdSubjects(){
        String loadIdSubjects= sharedPreferenceSubjectsUser.getSubjectsUser();
        return loadIdSubjects;
    }

    private void loadSentence(String author){
        DocumentReference docRef = firebaseFirestoreB.collection("fraseDelDia").document(author);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String sentence = document.getString("sentence");
                        text_view_sentence_userB.setText(sentence);
                        text_view_sentence_authorB.setText(author);
                    } else {
                        Toast.makeText(DataCourseActivity.this,"no se encontro la informacion",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    firebaseCrashlyticsB.log("loadSentence");
                    firebaseCrashlyticsB.recordException(task.getException());
                }
            }
        });
    }

    private void loadTopics(){
        Intent intent = new Intent(DataCourseActivity.this,TopicsActivity.class);
        startActivity(intent);
    }

    private void loadDialog(){
        new DialogLike(context,DataCourseActivity.this);
    }

    private boolean stateLiked(){
        boolean stateLiked= sharedPreferenceSubjectsUser.getLikeState();
        return stateLiked;
    }

    private String loadUserEmail(){
        String emailUser = sharedPreferenceUserData.getEmailUser();
        return emailUser;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.material_button_data_course:
                loadTopics();
                break;

            case R.id.lottie_animation_data_course_dialog:
                loadDialog();
                break;
        }
    }

    @Override
    public void resultDialog(String state) {


        if(state=="like"){
               DocumentReference docRef = firebaseFirestoreB.collection("Aula").document(loadAuthorEmail()).collection("Subjects").document(loadIdSubjects());
               docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                       if (task.isSuccessful()) {
                           DocumentSnapshot document = task.getResult();
                           if (document.exists()) {

                               String likes = String.valueOf(document.get("likes"));
                               DocumentReference docRef = firebaseFirestoreB.collection("Aula").document(loadAuthorEmail()).collection("Subjects").document(loadIdSubjects());
                               int convert= Integer.parseInt(likes);
                               int operate = convert+1;
                              docRef.update("likes",operate);
                               Log.d("TAG", "DocumentSnapshot data: " + document.getData() +likes);
                           } else {
                               Log.d("TAG", "No such document");
                           }
                       } else {
                           Log.d("TAG", "get failed with ", task.getException());
                       }
                   }
               });

        }else{
            DocumentReference docRef = firebaseFirestoreB.collection("Aula").document(loadAuthorEmail()).collection("Subjects").document(loadIdSubjects());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {

                            String likes = String.valueOf(document.get("likes"));
                            DocumentReference docRef = firebaseFirestoreB.collection("Aula").document(loadAuthorEmail()).collection("Subjects").document(loadIdSubjects());
                            int convert= Integer.parseInt(likes);
                            int operate = convert+1;
                            docRef.update("notlikes",operate);
                            Log.d("TAG", "DocumentSnapshot data: " + document.getData() +likes);
                        } else {
                            Log.d("TAG", "No such document");
                        }
                    } else {
                        Log.d("TAG", "get failed with ", task.getException());
                    }
                }
            });
        }

    }
}