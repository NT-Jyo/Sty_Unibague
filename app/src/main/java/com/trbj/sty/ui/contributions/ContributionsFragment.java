package com.trbj.sty.ui.contributions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.trbj.sty.R;

public class ContributionsFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,   ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_contributions, container, false);

        return root;
    }
}