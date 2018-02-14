package com.dhavalanjaria.dyerest;

import com.dhavalanjaria.dyerest.fragments.ExerciseListFragment;
import com.dhavalanjaria.dyerest.models.MockData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by Dhaval Anjaria on 2/14/2018.
 */

public class LiftingExerciseListFragment extends ExerciseListFragment {

    // TODO: Refractor this into something more sane
    public LiftingExerciseListFragment() {
        mExerciseList = MockData.getLiftingExercises();
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        return null;
        // Return Firebase data
    }
}
