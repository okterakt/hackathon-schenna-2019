package com.hackathon.tyroler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.Objects;

public class ChallengeDetailFragment extends Fragment {
    private ProgressBar bar;
    private TextView progressText;
    SharedViewModel model;

    public ChallengeDetailFragment() {
        // Required empty public constructor
    }

    public static ChallengeDetailFragment newInstance() {
        return new ChallengeDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(Objects.requireNonNull(getActivity()))
                .get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenge_detail, container, false);

        model.getCurr().observe(this, curr -> {
            bar = view.findViewById(R.id.progressBarDetail);
            progressText = view.findViewById(R.id.progressTextDetail);
            progressText.setText(curr + " of " + 3);
            bar.setProgress(curr);
            bar.setMax(3);
        });

        return view;
    }
}
