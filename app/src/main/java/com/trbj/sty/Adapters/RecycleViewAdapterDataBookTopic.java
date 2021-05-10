package com.trbj.sty.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.trbj.sty.Activitys.DataBookPageActivity;
import com.trbj.sty.Models.Annotations;
import com.trbj.sty.R;

public class RecycleViewAdapterDataBookTopic extends FirestoreRecyclerAdapter<Annotations, RecycleViewAdapterDataBookTopic.ViewHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RecycleViewAdapterDataBookTopic(@NonNull FirestoreRecyclerOptions<Annotations> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecycleViewAdapterDataBookTopic.ViewHolder holder, int position, @NonNull Annotations annotations) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
        String documentId = documentSnapshot.getId();

        String title = annotations.getTitle();
        String date= annotations.getDate();
        String keyWords= annotations.getKeywords();
        String photoURL= annotations.getPhotoURL();
        String resume=annotations.getResume();
        String examples=annotations.getExamples();
        String topic=annotations.getTopic();

        holder.text_view_book_topic_titleB.setText(title);
        holder.text_view_book_topic_dateB.setText(date);
        holder.text_view_book_topic_keyworB.setText(keyWords);
        Glide.with(holder.image_view_book_topicB.getContext()).load(photoURL).circleCrop().into(holder.image_view_book_topicB);
        holder.material_card_view_book_topic_noteB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity appCompatActivity =(AppCompatActivity)v.getContext();
                Intent intent = new Intent (appCompatActivity, DataBookPageActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("date", date);
                intent.putExtra("keyWords", keyWords);
                intent.putExtra("resume", resume);
                intent.putExtra("examples", examples);
                intent.putExtra("topic", topic);
                intent.putExtra("photoURL",photoURL);
                appCompatActivity.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public RecycleViewAdapterDataBookTopic.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_data_book_topic_note,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView material_card_view_book_topic_noteB;
        TextView text_view_book_topic_titleB;
        TextView text_view_book_topic_dateB;
        TextView text_view_book_topic_keyworB;
        ImageView image_view_book_topicB;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            material_card_view_book_topic_noteB =itemView.findViewById(R.id.material_card_view_book_topic_note);
            text_view_book_topic_titleB=itemView.findViewById(R.id.text_view_book_topic_title);
            text_view_book_topic_dateB=itemView.findViewById(R.id.text_view_book_topic_date);
            text_view_book_topic_keyworB=itemView.findViewById(R.id.text_view_book_topic_keyword);
            image_view_book_topicB=itemView.findViewById(R.id.image_view_book_topic);
        }
    }
}
