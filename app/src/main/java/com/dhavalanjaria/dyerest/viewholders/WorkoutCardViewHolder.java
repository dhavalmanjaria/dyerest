package com.dhavalanjaria.dyerest.viewholders;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.WorkoutDetailActivity;
import com.dhavalanjaria.dyerest.models.Workout;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dhaval Anjaria on 2/6/2018.
 * @brief Holds Workout Cards.
 */
public class WorkoutCardViewHolder extends RecyclerView.ViewHolder {

    private TextView mWorkoutName;
    private TextView mDateCreated;
    private TextView mDateCreatedValue;
    private TextView mTotalPoints;
    private Button mViewWorkoutButton;

    public WorkoutCardViewHolder(final View itemView) {
        super(itemView);

        mWorkoutName = (TextView) itemView.findViewById(R.id.workout_name);
        mDateCreated = (TextView) itemView.findViewById(R.id.date_created);
        mDateCreatedValue = (TextView) itemView.findViewById(R.id.date_created_value);
        mTotalPoints =  (TextView) itemView.findViewById(R.id.total_points_text);
        mViewWorkoutButton = (Button) itemView.findViewById(R.id.view_workout_btn);

        mViewWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = WorkoutDetailActivity.newIntent(itemView.getContext(),
                        mWorkoutName.getText().toString());
                itemView.getContext().startActivity(intent);
            }
        });

    }

    public void bind(Workout workout) {
        mWorkoutName.setText(workout.getName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(workout.getDateCreated());
        mDateCreatedValue.setText(formattedDate);
        mTotalPoints.setText(workout.getTotalPoints() + "");
    }
}
