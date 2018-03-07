package com.dhavalanjaria.dyerest.fragments;

import android.os.Bundle;

import com.dhavalanjaria.dyerest.BaseActivity;
import com.dhavalanjaria.dyerest.ExerciseListActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by Dhaval Anjaria on 2/14/2018.
 */

public class EditDayLiftingExercisesFragment extends EditDayExercisesFragment {

    public EditDayLiftingExercisesFragment() {
    }

    // TODO: Refractor this into something more sane
    public static EditDayLiftingExercisesFragment newInstance(ExerciseListActivity.LIST_TYPE listType, String workoutDayRefUrl) {
        Bundle args = new Bundle();

        EditDayLiftingExercisesFragment fragment = new EditDayLiftingExercisesFragment();
        args.putSerializable(KEY_LIST_TYPE, listType);
        args.putSerializable(KEY_WORKOUT_DAY_REF_URL, workoutDayRefUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Query getQuery() {
        return BaseActivity.getRootDataReference()
                .child("exercises")
                .orderByChild("exerciseType")
                .equalTo("LIFTING");
    }
}
