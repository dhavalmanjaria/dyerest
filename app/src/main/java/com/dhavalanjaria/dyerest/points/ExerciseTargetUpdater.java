package com.dhavalanjaria.dyerest.points;

/**
 * Created by Dhaval Anjaria on 3/26/2018.
 */

import android.util.Log;

import com.dhavalanjaria.dyerest.BaseActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * This is used to update the exercise targets from which points may be calculated
 */
public class ExerciseTargetUpdater {
    public static final String TAG = "ExerciseTargetUpdater";
    private DatabaseReference mDayTimestampRef;
    private ExercisePointsCache mExercisePointsCache;

    public ExerciseTargetUpdater(DatabaseReference dayTimestampRef,ExercisePointsCache exercisePointsCache) {
        mDayTimestampRef = dayTimestampRef;
        mExercisePointsCache = exercisePointsCache;
    }

    public DatabaseReference getDayTimestampRef() {
        return mDayTimestampRef;
    }

    public void setDayTimestampRef(DatabaseReference dayTimestampRef) {
        mDayTimestampRef = dayTimestampRef;
    }

    public ExercisePointsCache getExercisePointsCache() {
        return mExercisePointsCache;
    }

    public void setExercisePointsCache(ExercisePointsCache exercisePointsCache) {
        mExercisePointsCache = exercisePointsCache;
    }

    public void addTargetFromCache() {
        final DatabaseReference ref = BaseActivity.getRootDataReference().child("targets");

        final String dayKey = mDayTimestampRef.getParent().getKey();


        mDayTimestampRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot exercisePerfSnap: dataSnapshot.getChildren()) {
                    String exerciseKey = exercisePerfSnap.child("exerciseKey").getValue(String.class);
                    String exPerfKey = exercisePerfSnap.getKey();

                    Map<String, Object> valuesMap = (Map<String, Object>) exercisePerfSnap
                            .child("values").getValue();

                    int points = exercisePerfSnap.child("points").getValue(Integer.class);

                    Map<String, Object> targetMap = new HashMap<>();
                    targetMap.put("exerciseName", dayKey);
                    targetMap.put("exercisePerformedKey", exPerfKey);
                    targetMap.put("points", points);
                    targetMap.put("values", valuesMap);

                    ref.child(exerciseKey)
                            .child(mDayTimestampRef.getKey())
                            .updateChildren(targetMap);

                    /**
                     * The structure this creates is: exercise -> timestamp ->
                     * {exerciseName: ..., exercisePerformedKey: ...}
                     * The idea behind this structure is that we can organize by date and get the
                     * values for the exercise the last time it was performed, whenever that was.
                     * The exerciseName and the exercisePerformedKey make it simple to find the values and
                     * points for that timestamp.
                     */
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
                Log.e(TAG, databaseError.getDetails());
            }
        });

    }
}
