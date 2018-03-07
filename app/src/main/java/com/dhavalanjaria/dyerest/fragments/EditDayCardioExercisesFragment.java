package com.dhavalanjaria.dyerest.fragments;

import android.os.Bundle;

import com.dhavalanjaria.dyerest.BaseActivity;
import com.dhavalanjaria.dyerest.ExerciseListActivity;
import com.dhavalanjaria.dyerest.fragments.EditDayExercisesFragment;
import com.dhavalanjaria.dyerest.models.MockData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by Dhaval Anjaria on 2/14/2018.
 */

public class EditDayCardioExercisesFragment extends EditDayExercisesFragment {

    // TODO: Refractor this into something more sane
    // Eventually this will be removed in favor of the getQuery override.

    public static EditDayCardioExercisesFragment newInstance(ExerciseListActivity.LIST_TYPE listType, String workoutDayRefUrl) {
        Bundle args = new Bundle();

        EditDayCardioExercisesFragment  fragment = new EditDayCardioExercisesFragment ();
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
                .equalTo("CARDIO");
    }
}
