package com.dhavalanjaria.dyerest.points;

/**
 * Created by Dhaval Anjaria on 3/21/2018.
 */

import android.util.Log;

import com.dhavalanjaria.dyerest.BaseActivity;
import com.dhavalanjaria.dyerest.models.ActiveExerciseField;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * This class takes points from a values list and adds it to the exercise, the day and the workout
 * subsequently.
 */
public class ActiveExercisePoints {
    private static final String TAG = "ActiveExercisePoints";
    private List<ActiveExerciseField> mActiveExerciseFieldList;

    public ActiveExercisePoints() {
    }

    public ActiveExercisePoints(List<ActiveExerciseField> activeExerciseFieldList) {
        mActiveExerciseFieldList = activeExerciseFieldList;
    }

    public static void updateExercisePerformedPoints(List<ActiveExerciseField> fieldsList, DatabaseReference exercisePerformedReference) {
        int points = 0;
        for (ActiveExerciseField field: fieldsList) {
            points += field.getValue();
        }

        exercisePerformedReference.child("points").setValue(points);
        String dayKey = exercisePerformedReference.getParent().getParent().getKey();

        final int pointsTemp = points;

        exercisePerformedReference.child("points")
                .addValueEventListener(new ExercisePointsAddedListener(points));

    }
}
