package com.dhavalanjaria.dyerest.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;

import com.dhavalanjaria.dyerest.ListAllExerciseActivity;
import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.models.Exercise;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Dhaval Anjaria on 2/14/2018.
 */

public class AddExerciseToDayViewHolder extends ExerciseListViewHolder {

    private Switch mAddRemoveExerciseSwitch;
    private boolean mAddingToDay;

    public AddExerciseToDayViewHolder(View itemView) {
        super(itemView);

        mExerciseName = (TextView) itemView.findViewById(R.id.exercise_name_text);

        mAddRemoveExerciseSwitch = (Switch) itemView.findViewById(R.id.add_remove_exercise_switch);
        mAddRemoveExerciseSwitch.setVisibility(View.VISIBLE);
    }


    @Override
    public void bind(Exercise exercise, String exerciseKey) {
        super.bind(exercise, exerciseKey);
        mAddRemoveExerciseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Do something
                } else {
                    // Do something else
                }
            }
        });
    }
}
