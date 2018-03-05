package com.dhavalanjaria.dyerest.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.models.Exercise;

/**
 * Created by Dhaval Anjaria on 3/4/2018.
 */

public abstract class ExerciseListViewHolder extends RecyclerView.ViewHolder {
    protected TextView mExerciseName;

    public ExerciseListViewHolder(View itemView) {
        super(itemView);
        mExerciseName = (TextView) itemView.findViewById(R.id.exercise_name_text);
    }

    public void bind(Exercise exercise, String exerciseKey) {
        mExerciseName.setText(exercise.getName());
    }
}
