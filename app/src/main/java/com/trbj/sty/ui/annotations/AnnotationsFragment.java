package com.trbj.sty.ui.annotations;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.card.MaterialCardView;
import com.trbj.sty.Activitys.CourseQuestionActivity;
import com.trbj.sty.Activitys.CoursesActivity;
import com.trbj.sty.R;

public class AnnotationsFragment extends Fragment  implements  View.OnClickListener{

   MaterialCardView cardView_courses;
   MaterialCardView card_view_annotation_course_questionB;

   public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

       View root = inflater.inflate(R.layout.fragment_annotations, container, false);
       cardView_courses= (MaterialCardView) root.findViewById(R.id.card_view_annotation_course);
       card_view_annotation_course_questionB=(MaterialCardView)root.findViewById(R.id.card_view_annotation_course_question);

       card_view_annotation_course_questionB.setOnClickListener(this);
       cardView_courses.setOnClickListener(this);
       return root;
    }

    private void loadCourse(){
        Intent intent = new Intent(getContext(), CoursesActivity.class);
        startActivity(intent);
    }

    private void loadQuestionCourse(){
       Intent intent = new Intent(getContext(), CourseQuestionActivity.class);
       startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_view_annotation_course:
                loadCourse();
                break;
            case  R.id.card_view_annotation_course_question:
                loadQuestionCourse();
                break;
        }
    }
}