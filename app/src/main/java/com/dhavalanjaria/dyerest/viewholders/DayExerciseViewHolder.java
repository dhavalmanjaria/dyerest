package com.dhavalanjaria.dyerest.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.models.DayExercise;
import com.dhavalanjaria.dyerest.models.Exercise;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Dhaval Anjaria on 2/26/2018.
 */

public class DayExerciseViewHolder extends RecyclerView.ViewHolder {

    private TextView mSequenceNumberText;
    private TextView mExerciseNameText;
    private TextView mOptionsMenu;

    public DayExerciseViewHolder(View itemView) {
        super(itemView);

        mSequenceNumberText = itemView.findViewById(R.id.exercise_sequence_number_text);
        mExerciseNameText = itemView.findViewById(R.id.edit_sequence_exercise_name_text);
        mOptionsMenu = itemView.findViewById(R.id.options_menu_text);

    }

    public void bind(DayExercise model, String exerciseName) {
        mExerciseNameText.setText(exerciseName);
        mSequenceNumberText.setText(model.getSequenceNumber() + "");

        PopupMenu menu = new PopupMenu(itemView.getContext(), mOptionsMenu);
        menu.inflate(R.menu.edit_delete_popup_menu);

    }

    // For use in Firebase
    public void bind(DayExercise model, DatabaseReference workoutDayReference, DatabaseReference exerciseRef) {

        String exerciseName = exerciseRef.child("name").toString();
        mExerciseNameText.setText(exerciseName);
        mSequenceNumberText.setText(model.getSequenceNumber());

        PopupMenu menu = new PopupMenu(itemView.getContext(), mOptionsMenu);
        menu.inflate(R.menu.edit_delete_popup_menu);

        // Edit exercise with menu

    }
}
