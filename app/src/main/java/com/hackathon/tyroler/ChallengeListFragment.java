package com.hackathon.tyroler;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.card.MaterialCardView;

import java.util.Objects;

public class ChallengeListFragment extends Fragment {
    private ProgressBar bar;
    private TextView progressText;
    private SharedViewModel model;


    public ChallengeListFragment() {
        // Required empty public constructor
    }

    public static ChallengeListFragment newInstance() {
        return new ChallengeListFragment();
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
        View view = inflater.inflate(R.layout.fragment_challenge_list, container, false);

        bar = view.findViewById(R.id.progressBar);
        progressText = view.findViewById(R.id.progressText);

        model.getCurr().observe(this, curr ->  {
            progressText.setText(curr + " of " + 3);
            bar.setProgress(curr);
            bar.setMax(3);
        });

        MaterialCardView beerNightChallengeCard
                = view.findViewById(R.id.challenge_beer_night);
        beerNightChallengeCard.setOnClickListener(v ->
                Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.nav_host_fragment)
                        .navigate(R.id.action_challengeListFragment_to_challengeDetailFragment));

        return view;
    }
}
