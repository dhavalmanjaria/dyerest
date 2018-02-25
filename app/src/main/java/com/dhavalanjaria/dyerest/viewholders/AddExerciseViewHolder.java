package com.dhavalanjaria.dyerest.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;

import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.models.Exercise;

/**
 * Created by Dhaval Anjaria on 2/14/2018.
 */

public class AddExerciseViewHolder extends RecyclerView.ViewHolder {

    private TextView mExerciseName;
    private Switch mAddRemoveExerciseSwitch;
    private TextView mOptionsView;
    private boolean mAddingToDay;

    public AddExerciseViewHolder(View itemView, boolean addingToDay) {
        super(itemView);
        mAddingToDay = addingToDay;

        mExerciseName = (TextView) itemView.findViewById(R.id.exercise_name_text);

        mAddRemoveExerciseSwitch = (Switch) itemView.findViewById(R.id.add_remove_exercise_switch);
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

        mOptionsView = itemView.findViewById(R.id.add_edit_exercise_text);


        // Add setChecked here if the exercise is already added.


    }

    public void bind(Exercise exercise) {
        mExerciseName.setText(exercise.getName());

        // Code Smell: this should be polymorphic.
        if (mAddingToDay) {
            mAddRemoveExerciseSwitch.setVisibility(View.VISIBLE);
            mOptionsView.setVisibility(View.INVISIBLE);
        } else {
            mAddRemoveExerciseSwitch.setVisibility(View.INVISIBLE);
            mOptionsView.setVisibility(View.VISIBLE);
        }

        mOptionsView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Code smell, code repeated from ExerciseFieldViewHolder
                    PopupMenu menu = new PopupMenu(itemView.getContext(), mOptionsView);
                    menu.inflate(R.menu.edit_delete_popup_menu);

                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.edit_item:
                                    // Handle menu edit click
                                    return true;
                                case R.id.delete_item:
                                    return true;
                                default:
                                    break;
                            }
                            return false;
                        }
                    });
                    menu.show();
                }
            });

    }
}
