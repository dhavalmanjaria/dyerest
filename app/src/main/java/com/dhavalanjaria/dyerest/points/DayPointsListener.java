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

public class DayPointsListener implements ValueEventListener {
    private static final String TAG = "DayPointsListener";
    private int pointsToAdd;

    public DayPointsListener(int pointsToAdd) {
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

        String dayRefKey = dataSnapshot.getRef()
                .getParent()
                .getKey();

        final DatabaseReference dayRef = BaseActivity.getRootDataReference().child("days")
                .child(dayRefKey);

        dayRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int existingPoints = 0;
                String existingPointsStr = (long) dataSnapshot.child("totalPoints")
                        .getValue() + "";
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
                dayRef.child("totalPoints").setValue(points);
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
