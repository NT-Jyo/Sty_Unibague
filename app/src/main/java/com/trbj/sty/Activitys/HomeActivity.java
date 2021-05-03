package com.trbj.sty.Activitys;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.Lottie;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.firestore.FirebaseFirestore;
import com.trbj.sty.R;
import com.trbj.sty.Shareds.SharedPreferenceUserData;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;

    FirebaseAuth firebaseAuthB;
    FirebaseFirestore firebaseFirestoreB;
    FirebaseCrashlytics firebaseCrashlyticsB;

    SharedPreferenceUserData sharedPreferenceUserData;

    TextView textView_name_header,textView_email_header;
    ImageView imageView_header;

    /**
     * mAppBarConfiguration Passing each menu ID as a set of Ids because each
     * menu should be considered as top level destinations.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferenceUserData = new SharedPreferenceUserData(this);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        textView_name_header=(TextView)headerView.findViewById(R.id.text_view_name_header);
        textView_email_header =(TextView)headerView.findViewById(R.id.text_view_email_header);
        imageView_header=(ImageView)headerView.findViewById(R.id.image_view_nav_header);


        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_Course, R.id.nav_annotations, R.id.nav_contributions)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        firebaseAuthB =FirebaseAuth.getInstance();
        firebaseFirestoreB=FirebaseFirestore.getInstance();
        firebaseCrashlyticsB = FirebaseCrashlytics.getInstance();

        loadUserData();
    }

    /**
     * Inflate the menu; this adds items to the action bar if it is present.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * action exit = remove preferences user Data, finish App
     * @param item
     * @return
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_exit:
                Toast.makeText(HomeActivity.this, "¡Nos vemos, cuídate!", Toast.LENGTH_SHORT).show();
                firebaseAuthB.signOut();
                sharedPreferenceUserData.deleteData();
                finishAffinity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * load SharedPreference user data
     */
    private void loadUserData(){

        textView_name_header.setText(sharedPreferenceUserData.getNameUser());
        textView_email_header.setText(sharedPreferenceUserData.getEmailUser());
        Glide.with(HomeActivity.this).load(sharedPreferenceUserData.getPhotoUser()).circleCrop().into(imageView_header);
    }


}