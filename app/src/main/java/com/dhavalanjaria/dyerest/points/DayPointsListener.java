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
    private String mDayKey;

    public DayPointsListener(int pointsToAdd, String dayKey) {
        this.pointsToAdd = pointsToAdd;
        this.mDayKey = dayKey;
    }

    public int getPointsToAdd() {
        return pointsToAdd;
    }

    public void setPointsToAdd(int pointsToAdd) {
        this.pointsToAdd = pointsToAdd;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        final DatabaseReference dayRef = BaseActivity.getRootDataReference().child("days")
                .child(mDayKey);

        dayRef.addListenerForSingleValueEvent(new UpdateTotalPointsListener(pointsToAdd));

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
