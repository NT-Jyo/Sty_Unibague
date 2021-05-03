package com.trbj.sty.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.trbj.sty.Adapters.RecycleViewAdapterSubjects;
import com.trbj.sty.Models.Subjects;
import com.trbj.sty.R;
import com.trbj.sty.Shareds.SharedPreferenceSubjectsUser;

public class SubjectsActivity extends AppCompatActivity {

    RecyclerView recyclerView_subjects;
    RecycleViewAdapterSubjects recycleViewAdapterSubjects;
    FirebaseFirestore firebaseFirestoreB;
    FirebaseAuth firebaseAuthB;
    FirebaseCrashlytics firebaseCrashlyticsB;
    SharedPreferenceSubjectsUser sharedPreferenceSubjectsUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

        recyclerView_subjects = findViewById(R.id.recycle_view_subjects);
        recyclerView_subjects.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        firebaseAuthB =FirebaseAuth.getInstance();
        firebaseFirestoreB= FirebaseFirestore.getInstance();
        firebaseCrashlyticsB=FirebaseCrashlytics.getInstance();

        sharedPreferenceSubjectsUser= new SharedPreferenceSubjectsUser(this);


        loadDataSubjects();
    }

    @Override
    protected void onStart() {
        super.onStart();
        recycleViewAdapterSubjects.startListening();
        FirebaseUser firebaseUser = firebaseAuthB.getCurrentUser();
        if(firebaseUser != null){
            firebaseUser.reload();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        recycleViewAdapterSubjects.startListening();
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
                loadDateSubjectsSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loadDateSubjectsSearch(newText);
                return false;
            }
        });

        return true;
    }
    /**
     * action exit = remove preferences user Data, finish App
     * @param item
     * @return
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_help:

                break;
            case R.id.action_back:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public String loadIdDocument(){
        String idDocument = sharedPreferenceSubjectsUser.getTeacherUser();
        return idDocument;
    }


    private void loadDataSubjects() {
        try{
            Query query = firebaseFirestoreB.collection("Aula").document(loadIdDocument()).collection("Subjects");
            FirestoreRecyclerOptions<Subjects> fireStoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Subjects>().setQuery(query,Subjects.class).build();
            recycleViewAdapterSubjects = new RecycleViewAdapterSubjects(fireStoreRecyclerOptions);
            recyclerView_subjects.setAdapter(recycleViewAdapterSubjects);
        }catch (Exception e){
            Log.w("TAG", "loadDataSubjects in failed", e);
            firebaseCrashlyticsB.log("loadDataSubjects");
            firebaseCrashlyticsB.recordException(e);
        }
    }

    private void loadDateSubjectsSearch(String nameSubject ){
        try{
            Query query = firebaseFirestoreB.collection("Aula").document(loadIdDocument()).collection("Subjects").orderBy("nameSubject").startAt(nameSubject).endAt(nameSubject+"\uf8ff");;
            FirestoreRecyclerOptions<Subjects> fireStoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Subjects>().setQuery(query,Subjects.class).build();
            recycleViewAdapterSubjects = new RecycleViewAdapterSubjects(fireStoreRecyclerOptions);
            recycleViewAdapterSubjects.startListening();
            recyclerView_subjects.setAdapter(recycleViewAdapterSubjects);
        }catch (Exception e){
            Log.w("TAG", "loadDateSubjectsSearch in failed", e);
            firebaseCrashlyticsB.log("loadDateSubjectsSearch");
            firebaseCrashlyticsB.recordException(e);
        }

    }
}