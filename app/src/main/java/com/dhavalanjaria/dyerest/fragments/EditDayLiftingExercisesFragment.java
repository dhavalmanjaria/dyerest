package com.dhavalanjaria.dyerest.fragments;

import android.os.Bundle;

import com.dhavalanjaria.dyerest.fragments.EditDayExercisesFragment;
import com.dhavalanjaria.dyerest.models.MockData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by Dhaval Anjaria on 2/14/2018.
 */

public class EditDayLiftingExercisesFragment extends EditDayExercisesFragment {

    public EditDayLiftingExercisesFragment() {
    }

    // TODO: Refractor this into something more sane
    public static EditDayLiftingExercisesFragment newInstance(boolean addingToDay) {
        Bundle args = new Bundle();

        EditDayLiftingExercisesFragment fragment = new EditDayLiftingExercisesFragment();
        args.putSerializable(KEY_ADDING_TO_DAY, addingToDay);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        return null;
        // Return Firebase data
    }
}
