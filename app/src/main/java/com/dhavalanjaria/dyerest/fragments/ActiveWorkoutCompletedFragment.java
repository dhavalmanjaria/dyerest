package com.dhavalanjaria.dyerest.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dhavalanjaria.dyerest.ActiveWorkoutActivity;
import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.points.PointsUpdater;
import com.dhavalanjaria.dyerest.points.ExercisePointsCache;
import com.dhavalanjaria.dyerest.points.ExerciseTargetUpdater;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by Dhaval Anjaria on 3/21/2018.
 */

public class ActiveWorkoutCompletedFragment extends Fragment {

    private static final String KEY_DAY_PERFORMED_URL = "ExercisePerformedUrl";
    private static final String TAG = "AWCompletedFragment";
    private DatabaseReference mDayPerformedReference;
    private Button mOkButton;

    public static ActiveWorkoutCompletedFragment newInstance(String dayPerformedUrl) {

        Bundle args = new Bundle();

        ActiveWorkoutCompletedFragment fragment = new ActiveWorkoutCompletedFragment();
        fragment.setArguments(args);
        args.putString(KEY_DAY_PERFORMED_URL, dayPerformedUrl);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_active_workout_completed, container, false);

        String exercisePerformedUrl = getArguments().getString(KEY_DAY_PERFORMED_URL);
        mDayPerformedReference = FirebaseDatabase.getInstance().getReferenceFromUrl(exercisePerformedUrl);

        mOkButton = v.findViewById(R.id.workout_completed_ok_button);
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Integer> map = ((ActiveWorkoutActivity)getActivity()).getExercisePointsCache();

                ExercisePointsCache cache = new ExercisePointsCache(map);

                PointsUpdater pointsUpdater = new PointsUpdater(
                        cache, mDayPerformedReference, getActivity());

                pointsUpdater.updateExercisePointsFromCache();

                // Perhaps this should be called by the PointsUpdater since targets have to do
                // with the points system.
                ExerciseTargetUpdater targetUpdater = new ExerciseTargetUpdater(mDayPerformedReference, cache);
                targetUpdater.addTargetFromCache();

                getActivity().finish();

            }
        });

        return v;
    }
}
