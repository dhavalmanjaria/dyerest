package com.dhavalanjaria.dyerest.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.models.ToDeleteExercisePerformed;

/**
 * Created by Dhaval Anjaria on 2/26/2018.
 */

public class ExercisePerformedViewHolder extends RecyclerView.ViewHolder{
    private TextView mExercisePeformedNameText;
    private TextView mPointsText;

    public ExercisePerformedViewHolder(View itemView) {
        super(itemView);

        mExercisePeformedNameText = itemView.findViewById(R.id.exercise_performed_name_text);
        mPointsText = itemView.findViewById(R.id.exercise_performed_points_text);
    }

    // Not for Firebase use
    public void bind(ToDeleteExercisePerformed model) {
        mExercisePeformedNameText.setText(model.getExerciseKey());
        mPointsText.setText(model.getPoints() + "");
    }

}
