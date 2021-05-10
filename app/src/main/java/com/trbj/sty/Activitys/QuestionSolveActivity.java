package com.trbj.sty.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.trbj.sty.Adapters.RecycleViewAdapterCourses;
import com.trbj.sty.Adapters.RecycleViewAdapterSolveQuestions;
import com.trbj.sty.Adapters.RecycleViewAdapterTopic;
import com.trbj.sty.Models.SolveQuestion;
import com.trbj.sty.Models.Topic;
import com.trbj.sty.Models.Topics.Question;
import com.trbj.sty.R;
import com.trbj.sty.Shareds.SharedPreferenceSubjectsUser;
import com.trbj.sty.Shareds.SharedPreferenceUserData;

public class QuestionSolveActivity extends AppCompatActivity {

    RecyclerView recyclerView_question_solves;
    RecycleViewAdapterSolveQuestions recycleViewAdapterSolveQuestions;

    FirebaseFirestore firebaseFirestoreB;
    FirebaseAuth firebaseAuthB;
    FirebaseCrashlytics firebaseCrashlyticsB;

    SharedPreferenceSubjectsUser sharedPreferenceSubjectsUser;
    SharedPreferenceUserData sharedPreferenceUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_solve);

        recyclerView_question_solves = findViewById(R.id.recycler_view_question_solve);
        recyclerView_question_solves.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        sharedPreferenceSubjectsUser= new SharedPreferenceSubjectsUser(this);
        sharedPreferenceUserData= new SharedPreferenceUserData(this);

        firebaseAuthB =FirebaseAuth.getInstance();
        firebaseFirestoreB= FirebaseFirestore.getInstance();
        firebaseCrashlyticsB=FirebaseCrashlytics.getInstance();

        loadDataQuestionSolve();
    }

    @Override
    protected void onStart() {
        super.onStart();
        recycleViewAdapterSolveQuestions.startListening();
        FirebaseUser firebaseUser = firebaseAuthB.getCurrentUser();
        if(firebaseUser != null){
            firebaseUser.reload();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        recycleViewAdapterSolveQuestions.startListening();
    }

    /**
     * Inflate the menu; this adds items to the action bar if it is present.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.course, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadDataQuestionSolveSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loadDataQuestionSolveSearch(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_help:

                break;
            case R.id.action_back:
                Intent intent=new Intent(QuestionSolveActivity.this,HomeActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private String loadIdTeacher(){
        String loadIdTeacher= sharedPreferenceSubjectsUser.getTeacherUser();
        System.out.println("loadIdTeacher"+loadIdTeacher);
        return loadIdTeacher;

    }
    private String loadIdSubjects(){
        String loadIdSubjects= sharedPreferenceSubjectsUser.getSubjectsUser();
        System.out.println("idSubject"+loadIdSubjects);
        return loadIdSubjects;
    }



    private void loadDataQuestionSolve(){
        try{
            Query query = firebaseFirestoreB.collection("Aula").document(loadIdTeacher()).collection("Subjects").document(loadIdSubjects()).collection("comments")
                    .whereGreaterThanOrEqualTo("qualification",4);
            FirestoreRecyclerOptions<SolveQuestion> fireStoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<SolveQuestion>().setQuery(query, SolveQuestion.class).build();
            recycleViewAdapterSolveQuestions = new RecycleViewAdapterSolveQuestions(fireStoreRecyclerOptions);
            recycleViewAdapterSolveQuestions.startListening();
            recyclerView_question_solves.setAdapter(recycleViewAdapterSolveQuestions);
        }catch (Exception e){
            Log.w("TAG", "loadDataSubjects in failed", e);
            firebaseCrashlyticsB.log("loadDataSubjects");
            firebaseCrashlyticsB.recordException(e);

        }
    }

    private void loadDataQuestionSolveSearch(String querySearch){
        try{
            Query query = firebaseFirestoreB.collection("Aula").document(loadIdTeacher()).collection("Subjects").document(loadIdSubjects()).collection("comments")
                    .orderBy("question").startAt(querySearch).endAt(querySearch + "\uf8ff");;
            FirestoreRecyclerOptions<SolveQuestion> fireStoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<SolveQuestion>().setQuery(query, SolveQuestion.class).build();
            recycleViewAdapterSolveQuestions = new RecycleViewAdapterSolveQuestions(fireStoreRecyclerOptions);
            recycleViewAdapterSolveQuestions.startListening();
            recyclerView_question_solves.setAdapter(recycleViewAdapterSolveQuestions);
        }catch (Exception e){
            Log.w("TAG", "loadDataSubjects in failed", e);
            firebaseCrashlyticsB.log("loadDataSubjects");
            firebaseCrashlyticsB.recordException(e);

        }
    }

}