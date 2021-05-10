package com.trbj.sty.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.trbj.sty.Adapters.RecycleViewAdapterTeachers;
import com.trbj.sty.Models.Teacher;
import com.trbj.sty.R;
import com.trbj.sty.Shareds.SharedPreferenceFaculty;

public class TeachersActivity extends AppCompatActivity {



    RecyclerView recyclerView_teachers;
    RecycleViewAdapterTeachers recycleViewAdapterTeachers;
    FirebaseFirestore firebaseFirestoreB;
    FirebaseAuth firebaseAuthB;
    FirebaseCrashlytics firebaseCrashlyticsB;
    SharedPreferenceFaculty sharedPreferenceFaculty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);

        recyclerView_teachers = findViewById(R.id.recycle_view_teachers);
        recyclerView_teachers.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        firebaseAuthB =FirebaseAuth.getInstance();
        firebaseFirestoreB= FirebaseFirestore.getInstance();
        firebaseCrashlyticsB=FirebaseCrashlytics.getInstance();

        sharedPreferenceFaculty = new SharedPreferenceFaculty(this);

        loadDataTeachers();
    }

    @Override
    protected void onStart() {
        super.onStart();
        recycleViewAdapterTeachers.startListening();
        FirebaseUser firebaseUser = firebaseAuthB.getCurrentUser();
        if(firebaseUser != null){
            firebaseUser.reload();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        recycleViewAdapterTeachers.startListening();
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
                loadDateTeacherSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loadDateTeacherSearch(newText);
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
                Intent intent=new Intent(TeachersActivity.this,HomeActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void loadDataTeachers(){
        try{
            Query query = firebaseFirestoreB.collection("usuariosW").whereEqualTo("faculty",sharedPreferenceFaculty.getFacultyUser());
            FirestoreRecyclerOptions<Teacher> fireStoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Teacher>().setQuery(query,Teacher.class).build();
            recycleViewAdapterTeachers= new RecycleViewAdapterTeachers(fireStoreRecyclerOptions);
            recyclerView_teachers.setAdapter(recycleViewAdapterTeachers);
        }catch (Exception e){
            Log.w("TAG", "loadDataTeachers in failed", e);
            firebaseCrashlyticsB.log("loadDataTeachers");
            firebaseCrashlyticsB.recordException(e);

        }
    }


    private void loadDateTeacherSearch(String querySearch){
        try{
            Query query = firebaseFirestoreB.collection("usuariosW").whereEqualTo("faculty",sharedPreferenceFaculty.getFacultyUser()).orderBy("displayName").startAt(querySearch).endAt(querySearch+"\uf8ff");
            FirestoreRecyclerOptions<Teacher> fireStoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Teacher>().setQuery(query,Teacher.class).build();
            recycleViewAdapterTeachers= new RecycleViewAdapterTeachers(fireStoreRecyclerOptions);
            recycleViewAdapterTeachers.startListening();
            recyclerView_teachers.setAdapter(recycleViewAdapterTeachers);
        }catch (Exception e){
            Log.w("TAG", "loadDateTeacherSearch in failed", e);
            firebaseCrashlyticsB.log("loadDateTeacherSearch");
            firebaseCrashlyticsB.recordException(e);
        }

    }



}