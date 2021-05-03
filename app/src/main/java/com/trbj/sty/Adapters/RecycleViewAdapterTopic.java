package com.trbj.sty.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.trbj.sty.Activitys.IntroductionActivity;
import com.trbj.sty.Activitys.SubjectsActivity;
import com.trbj.sty.Models.Topic;
import com.trbj.sty.R;
import com.trbj.sty.Shareds.SharedPreferenceSubjectsUser;

public class RecycleViewAdapterTopic extends FirestoreRecyclerAdapter<Topic, RecycleViewAdapterTopic.ViewHolder> {

    SharedPreferenceSubjectsUser sharedPreferenceSubjectsUser;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RecycleViewAdapterTopic(@NonNull FirestoreRecyclerOptions<Topic> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Topic topic) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());

        String idDocument =documentSnapshot.getId();
        String nameTopic = topic.getName();

        holder.materialTextView_topic_name.setText(nameTopic);
        holder.materialCardView_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity= (AppCompatActivity)v.getContext();
                sharedPreferenceSubjectsUser = new SharedPreferenceSubjectsUser(activity);
                sharedPreferenceSubjectsUser.setDataUserTopics(idDocument);
                sharedPreferenceSubjectsUser.setNameTopic(nameTopic);
                Intent intent = new Intent(activity, IntroductionActivity.class);
                activity.startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_topic,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView materialCardView_topic;
        MaterialTextView materialTextView_topic_name;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            materialCardView_topic = itemView.findViewById(R.id.material_card_view_topic);
            materialTextView_topic_name=itemView.findViewById(R.id.text_view_name_topic);
        }
    }
}
