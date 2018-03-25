package com.dhavalanjaria.dyerest.points;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Dhaval Anjaria on 3/26/2018.
 */

public class WorkoutPointsListener implements ValueEventListener {
    private static final String TAG = "WorkoutPointsListener";
    private int pointsToAdd;

    public WorkoutPointsListener(int pointsToAdd) {
        this.pointsToAdd = pointsToAdd;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        String existingPointsStr =(long) dataSnapshot.child("totalPoints")
                .getValue() + "";
        int existingPoints = 0;
        int points = 0;
        if (existingPointsStr != null) {
            try {
                existingPoints = Integer.parseInt(existingPointsStr);
            } catch (NumberFormatException ex) {
                Log.e(TAG, ex.getStackTrace().toString());
            }
            points = pointsToAdd + existingPoints;
        } else {
            points = pointsToAdd;
        }
        dataSnapshot.child("totalPoints").getRef().setValue(points);

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
