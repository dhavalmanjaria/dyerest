package com.dhavalanjaria.dyerest.points;

import android.util.Log;
import android.view.View;

import com.dhavalanjaria.dyerest.BaseActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dhaval Anjaria on 3/26/2018.
 */

public class ExerciseTargetFetcher {

    private static final String TAG = "ExerciseTargetFetcher";

    public int getExerciseTargetPoints(DatabaseReference exerciseRef) {
        DatabaseReference ref = BaseActivity.getRootDataReference()
                .child("targets")
                .child(exerciseRef.getKey());

        final int[] retval = {0};

        ref.orderByKey().limitToFirst(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Select first value
                        dataSnapshot = dataSnapshot.getChildren().iterator().next();

                        int points = dataSnapshot.child("points").getValue(Integer.class);

                        retval[0] = points;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, databaseError.getMessage());
                        Log.e(TAG, databaseError.getDetails());
                    }
                });

        return retval[0];
    }

    public Map<String, Object> getExerciseTargetValues(DatabaseReference exerciseRef) {

        DatabaseReference ref = BaseActivity.getRootDataReference()
                .child("targets")
                .child(exerciseRef.getKey());

        final Map<String, Object> retval = new HashMap<>();

        ref.orderByKey().limitToFirst(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, Object> valuesMap = (Map<String, Object>) dataSnapshot.child("values")
                                .getValue();

                        retval.put("values", valuesMap);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        return retval;
    }
}
