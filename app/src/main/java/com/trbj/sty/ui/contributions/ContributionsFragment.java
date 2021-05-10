package com.trbj.sty.ui.contributions;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.trbj.sty.R;

public class ContributionsFragment extends Fragment {


    LottieAnimationView lottieAnimationViewPerson1;
    LottieAnimationView lottieAnimationViewPerson2;


    public View onCreateView(@NonNull LayoutInflater inflater,   ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_contributions, container, false);
        lottieAnimationViewPerson1 =root.findViewById(R.id.image_view_person_special1);
        lottieAnimationViewPerson2=root.findViewById(R.id.image_view_person_special2);

        final boolean[] likePerson1 = {false};
        final boolean[] likePerson2 = {false};
        lottieAnimationViewPerson1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likePerson1[0] = loadAnimatePerson1(lottieAnimationViewPerson1, likePerson1[0]);
            }
        });

        lottieAnimationViewPerson2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likePerson2[0] = loadAnimatePerson2(lottieAnimationViewPerson2, likePerson2[0]);
            }
        });


        return root;


    }


    private boolean loadAnimatePerson1(LottieAnimationView lottieAnimationView, boolean like){
        if(!like){
            lottieAnimationView.setAnimation("8377_like_burst.json");
            lottieAnimationView.playAnimation();
        }else{
            lottieAnimationView.animate()
                    .alpha(0f)
                    .setDuration(200)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            lottieAnimationView.setImageResource(R.drawable.bakugo_round);
                            lottieAnimationView.setAlpha(1f);
                        }
                    });}
        return !like;
    }

    private boolean loadAnimatePerson2(LottieAnimationView lottieAnimationView, boolean like){
        if(!like){
            lottieAnimationView.setAnimation("2448_party_penguin.json");
            lottieAnimationView.playAnimation();
            lottieAnimationView.setRepeatCount(3);
        }else{
            lottieAnimationView.animate()
                    .alpha(0f)
                    .setDuration(200)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            lottieAnimationView.setImageResource(R.drawable.gustavo);
                            lottieAnimationView.setAlpha(1f);
                        }
                    });}
        return !like;
    }
}