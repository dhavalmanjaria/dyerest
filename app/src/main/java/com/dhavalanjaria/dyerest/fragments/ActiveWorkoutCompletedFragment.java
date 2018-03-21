package com.dhavalanjaria.dyerest.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dhavalanjaria.dyerest.R;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Dhaval Anjaria on 3/21/2018.
 */

public class ActiveWorkoutCompletedFragment extends Fragment {

    private static final String KEY_EXERCISE_PERFORMED_URL = "ExercisePerformedUrl";
    private DatabaseReference mExercisePerformedReference;
    private Button mOkButton;

    public static ActiveWorkoutCompletedFragment newInstance(String exercisePerformedUrl) {

        Bundle args = new Bundle();

        ActiveWorkoutCompletedFragment fragment = new ActiveWorkoutCompletedFragment();
        fragment.setArguments(args);
        args.putString(KEY_EXERCISE_PERFORMED_URL, exercisePerformedUrl);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_active_workout_completed, container, false);


        return v;
    }
}
