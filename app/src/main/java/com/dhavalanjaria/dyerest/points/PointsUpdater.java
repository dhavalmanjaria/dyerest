package com.dhavalanjaria.dyerest.points;

/**
 * Created by Dhaval Anjaria on 3/21/2018.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.dhavalanjaria.dyerest.BaseActivity;
import com.dhavalanjaria.dyerest.R;
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
public class PointsUpdater {
    private static final String TAG = "PointsUpdater";
    private List<ActiveExerciseField> mActiveExerciseFieldList;
    private Context mContext;
    private ExercisePointsCache mPointsCache;
    private DatabaseReference mDayTimestampRef;


    public PointsUpdater() {
    }

    public PointsUpdater(ExercisePointsCache pointsCache, DatabaseReference dayPerformedReference, Context context) {
        this.mPointsCache = pointsCache;
        this.mContext = context;
        this.mDayTimestampRef = dayPerformedReference;
    }

    public void updateExercisePointsFromCache() {
        HashMap<String, Integer> map = mPointsCache.getCache();

        int totalPointsForDay = 0;

        for (String key: map.keySet()) {
            mDayTimestampRef.child(key)
                    .child("points")
                    .setValue(map.get(key));

            DatabaseReference exercisePerformedRef = mDayTimestampRef.child(key);
            mDayTimestampRef.child(key)
                    .child("points")
                    .addValueEventListener(new ExercisePointsAddedListener(exercisePerformedRef,
                            map.get(key)));

            totalPointsForDay += map.get(key);
        }

        String dayKey = mDayTimestampRef.getParent().getKey();
        mDayTimestampRef
                .addListenerForSingleValueEvent(new DayPointsListener(totalPointsForDay, dayKey));


        SharedPreferences preferences = mContext.getSharedPreferences(
                mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        String workoutKey = preferences.getString(mContext.getString(R.string.workout_id_key), null);

        if (preferences.contains(mContext.getString(R.string.workout_id_key))) {
            BaseActivity.getRootDataReference()
                    .child("workouts")
                    .child(workoutKey)
                    .addListenerForSingleValueEvent(new UpdateTotalPointsListener(totalPointsForDay));
        }
    }
}
