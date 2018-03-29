package com.dhavalanjaria.dyerest.viewholders;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dhavalanjaria.dyerest.BaseActivity;
import com.dhavalanjaria.dyerest.ExercisePointsByDateActivity;
import com.dhavalanjaria.dyerest.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Dhaval Anjaria on 2/26/2018.
 */

public class ExerciseHistoryViewHolder extends RecyclerView.ViewHolder {

    private TextView mExerciseName;
    private TextView mPointsText;
    private Button mViewButton;


    public ExerciseHistoryViewHolder(View itemView) {
        super(itemView);

        mExerciseName = itemView.findViewById(R.id.exercise_date_text);
        mPointsText = itemView.findViewById(R.id.exercise_points_text);
        mViewButton = itemView.findViewById(R.id.view_exercise_points_button);

    }


    // Not for Firebase use
    public void bind(final DatabaseReference exerciseRef) {

        exerciseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String pointsStr = dataSnapshot.child("totalPoints").getValue() + "";
                String name = (String) dataSnapshot.child("name").getValue();

                mExerciseName.setText(name);
                mPointsText.setText(pointsStr);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        final DatabaseReference targetRef = BaseActivity.getRootDataReference()
                .child("targets")
                .child(exerciseRef.getKey());

        mViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace getAdapterPosition, perhaps.
                Intent intent = ExercisePointsByDateActivity
                        .newIntent(itemView.getContext(), targetRef.toString());
                itemView.getContext().startActivity(intent);
            }
        });
    }
}
