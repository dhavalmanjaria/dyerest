package com.dhavalanjaria.dyerest;

import com.dhavalanjaria.dyerest.fragments.ExerciseListFragment;
import com.dhavalanjaria.dyerest.models.MockData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by Dhaval Anjaria on 2/14/2018.
 */

public class CardioExerciseListFragment extends ExerciseListFragment {

    // TODO: Refractor this into something more sane
    // Eventually this will be removed in favor of the getQuery override.
    public CardioExerciseListFragment() {
        mExerciseList = MockData.getCardioExercises();
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // Return Firebase data
        return null;
    }
}
