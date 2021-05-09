package com.trbj.sty.Activitys;

import androidx.appcompat.app.AppCompatActivity;
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
import com.trbj.sty.Adapters.RecycleViewAdapterCourses;
import com.trbj.sty.Adapters.RecycleViewAdapterCoursesQuestion;
import com.trbj.sty.Adapters.RecycleViewAdapterSolveQuestions;
import com.trbj.sty.Models.Courses;
import com.trbj.sty.R;
import com.trbj.sty.Shareds.SharedPreferenceSubjectsUser;
import com.trbj.sty.Shareds.SharedPreferenceUserData;

public class CourseQuestionActivity extends AppCompatActivity {

    RecyclerView recyclerView_courses_question;
    RecycleViewAdapterCoursesQuestion recycleViewAdapterCoursesQuestion;

    FirebaseFirestore firebaseFirestoreB;
    FirebaseAuth firebaseAuthB;
    FirebaseCrashlytics firebaseCrashlyticsB;

    SharedPreferenceSubjectsUser sharedPreferenceSubjectsUser;
    SharedPreferenceUserData sharedPreferenceUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_question);

        sharedPreferenceSubjectsUser= new SharedPreferenceSubjectsUser(this);
        sharedPreferenceUserData= new SharedPreferenceUserData(this);

        recyclerView_courses_question = findViewById(R.id.recycler_view_course_question);
        recyclerView_courses_question.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        firebaseAuthB =FirebaseAuth.getInstance();
        firebaseFirestoreB= FirebaseFirestore.getInstance();
        firebaseCrashlyticsB=FirebaseCrashlytics.getInstance();

        loadDataCourses();
    }

    @Override
    protected void onStart() {
        super.onStart();
        recycleViewAdapterCoursesQuestion.startListening();
        FirebaseUser firebaseUser = firebaseAuthB.getCurrentUser();
        if(firebaseUser != null){
            firebaseUser.reload();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        recycleViewAdapterCoursesQuestion.startListening();
    }


    private String loadUserEmail(){
        String emailUser = sharedPreferenceUserData.getEmailUser();
        return emailUser;
    }



    private void loadDataCourses(){
        try{

            String[] idUser = loadUserEmail().split("\\@");

            if (idUser[1].equals("estudiantesunibague.edu.co") || idUser[1].equals("unibague.edu.co")) {
                Query query = firebaseFirestoreB.collection("Unibague").document(loadUserEmail()).collection("Course");
                FirestoreRecyclerOptions<Courses> fireStoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Courses>().setQuery(query,Courses.class).build();
                recycleViewAdapterCoursesQuestion = new RecycleViewAdapterCoursesQuestion(fireStoreRecyclerOptions);
                recyclerView_courses_question.setAdapter(recycleViewAdapterCoursesQuestion);
            } else {
                Query query = firebaseFirestoreB.collection("Usuario").document(loadUserEmail()).collection("Course");
                FirestoreRecyclerOptions<Courses> fireStoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Courses>().setQuery(query,Courses.class).build();
                recycleViewAdapterCoursesQuestion = new RecycleViewAdapterCoursesQuestion(fireStoreRecyclerOptions);
                recyclerView_courses_question.setAdapter(recycleViewAdapterCoursesQuestion);
            }

        }catch (Exception e){
            Log.w("TAG", "loadDataSubjects in failed", e);
            firebaseCrashlyticsB.log("loadDataSubjects");
            firebaseCrashlyticsB.recordException(e);

        }
    }
}