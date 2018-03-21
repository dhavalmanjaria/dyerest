package com.dhavalanjaria.dyerest.points;

import android.util.Log;

import com.dhavalanjaria.dyerest.BaseActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Dhaval Anjaria on 3/21/2018.
 */

public class ExercisePointsAddedListener implements ValueEventListener {

    public static final String TAG = "ExercisePointsAddedListener";
    private int pointsToAdd;

    public ExercisePointsAddedListener(int pointsToAdd) {
        this.pointsToAdd = pointsToAdd;
    }

    public int getPointsToAdd() {
        return pointsToAdd;
    }

    public void setPointsToAdd(int pointsToAdd) {
        this.pointsToAdd = pointsToAdd;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        String dayKey = dataSnapshot.getRef()
                .getParent() // exercise
                .getParent() // dayTimeStamp
                .getParent() // DayKey
                .getKey();

        BaseActivity.getRootDataReference()
                .child("days")
                .child(dayKey)
                .addListenerForSingleValueEvent(new DayPointsListener(pointsToAdd));

        String exerciseKey = dataSnapshot.getRef().getKey();
        Log.d(TAG, exerciseKey);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
