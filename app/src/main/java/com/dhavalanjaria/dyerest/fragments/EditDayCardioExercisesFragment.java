package com.dhavalanjaria.dyerest.fragments;

import android.os.Bundle;

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

    public static EditDayCardioExercisesFragment newInstance(boolean addingToDay) {
        Bundle args = new Bundle();

        EditDayCardioExercisesFragment  fragment = new EditDayCardioExercisesFragment ();
        args.putSerializable(KEY_ADDING_TO_DAY, addingToDay);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // Return Firebase data
        return null;
    }
}
