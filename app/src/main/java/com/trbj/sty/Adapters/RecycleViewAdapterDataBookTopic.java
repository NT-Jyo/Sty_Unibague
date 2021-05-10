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
import com.trbj.sty.Activitys.DataBookActivity;
import com.trbj.sty.Models.Annotations;
import com.trbj.sty.Models.Courses;
import com.trbj.sty.R;
import com.trbj.sty.Shareds.SharedPreferenceSubjectsUser;

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

        holder.text_view_book_topic_titleB.setText(title);
        holder.material_card_view_book_topic_noteB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO TERMINAR
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            material_card_view_book_topic_noteB =itemView.findViewById(R.id.material_card_view_book_topic_note);
            text_view_book_topic_titleB=itemView.findViewById(R.id.text_view_book_topic_title);
            text_view_book_topic_dateB=itemView.findViewById(R.id.text_view_book_topic_date);
            text_view_book_topic_keyworB=itemView.findViewById(R.id.text_view_book_topic_keyword);
        }
    }
}
