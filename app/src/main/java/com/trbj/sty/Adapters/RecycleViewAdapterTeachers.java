package com.trbj.sty.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.media.TimedText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.internal.InternalTokenProvider;
import com.trbj.sty.Activitys.HomeActivity;
import com.trbj.sty.Activitys.SubjectsActivity;
import com.trbj.sty.Models.Teacher;
import com.trbj.sty.R;
import com.trbj.sty.Shareds.SharedPreferenceSubjectsUser;

public class RecycleViewAdapterTeachers extends FirestoreRecyclerAdapter<Teacher, RecycleViewAdapterTeachers.ViewHolder> {

    SharedPreferenceSubjectsUser sharedPreferenceSubjectsUser;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.     *
     * @param options
     */
    public RecycleViewAdapterTeachers(@NonNull FirestoreRecyclerOptions<Teacher> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Teacher teacher) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());

        String idDocument =documentSnapshot.getId();
        String nameTeacher = teacher.getDisplayName();
        String programTeacher= teacher.getProgram();
        String subjectsTeacher = teacher.getSubjects();
        String emailTeacher = teacher.getEmail();
        String descriptionTeacher = teacher.getDescription();
        String imageTeacher=teacher.getPhotoURL();
        int gender=teacher.getGender();

        holder.textView_nameTeacher.setText(nameTeacher);
        holder.textView_programTeacher.setText(programTeacher);
        holder.textView_emailTeacher.setText(emailTeacher);
        holder.textView_subjectsTeacher.setText(subjectsTeacher);
        holder.textView_descriptionTeacher.setText(descriptionTeacher);
        Glide.with(holder.imageView_teacher_photo.getContext()).load(imageTeacher).circleCrop().into(holder.imageView_teacher_photo);



        holder.materialCardView_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCompatActivity activity= (AppCompatActivity)v.getContext();
                sharedPreferenceSubjectsUser = new SharedPreferenceSubjectsUser(activity);
                sharedPreferenceSubjectsUser.setDataUserTeacher(emailTeacher);
                sharedPreferenceSubjectsUser.setGenderTeacher(gender);
                Intent intent = new Intent(activity,SubjectsActivity.class);
                activity.startActivity(intent);
                Toast.makeText(holder.materialCardView_teacher.getContext(), "Toas test", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_teachers,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView materialCardView_teacher;
        MaterialTextView textView_nameTeacher;
        MaterialTextView textView_programTeacher;
        MaterialTextView textView_subjectsTeacher;
        MaterialTextView textView_emailTeacher;
        MaterialTextView textView_descriptionTeacher;
        ImageView imageView_teacher_photo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            materialCardView_teacher = itemView.findViewById(R.id.material_card_view_teacher);
            textView_nameTeacher =  itemView.findViewById(R.id.text_view_name_teacher);
            textView_programTeacher = itemView.findViewById(R.id.text_view_program_teacher);
            textView_subjectsTeacher=itemView.findViewById(R.id.text_view_subjects_teacher);
            textView_emailTeacher=itemView.findViewById(R.id.text_view_email_teacher);
            textView_descriptionTeacher=itemView.findViewById(R.id.text_view_description_teacher);
            imageView_teacher_photo = itemView.findViewById(R.id.image_view_teacher);
        }
    }
}
