package com.dhavalanjaria.dyerest.viewholders;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.models.Exercise;

/**
 * Created by Dhaval Anjaria on 2/15/2018.
 */

public class ExerciseDetailViewHolder extends RecyclerView.ViewHolder {

    private TextView mExerciseFieldText;
    private EditText mExerciseValueEdit;
    private FloatingActionButton mSaveButton;

    public ExerciseDetailViewHolder(View itemView) {
        super(itemView);

        mExerciseFieldText = (TextView) itemView.findViewById(R.id.exercise_detail_text);
        mExerciseValueEdit = (EditText) itemView.findViewById(R.id.exercise_detail_edit);
        mSaveButton = (FloatingActionButton) itemView.findViewById(R.id.save_button);
    }

    /**
     * This function binds one field of an exercise to one ViewHolder in the RecyclerView. Each
     * ViewHolder will contain the name of one field and it's target value
     * @param fieldName Name of the field to display
     * @param targetValue The hint that will be displayed in the EditText. This would be the target
     *                    value of the exercise.
     */
    public void bind(String fieldName, int targetValue) {
        mExerciseFieldText.setText(fieldName);
        mExerciseValueEdit.setHint("" + targetValue);
    }
}
