package com.dhavalanjaria.dyerest.points;

import android.util.Log;

import com.dhavalanjaria.dyerest.BaseActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Dhaval Anjaria on 3/21/2018.
 */

public class ExercisePointsAddedListener implements ValueEventListener {

    public static final String TAG = "ExPtsAddedListener";
    private int pointsToAdd;
    private DatabaseReference mExercisePerformedRef;

    public ExercisePointsAddedListener(DatabaseReference exercisePerformedRef, int pointsToAdd) {
        this.pointsToAdd = pointsToAdd;
        this.mExercisePerformedRef = exercisePerformedRef;
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

        mExercisePerformedRef
                .child("exerciseKey")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String exerciseKey = (String)dataSnapshot.getValue();
                        BaseActivity.getRootDataReference()
                                .child("exercises")
                                .child(exerciseKey)
                                .addListenerForSingleValueEvent(new UpdateTotalPointsListener(pointsToAdd));

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
