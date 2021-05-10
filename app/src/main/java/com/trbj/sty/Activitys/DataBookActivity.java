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
import com.trbj.sty.Adapters.RecycleViewAdapterAnnotationDataBookTopic;
import com.trbj.sty.Adapters.RecycleViewAdapterTopic;
import com.trbj.sty.Models.Topic;
import com.trbj.sty.R;
import com.trbj.sty.Shareds.SharedPreferenceSubjectsUser;

public class DataBookActivity extends AppCompatActivity {


    RecyclerView recyclerView_data_book_topic;
    RecycleViewAdapterAnnotationDataBookTopic recycleViewAdapterAnnotationDataBookTopic;

    FirebaseFirestore firebaseFirestoreB;
    FirebaseAuth firebaseAuthB;
    FirebaseCrashlytics firebaseCrashlyticsB;

    SharedPreferenceSubjectsUser sharedPreferenceSubjectsUser;

    //TODO TOPICS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_book);

        recyclerView_data_book_topic = findViewById(R.id.recycle_view_data_book_topic);
        recyclerView_data_book_topic.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        firebaseAuthB =FirebaseAuth.getInstance();
        firebaseFirestoreB= FirebaseFirestore.getInstance();
        firebaseCrashlyticsB=FirebaseCrashlytics.getInstance();

        sharedPreferenceSubjectsUser = new SharedPreferenceSubjectsUser(this);

        loadDateTopic();

    }

    @Override
    protected void onStart() {
        super.onStart();
        recycleViewAdapterAnnotationDataBookTopic.startListening();
        FirebaseUser firebaseUser = firebaseAuthB.getCurrentUser();
        if(firebaseUser != null){
            firebaseUser.reload();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        recycleViewAdapterAnnotationDataBookTopic.startListening();
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
                loadDateTopicsSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loadDateTopicsSearch(newText);
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


    private String loadIdTeacher(){
        String loadIdTeacher= sharedPreferenceSubjectsUser.getTeacherUser();
        return loadIdTeacher;

    }
    private String loadIdSubjects(){
        String loadIdSubjects= sharedPreferenceSubjectsUser.getSubjectsUser();
        return loadIdSubjects;
    }

    private void loadDateTopic(){
        try{
            Query query = firebaseFirestoreB.collection("Aula").document(loadIdTeacher()).collection("Subjects").document(loadIdSubjects()).collection("topics").orderBy("date");
            FirestoreRecyclerOptions<Topic> fireStoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Topic>().setQuery(query, Topic.class).build();
            recycleViewAdapterAnnotationDataBookTopic = new RecycleViewAdapterAnnotationDataBookTopic(fireStoreRecyclerOptions);
            recyclerView_data_book_topic.setAdapter(recycleViewAdapterAnnotationDataBookTopic);
        }catch (Exception e){
            Log.w("TAG", "loadDataSubjects in failed", e);
            firebaseCrashlyticsB.log("loadDataSubjects");
            firebaseCrashlyticsB.recordException(e);

        }
    }

    private void loadDateTopicsSearch(String querySearch) {
        try{
            Query query = firebaseFirestoreB.collection("Aula").document(loadIdTeacher()).collection("Subjects").document(loadIdSubjects()).collection("topics").orderBy("name").startAt(querySearch).endAt(querySearch+"\uf8ff");;
            FirestoreRecyclerOptions<Topic> fireStoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Topic>().setQuery(query, Topic.class).build();
            recycleViewAdapterAnnotationDataBookTopic = new RecycleViewAdapterAnnotationDataBookTopic(fireStoreRecyclerOptions);
            recycleViewAdapterAnnotationDataBookTopic.startListening();
            recyclerView_data_book_topic.setAdapter(recycleViewAdapterAnnotationDataBookTopic);
        }catch (Exception e){
            Log.w("TAG", "loadDataSubjects in failed", e);
            firebaseCrashlyticsB.log("loadDataSubjects");
            firebaseCrashlyticsB.recordException(e);

        }


    }
}