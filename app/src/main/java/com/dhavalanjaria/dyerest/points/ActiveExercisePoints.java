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

import java.util.HashMap;
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

    public static void updateExercisePoints(ExercisePointsCache pointsCache, DatabaseReference mDayTimeStampRef) {
        HashMap<String, Integer> map = pointsCache.getCache();

        int totalPointsForDay = 0;

        for (String key: map.keySet()) {
            mDayTimeStampRef.child(key)
                    .child("points")
                    .setValue(map.get(key));

            DatabaseReference exercisePerformedRef = mDayTimeStampRef.child(key);
            mDayTimeStampRef.child(key)
                    .child("points")
                    .addValueEventListener(new ExercisePointsAddedListener(exercisePerformedRef,
                            map.get(key)));

            totalPointsForDay += map.get(key);
        }


        mDayTimeStampRef
                .addValueEventListener(new DayPointsListener(totalPointsForDay));
    }
}
