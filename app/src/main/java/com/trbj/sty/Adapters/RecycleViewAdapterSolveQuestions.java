package com.trbj.sty.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.trbj.sty.Models.SolveQuestion;
import com.trbj.sty.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class RecycleViewAdapterSolveQuestions extends FirestoreRecyclerAdapter<SolveQuestion,RecycleViewAdapterSolveQuestions.ViewHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RecycleViewAdapterSolveQuestions(@NonNull FirestoreRecyclerOptions<SolveQuestion> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecycleViewAdapterSolveQuestions.ViewHolder holder, int position, @NonNull SolveQuestion question) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());

        String idDocument =documentSnapshot.getId();
        Long questionDate =question.getDate();
        String questionInfo=question.getQuestion();
        String questionName =question.getName();
        String questionSolve=question.getSolveQuestion();

        DateFormat simple = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");

        holder.text_view_solve_question_titleB.setText(questionInfo);
        holder.text_view_solve_question_dateB.setText(simple.format(questionDate));
        holder.text_view_solve_question_solveB.setText(questionSolve);
        holder.text_view_solve_question_authorB.setText("{o,o}\n" +
                "|)__)\n" +
                "-\"-\"-"+ " "+ questionName);
    }

    @NonNull
    @Override
    public RecycleViewAdapterSolveQuestions.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_solve_questions,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView material_card_view_solve_questionB;
        TextView text_view_solve_question_titleB;
        TextView text_view_solve_question_dateB;
        TextView text_view_solve_question_solveB;
        TextView text_view_solve_question_authorB;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            material_card_view_solve_questionB = itemView.findViewById(R.id.material_card_view_solve_question);
            text_view_solve_question_titleB=itemView.findViewById(R.id.text_view_solve_question_title);
            text_view_solve_question_dateB=itemView.findViewById(R.id.text_view_solve_question_date);
            text_view_solve_question_solveB=itemView.findViewById(R.id.text_view_solve_question_solve);
            text_view_solve_question_authorB=itemView.findViewById(R.id.text_view_solve_question_author);
        }
    }
}
