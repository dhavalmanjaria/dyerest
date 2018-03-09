package com.dhavalanjaria.dyerest.viewholders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.models.DayExercise;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Dhaval Anjaria on 2/14/2018.
 */

public class AddExerciseToDayViewHolder extends RecyclerView.ViewHolder {

    public static final String TAG = "AddExerciseToDayVH";

    private Switch mAddRemoveExerciseSwitch;
    private TextView mExerciseName;
    private boolean mAddingToDay;

    public AddExerciseToDayViewHolder(View itemView) {
        super(itemView);

        mExerciseName = (TextView) itemView.findViewById(R.id.exercise_name_text);

        mAddRemoveExerciseSwitch = (Switch) itemView.findViewById(R.id.add_remove_exercise_switch);
        mAddRemoveExerciseSwitch.setVisibility(View.VISIBLE);
    }


    public void bind(final DayExercise exercise, final DatabaseReference exerciseRef, final DatabaseReference dayReference) {

        class CheckChangedListener implements CompoundButton.OnCheckedChangeListener {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    exercise.setExerciseKey(exerciseRef.getKey());
                    getSequenceNumberQuery(dayReference).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            int lastSequenceNumber = 0;

                            if (dataSnapshot.getValue() != null) {
                                DataSnapshot lastExerciseSnap = dataSnapshot.getChildren().iterator().next();
                                DayExercise lastExercise = lastExerciseSnap.getValue(DayExercise.class);
                                lastSequenceNumber = lastExercise.getSequenceNumber();
                            }
                            exercise.setSequenceNumber(lastSequenceNumber + 1);
                            Map<String, Object> dayExercises = exercise.toMap();
                            dayReference.child("exercises").push().updateChildren(dayExercises);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e(TAG, databaseError.toString());
                        }
                    });


                } else {
                    Query query = dayReference.child("exercises").orderByChild("exerciseKey").equalTo(exerciseRef.getKey());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d(TAG, dataSnapshot.getChildren().iterator().next().getKey());
                            dayReference.child("exercises").child(dataSnapshot.getChildren().iterator().next().getKey()).removeValue();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        }

        final List<String> exerciseKeys = new LinkedList<String>();

        dayReference.child("exercises").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> children = dataSnapshot.getChildren().iterator();
                while (children.hasNext()) {

                    DataSnapshot childSnap = children.next();
                    String key = childSnap.child("exerciseKey").getValue().toString();
                    Log.i(TAG, key);
                    exerciseKeys.add(key);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.toString());
            }
        });

        exerciseRef.child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue().toString();
                mExerciseName.setText(name);

                if (exerciseKeys.contains(exerciseRef.getKey())) {
                    mAddRemoveExerciseSwitch.setOnCheckedChangeListener(null);
                    mAddRemoveExerciseSwitch.setChecked(true);
                    mAddRemoveExerciseSwitch.setOnCheckedChangeListener(new CheckChangedListener());
                    // NOTE: This fires the onCheckedChangedListener.
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.toString());
            }
        });



        mAddRemoveExerciseSwitch.setOnCheckedChangeListener(new CheckChangedListener());
    }

    /**
     * Returns a reference of the last sequence number. Since Firebase listeners are asynchronous we
     * need to change the text for our Views within the listeners themselves.
     * @param dayReference
     * @return DatabaseReference -- reference of the sequenceNumber child of the last exercise in
     * the sequence
     */
    private Query getSequenceNumberQuery(DatabaseReference dayReference) {
        Query query = dayReference.child("exercises").orderByChild("sequenceNumber");

        Query ret = query.limitToLast(1);
        return ret;

    }
}
