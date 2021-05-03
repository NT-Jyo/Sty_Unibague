package com.trbj.sty.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.trbj.sty.Activitys.QuestionActivity;
import com.trbj.sty.Activitys.SeccionFiveActivity;
import com.trbj.sty.Models.Topic;
import com.trbj.sty.Models.Topics.Question;
import com.trbj.sty.R;
import com.trbj.sty.Shareds.SharedPreferenceQuestion;

public class RecycleViewAdapterQuestions extends FirestoreRecyclerAdapter<Question,RecycleViewAdapterQuestions.ViewHolder> {

    SharedPreferenceQuestion sharedPreferenceQuestion;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RecycleViewAdapterQuestions(@NonNull FirestoreRecyclerOptions<Question> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Question question) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());

        String idDocument =documentSnapshot.getId();
        String questionB =question.getQuestion();


        holder.text_view_questionB.setText(questionB);
        holder.material_card_view_questionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity= (AppCompatActivity)v.getContext();
                sharedPreferenceQuestion = new SharedPreferenceQuestion(activity);
                sharedPreferenceQuestion.setDataIdQuestion(idDocument);
                sharedPreferenceQuestion.setDataQuestion(questionB);
                Intent intent = new Intent(activity, QuestionActivity.class);
                activity.startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_question,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_view_questionB;
        MaterialCardView material_card_view_questionB;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_view_questionB =itemView.findViewById(R.id.text_view_question);
            material_card_view_questionB= itemView.findViewById(R.id.material_card_view_question);

        }
    }
}
