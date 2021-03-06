package com.dhavalanjaria.dyerest.viewholders;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dhavalanjaria.dyerest.BaseActivity;
import com.dhavalanjaria.dyerest.MainActivity;
import com.dhavalanjaria.dyerest.helpers.OnDialogCompletedListener;
import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.WorkoutDetailActivity;
import com.dhavalanjaria.dyerest.fragments.EditDialogFragment;
import com.dhavalanjaria.dyerest.models.Workout;

import java.text.SimpleDateFormat;

/**
 * Created by Dhaval Anjaria on 2/6/2018.
 * @brief Holds Workout Cards.
 */
public class WorkoutCardViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "WorkoutCardViewHolder";
    private TextView mWorkoutName;
    private TextView mDateCreated;
    private TextView mDateCreatedValue;
    private TextView mTotalPoints;
    private Button mViewWorkoutButton;
    private Button mEditWorkButton;

    public WorkoutCardViewHolder(final View itemView) {
        super(itemView);

        mWorkoutName = (TextView) itemView.findViewById(R.id.workout_name);
        mDateCreated = (TextView) itemView.findViewById(R.id.date_created);
        mDateCreatedValue = (TextView) itemView.findViewById(R.id.date_created_value);
        mTotalPoints =  (TextView) itemView.findViewById(R.id.total_points_text);
        mViewWorkoutButton = (Button) itemView.findViewById(R.id.view_workout_btn);
        mEditWorkButton = (Button) itemView.findViewById(R.id.workout_edit_button);

    }

    public void bind(final Workout workout, final String workoutId) {
        mWorkoutName.setText(workout.getName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(workout.getDateCreated());
        mDateCreatedValue.setText(formattedDate);

        mTotalPoints.setText(workout.getTotalPoints() + "");

        mViewWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = WorkoutDetailActivity.newIntent(itemView.getContext(),
                        workoutId);
                itemView.getContext().startActivity(intent);
            }
        });

        mEditWorkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDialogFragment.newInstance("Edit name", new OnDialogCompletedListener() {
                    @Override
                    public void onDialogComplete(String text) {
                        mWorkoutName.setText(text);
                        BaseActivity.getRootDataReference()
                                .child("workouts")
                                .child(workoutId)
                                .child("name")
                                .setValue(text);
                    }
                })
                .show(((MainActivity)itemView.getContext()).getSupportFragmentManager(), TAG);
            }
        });

    }
}
