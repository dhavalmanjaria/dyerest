package com.dhavalanjaria.dyerest.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.models.DayExercise;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Dhaval Anjaria on 2/26/2018.
 */

public class DayExerciseSequenceViewHolder extends RecyclerView.ViewHolder {

    private TextView mSequenceNumberText;
    private TextView mExerciseNameText;

    public DayExerciseSequenceViewHolder(View itemView) {
        super(itemView);

        mSequenceNumberText = itemView.findViewById(R.id.exercise_sequence_number_text);
        mExerciseNameText = itemView.findViewById(R.id.edit_sequence_exercise_name_text);


    }
    public void bind(DatabaseReference dayExerciseRef) {

        dayExerciseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = (String) dataSnapshot.child("name").getValue();
                String sequenceNoStr = (long)dataSnapshot.child("sequenceNumber").getValue() + "";
                mExerciseNameText.setText(name);
                mSequenceNumberText.setText(sequenceNoStr);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
