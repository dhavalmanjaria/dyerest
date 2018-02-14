package com.dhavalanjaria.dyerest.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.models.Exercise;

/**
 * Created by Dhaval Anjaria on 2/14/2018.
 */

public class AddExerciseViewHolder extends RecyclerView.ViewHolder {

    private TextView mExerciseName;
    private Button mAddRemoveExercise;

    public AddExerciseViewHolder(View itemView) {
        super(itemView);

        mExerciseName = (TextView) itemView.findViewById(R.id.exercise_name_text);
        mAddRemoveExercise = (Button) itemView.findViewById(R.id.add_remove_exercise_button);

    }

    public void bind(Exercise exercise) {
        mExerciseName.setText(exercise.getName());
    }
}
