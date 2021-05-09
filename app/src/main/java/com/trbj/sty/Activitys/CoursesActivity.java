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
import com.trbj.sty.Adapters.RecycleViewAdapterTeachers;
import com.trbj.sty.Adapters.RecycleViewAdapterTopic;
import com.trbj.sty.Models.Courses;
import com.trbj.sty.Models.Teacher;
import com.trbj.sty.Models.Topic;
import com.trbj.sty.R;
import com.trbj.sty.Shareds.SharedPreferenceSubjectsUser;
import com.trbj.sty.Shareds.SharedPreferenceUserData;

public class CoursesActivity extends AppCompatActivity {

    RecyclerView recyclerView_courses;
    RecycleViewAdapterCourses recycleViewAdapterCourses;

    FirebaseFirestore firebaseFirestoreB;
    FirebaseAuth firebaseAuthB;
    FirebaseCrashlytics firebaseCrashlyticsB;

    SharedPreferenceSubjectsUser sharedPreferenceSubjectsUser;
    SharedPreferenceUserData sharedPreferenceUserData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        sharedPreferenceSubjectsUser= new SharedPreferenceSubjectsUser(this);
        sharedPreferenceUserData= new SharedPreferenceUserData(this);

        recyclerView_courses = findViewById(R.id.recycler_view_courses);
        recyclerView_courses.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        firebaseAuthB =FirebaseAuth.getInstance();
        firebaseFirestoreB= FirebaseFirestore.getInstance();
        firebaseCrashlyticsB=FirebaseCrashlytics.getInstance();

        loadDataCourses();
    }

    @Override
    protected void onStart() {
        super.onStart();
        recycleViewAdapterCourses.startListening();
        FirebaseUser firebaseUser = firebaseAuthB.getCurrentUser();
        if(firebaseUser != null){
            firebaseUser.reload();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        recycleViewAdapterCourses.startListening();
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
                recycleViewAdapterCourses = new RecycleViewAdapterCourses(fireStoreRecyclerOptions);
                recyclerView_courses.setAdapter(recycleViewAdapterCourses);
            } else {
                Query query = firebaseFirestoreB.collection("Usuario").document(loadUserEmail()).collection("Course");
                FirestoreRecyclerOptions<Courses> fireStoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Courses>().setQuery(query,Courses.class).build();
                recycleViewAdapterCourses = new RecycleViewAdapterCourses(fireStoreRecyclerOptions);
                recyclerView_courses.setAdapter(recycleViewAdapterCourses);
            }

        }catch (Exception e){
            Log.w("TAG", "loadDataSubjects in failed", e);
            firebaseCrashlyticsB.log("loadDataSubjects");
            firebaseCrashlyticsB.recordException(e);

        }
    }
}