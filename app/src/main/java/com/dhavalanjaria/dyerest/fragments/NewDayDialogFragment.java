package com.dhavalanjaria.dyerest.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

import com.dhavalanjaria.dyerest.helpers.OnDialogCompletedListener;
import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.WorkoutDetailActivity;

/**
 * Created by Dhaval Anjaria on 2/15/2018.
 */

public class NewDayDialogFragment extends DialogFragment {

    private OnDialogCompletedListener mDialogCompletedListener;

    private String workoutDayName;

    public String getWorkoutDayName() {
        return workoutDayName;
    }

    private void setWorkoutDayName(String workoutDayName) {
        this.workoutDayName = workoutDayName;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        final EditText workoutDayName = new EditText(getActivity());
        workoutDayName.setHint(R.string.add_day);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.new_day_name)
                .setView(workoutDayName)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setWorkoutDayName(workoutDayName.getText().toString());
                        mDialogCompletedListener.onDialogComplete(getWorkoutDayName());
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mDialogCompletedListener = (WorkoutDetailActivity)context;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnDialogCompletedListener");
        }
    }
}
