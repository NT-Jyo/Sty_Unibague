package com.trbj.sty.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.firestore.FirebaseFirestore;
import com.trbj.sty.Models.SolveQuestion;
import com.trbj.sty.R;
import com.trbj.sty.Shareds.SharedPreferenceQuestion;
import com.trbj.sty.Shareds.SharedPreferenceSubjectsUser;
import com.trbj.sty.Shareds.SharedPreferenceUserData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseFirestore firebaseFirestoreB;
    FirebaseAuth firebaseAuthB;
    FirebaseCrashlytics firebaseCrashlyticsB;

    SharedPreferenceSubjectsUser sharedPreferenceSubjectsUser;
    SharedPreferenceQuestion sharedPreferenceQuestion;
    SharedPreferenceUserData sharedPreferenceUserData;

    TextView text_view_questionB;
    TextInputEditText text_input_input_solve_questionB;
    TextInputLayout text_input_layout_solve_question;

    MaterialButton material_button_question_sendB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        firebaseAuthB = FirebaseAuth.getInstance();
        firebaseFirestoreB = FirebaseFirestore.getInstance();
        firebaseCrashlyticsB = FirebaseCrashlytics.getInstance();

        text_view_questionB = findViewById(R.id.text_view_question);
        text_input_input_solve_questionB = findViewById(R.id.text_input_input_solve_question);
        text_input_layout_solve_question = findViewById(R.id.text_input_layout_solve_question);

        material_button_question_sendB = findViewById(R.id.material_button_question_send);

        material_button_question_sendB.setOnClickListener(this);
        sharedPreferenceSubjectsUser = new SharedPreferenceSubjectsUser(this);
        sharedPreferenceQuestion = new SharedPreferenceQuestion(this);
        sharedPreferenceUserData = new SharedPreferenceUserData(this);

        text_view_questionB.setText(loadQuestion());
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = firebaseAuthB.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.reload();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private String loadIdTeacher() {
        String loadIdTeacher = sharedPreferenceSubjectsUser.getTeacherUser();
        return loadIdTeacher;

    }

    private String loadIdSubjects() {
        String loadIdSubjects = sharedPreferenceSubjectsUser.getSubjectsUser();
        return loadIdSubjects;
    }


    private String loadQuestion() {
        String question = sharedPreferenceQuestion.getQuestionUser();
        return question;
    }

    private String loadEmailUser() {
        String emailUser = sharedPreferenceUserData.getEmailUser();
        return emailUser;
    }

    private String loadNameUser() {
        String nameUser = sharedPreferenceUserData.getNameUser();
        return nameUser;
    }

    private String loadNameTopic() {
        String nameTopic = sharedPreferenceSubjectsUser.getNameTopic();
        return nameTopic;
    }

    private String loadIdQuestion(){
        String idQuestion = sharedPreferenceQuestion.getQuestionIdUser();
        return  idQuestion;
    }

    private String date() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        Date date = new Date();
        String dateB = dateFormat.format(date);
        return dateB;
    }

    private void loadProgress() {
        Map<String, Object> progressMap = new HashMap<>();
        progressMap.put("date", date());
        progressMap.put(loadNameTopic(), true);
        firebaseFirestoreB.collection("Aula").document(loadIdTeacher()).collection("Subjects").document(loadIdSubjects()).collection("progress").document(loadEmailUser()).update(progressMap)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "DocumentSnapshot successfully written!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("TAG", "Error writing document", e);
            }
        });

    }

    private void registerQuestion() {
        try {
            String saveQuestion = text_input_input_solve_questionB.getText().toString();
            if (!saveQuestion.isEmpty()) {
                SolveQuestion solveQuestion = new SolveQuestion(0.0, loadQuestion(), saveQuestion, loadEmailUser(), date(), loadNameUser());
                firebaseFirestoreB.collection("Aula").document(loadIdTeacher()).collection("Subjects").document(loadIdSubjects()).collection("comments").document(loadEmailUser()+loadIdQuestion()).set(solveQuestion).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loadProgress();
                        Toast.makeText(QuestionActivity.this, "Respuesta enviada", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                });
            } else {
                text_input_layout_solve_question.setError("Y tu respuesta?");
                text_input_layout_solve_question.requestFocus();
            }
        } catch (Exception e) {
            Log.w("TAG", "registerQuestion in failed", e);
            firebaseCrashlyticsB.log("registerQuestion");
            firebaseCrashlyticsB.recordException(e);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.material_button_question_send:
                registerQuestion();
                Toast.makeText(QuestionActivity.this, "NO me presiones", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}