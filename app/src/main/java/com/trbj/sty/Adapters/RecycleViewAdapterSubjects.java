package com.trbj.sty.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.trbj.sty.Activitys.ConfirmRegistrationActivity;
import com.trbj.sty.Activitys.TopicsActivity;
import com.trbj.sty.Models.Subjects;
import com.trbj.sty.R;
import com.trbj.sty.Shareds.SharedPreferenceSubjectsUser;
import com.trbj.sty.Shareds.SharedPreferenceUserData;

public class RecycleViewAdapterSubjects extends  FirestoreRecyclerAdapter<Subjects,RecycleViewAdapterSubjects.ViewHolder> {


    SharedPreferenceSubjectsUser sharedPreferenceSubjectsUser;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RecycleViewAdapterSubjects(@NonNull FirestoreRecyclerOptions<Subjects> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Subjects subjects) {

        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());

        String idDocument =documentSnapshot.getId();
        String nameSubjects = subjects.getNameSubject();
        double likesSubjects= subjects.getLikes();
        double commentsSubjects = subjects.getComments();
        double studentsSubjects = subjects.getStudents();
        double themesSubjects = subjects.getThemes();
        String imageSubjects=subjects.getPhotoURL();


        holder.materialTextView_subjects_name.setText(nameSubjects);
        holder.materialTextView_subjects_likes.setText(String.valueOf(likesSubjects));
        holder.materialTextView_subjects_comments.setText(String.valueOf(commentsSubjects)+ " Comentarios");
        holder.materialTextView_subjects_students.setText(String.valueOf(studentsSubjects));
        holder.materialTextView_subjects_themes.setText(String.valueOf(themesSubjects));
        Glide.with(holder.imageView_subjects_photo.getContext()).load(imageSubjects).into(holder.imageView_subjects_photo);



        holder.materialCardView_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity= (AppCompatActivity)v.getContext();
                sharedPreferenceSubjectsUser = new SharedPreferenceSubjectsUser(activity);
                sharedPreferenceSubjectsUser.setDataUserSubjects(idDocument);
                sharedPreferenceSubjectsUser.setNameSubject(nameSubjects);
                //Topics
                Intent intent = new Intent(activity, TopicsActivity.class);
                activity.startActivity(intent);

            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_subjects,parent,false);
        return new ViewHolder(view);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView materialCardView_subject;
        MaterialTextView materialTextView_subjects_name;
        MaterialTextView materialTextView_subjects_likes;
        MaterialTextView materialTextView_subjects_comments;
        MaterialTextView materialTextView_subjects_students;
        MaterialTextView materialTextView_subjects_themes;
        ImageView imageView_subjects_photo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            materialCardView_subject = itemView.findViewById(R.id.material_card_view_subject);
            materialTextView_subjects_name = itemView.findViewById(R.id.text_view_subjects_name);
            materialTextView_subjects_likes=itemView.findViewById(R.id.text_view_likes);
            materialTextView_subjects_comments=itemView.findViewById(R.id.tex_view_comments);
            materialTextView_subjects_students=itemView.findViewById(R.id.text_view_students);
            materialTextView_subjects_themes=itemView.findViewById(R.id.tex_view_themes);
            imageView_subjects_photo=itemView.findViewById(R.id.image_view_subjets_photo);

        }
    }
}
