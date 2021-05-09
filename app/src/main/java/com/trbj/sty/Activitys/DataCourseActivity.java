package com.trbj.sty.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.trbj.sty.R;
import com.trbj.sty.Shareds.SharedPreferenceSubjectsUser;

public class DataCourseActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseFirestore firebaseFirestoreB;
    FirebaseAuth firebaseAuthB;
    FirebaseCrashlytics firebaseCrashlyticsB;

    TextView text_view_sentence_userB;
    TextView text_view_sentence_authorB;
    MaterialButton material_button_data_course;

    SharedPreferenceSubjectsUser sharedPreferenceSubjectsUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_course);

        sharedPreferenceSubjectsUser = new SharedPreferenceSubjectsUser(this);

        firebaseAuthB =FirebaseAuth.getInstance();
        firebaseFirestoreB= FirebaseFirestore.getInstance();
        firebaseCrashlyticsB=FirebaseCrashlytics.getInstance();

        text_view_sentence_userB =findViewById(R.id.text_view_sentence_user);
        text_view_sentence_authorB=findViewById(R.id.text_view_sentence_author);
        material_button_data_course=findViewById(R.id.material_button_data_course);

        material_button_data_course.setOnClickListener(this);

        loadSentence(loadAuthorEmail());
    }


    private String loadAuthorEmail(){
        String emailAuthor = sharedPreferenceSubjectsUser.getTeacherUser();
        return emailAuthor;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.material_button_data_course:
                loadTopics();
                break;
        }
    }
}