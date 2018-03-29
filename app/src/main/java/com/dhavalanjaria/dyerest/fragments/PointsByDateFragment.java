package com.dhavalanjaria.dyerest.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.models.ExercisePoints;
import com.dhavalanjaria.dyerest.models.ToDeleteExercisePerformed;
import com.dhavalanjaria.dyerest.models.MockData;
import com.dhavalanjaria.dyerest.viewholders.ExerciseDatePointsViewHolder;

import java.util.List;

/**
 * Created by Dhaval Anjaria on 2/26/2018.
 */

@Deprecated
public class PointsByDateFragment extends Fragment {
    private ExercisePoints mExercisePoints;

    private RecyclerView mExercisePerformedRecycler;

    // should eventually create a new instance with args containing a "day key"
    // We get the list of exercises from the DayKey and populate the RecyclerView from there
    public static PointsByDateFragment newInstance() {

        Bundle args = new Bundle();

        PointsByDateFragment fragment = new PointsByDateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_exercise_performed, container, false);

        mExercisePerformedRecycler = v.findViewById(R.id.exercise_performed_recycler);
        mExercisePerformedRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }

}
