package com.trbj.sty.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.trbj.sty.Activitys.DataCourseActivity;
import com.trbj.sty.Activitys.QuestionSolveActivity;
import com.trbj.sty.Models.Courses;
import com.trbj.sty.R;
import com.trbj.sty.Shareds.SharedPreferenceSubjectsUser;

public class RecycleViewAdapterCoursesQuestion extends FirestoreRecyclerAdapter<Courses,RecycleViewAdapterCoursesQuestion.ViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RecycleViewAdapterCoursesQuestion(@NonNull FirestoreRecyclerOptions<Courses> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecycleViewAdapterCoursesQuestion.ViewHolder holder, int position, @NonNull Courses courses) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());

        String documentId = documentSnapshot.getId();
        String nameCourse = courses.getNameSubject();
        String authorCourse = courses.getIdTeacher();
        String idCourse = courses.getIdSubject();
        holder.text_view_name_list_course_questionB.setText(nameCourse);
        holder.material_card_view_list_course_questionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity appCompatActivity= (AppCompatActivity)v.getContext();
                Intent intent = new Intent(appCompatActivity, QuestionSolveActivity.class);
                SharedPreferenceSubjectsUser sharedPreferenceSubjectsUser= new SharedPreferenceSubjectsUser(appCompatActivity);
                sharedPreferenceSubjectsUser.setDataUserTeacher(authorCourse);
                sharedPreferenceSubjectsUser.setDataUserSubjects(idCourse);
                sharedPreferenceSubjectsUser.setNameSubject(nameCourse);
                appCompatActivity.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public RecycleViewAdapterCoursesQuestion.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_course_question,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView material_card_view_list_course_questionB;
        TextView text_view_name_list_course_questionB;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            material_card_view_list_course_questionB =itemView.findViewById(R.id.material_card_view_list_course_question);
            text_view_name_list_course_questionB=itemView.findViewById(R.id.text_view_name_list_course_question);
        }
    }
}