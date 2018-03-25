package com.dhavalanjaria.dyerest.points;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Dhaval Anjaria on 3/26/2018.
 */

public class UpdateTotalPointsListener implements ValueEventListener {

    private static final String TAG = "TotalPointsListener";
    private int pointsToAdd;

    public UpdateTotalPointsListener(int pointsToAdd) {
        this.pointsToAdd = pointsToAdd;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        int existingPoints = 0;
        int points = 0;

        if (dataSnapshot.child("totalPoints").getValue() != null) {
            String pointsStr = (long) dataSnapshot.child("totalPoints").getValue() + "";
            try {
                existingPoints = Integer.parseInt(pointsStr);
            } catch (NumberFormatException ex) {
                Log.e(TAG, ex.getStackTrace().toString());
                existingPoints = 0;
            }
            points = pointsToAdd + existingPoints;
        }
        dataSnapshot.child("totalPoints").getRef().setValue(points);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.e(TAG, databaseError.getMessage());
        Log.e(TAG, databaseError.getDetails());
    }
}
