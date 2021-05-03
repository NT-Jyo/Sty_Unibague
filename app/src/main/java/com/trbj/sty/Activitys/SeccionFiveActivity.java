package com.trbj.sty.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.trbj.sty.Adapters.RecycleViewAdapterQuestions;
import com.trbj.sty.Adapters.RecycleViewAdapterTeachers;
import com.trbj.sty.Models.Teacher;
import com.trbj.sty.Models.Topics.Question;
import com.trbj.sty.R;
import com.trbj.sty.Shareds.SharedPreferenceFaculty;
import com.trbj.sty.Shareds.SharedPreferenceQuestion;
import com.trbj.sty.Shareds.SharedPreferenceSubjectsUser;

public class SeccionFiveActivity extends AppCompatActivity {

    RecyclerView recyclerView_questions;
    RecycleViewAdapterQuestions recycleViewAdapterQuestions;
    FirebaseFirestore firebaseFirestoreB;
    FirebaseAuth firebaseAuthB;
    FirebaseCrashlytics firebaseCrashlyticsB;

    SharedPreferenceSubjectsUser sharedPreferenceSubjectsUser;
    SharedPreferenceQuestion sharedPreferenceQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seccion_five);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuthB =FirebaseAuth.getInstance();
        firebaseFirestoreB= FirebaseFirestore.getInstance();
        firebaseCrashlyticsB=FirebaseCrashlytics.getInstance();

        recyclerView_questions = findViewById(R.id.recycle_view_questions);
        recyclerView_questions.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        sharedPreferenceSubjectsUser = new SharedPreferenceSubjectsUser(this);
        sharedPreferenceQuestion = new SharedPreferenceQuestion(this);
        loadDataQuestions();
        setTitle("Preguntas");
    }

    @Override
    protected void onStart() {
        super.onStart();
        recycleViewAdapterQuestions.startListening();
        FirebaseUser firebaseUser = firebaseAuthB.getCurrentUser();
        if(firebaseUser != null){
            firebaseUser.reload();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        recycleViewAdapterQuestions.startListening();
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

    private String loadQuestion(){
        String loadIdTopic= sharedPreferenceSubjectsUser.getTopicsUser();
        return loadIdTopic;
    }

    public String loadSolve(){
        String loadIdTopic= sharedPreferenceSubjectsUser.getTopicsUser();
        return loadIdTopic;
    }


    private void loadDataQuestions(){
        try{

            Query query = firebaseFirestoreB.collection("Aula").document(loadIdTeacher()).collection("Subjects").document(loadIdSubjects()).collection("topics").document(loadIdTopic()).collection("Seccion5");
            FirestoreRecyclerOptions<Question> fireStoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Question>().setQuery(query,Question.class).build();
            recycleViewAdapterQuestions= new RecycleViewAdapterQuestions(fireStoreRecyclerOptions);
            recyclerView_questions.setAdapter(recycleViewAdapterQuestions);
        }catch (Exception e){
            Log.w("TAG", "loadDataQuestions in failed", e);
            firebaseCrashlyticsB.log("loadDataQuestions");
            firebaseCrashlyticsB.recordException(e);

        }
    }


}