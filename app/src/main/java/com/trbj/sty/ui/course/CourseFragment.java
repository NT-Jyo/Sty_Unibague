package com.trbj.sty.ui.course;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.trbj.sty.Activitys.TeachersActivity;
import com.trbj.sty.R;
import com.trbj.sty.Shareds.SharedPreferenceFaculty;

public class CourseFragment extends Fragment implements  View.OnClickListener{


    MaterialButton buttonEngineering;
    MaterialButton buttonEconomy;
    MaterialButton buttonMathematics;
    MaterialButton buttonLaw;
    MaterialButton buttonHumanities;
    SharedPreferenceFaculty sharedPreferenceFaculty;
    FirebaseCrashlytics firebaseCrashlyticsB;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_course, container, false);
        buttonEngineering = (MaterialButton) root.findViewById(R.id.material_button_faculty_engineering);
        buttonEconomy =(MaterialButton)root.findViewById(R.id.material_button_faculty_economic_sciences);
        buttonMathematics =(MaterialButton)root.findViewById(R.id.material_button_faculty_natural_mathematical_sciences);
        buttonLaw=(MaterialButton)root.findViewById(R.id.material_button_faculty_law_political_sciences);
        buttonHumanities=(MaterialButton)root.findViewById(R.id.material_button_faculty_humanities_art);

        firebaseCrashlyticsB=FirebaseCrashlytics.getInstance();
        sharedPreferenceFaculty = new SharedPreferenceFaculty(getActivity().getApplicationContext());

        buttonEngineering.setOnClickListener(this);
        buttonEconomy.setOnClickListener(this);
        buttonMathematics.setOnClickListener(this);
        buttonLaw.setOnClickListener(this);
        buttonHumanities.setOnClickListener(this);
        return root;
    }

    private void loadCourse(){
        try{
            Intent intent = new Intent(getActivity(), TeachersActivity.class);
            startActivity(intent);

        }catch (Exception e){
            firebaseCrashlyticsB.log("loadCourse");
            firebaseCrashlyticsB.recordException(e);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.material_button_faculty_engineering:
                sharedPreferenceFaculty.setDataUser("Facultad de Ingeniería");
                loadCourse();
                break;
            case R.id.material_button_faculty_economic_sciences:
                sharedPreferenceFaculty.setDataUser("Facultad de Ciencias Económicas y Administrativas");
                loadCourse();
                break;
            case R.id.material_button_faculty_natural_mathematical_sciences:
                sharedPreferenceFaculty.setDataUser("Facultad de Ciencias Naturales y Matemáticas");
                loadCourse();
                break;
            case R.id.material_button_faculty_law_political_sciences:
                sharedPreferenceFaculty.setDataUser("Facultad de Derecho y Ciencias Políticas");
                loadCourse();
                break;
            case R.id.material_button_faculty_humanities_art:
                sharedPreferenceFaculty.setDataUser("Facultad de Humanidades, Artes y Ciencias Políticas");
                loadCourse();
                break;
        }
    }
}