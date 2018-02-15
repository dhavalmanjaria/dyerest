package com.dhavalanjaria.dyerest.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dhavalanjaria.dyerest.EditWorkoutActivity;
import com.dhavalanjaria.dyerest.R;

/**
 * Created by Dhaval Anjaria on 2/12/2018.
 */

public class NewWorkoutDialogFragment extends DialogFragment {

    private String mWorkoutName;

    // Note: this is not considered "best practice" exactly.
    // However for the sake of expediency, we will use getters and setters here.


    public String getWorkoutName() {
        return mWorkoutName;
    }

    // This may possibly be a bad strategy to handle data exchange between a dialog and an activity
    private void setWorkoutName(String workoutName) {
        mWorkoutName = workoutName;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final EditText workoutName = new EditText(getActivity());
        workoutName.setHint(R.string.new_workout_hint);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.new_workout)
                .setView(workoutName)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setWorkoutName(workoutName.getText().toString());
                        // the Intent should carry the workout name, later
                        startActivity(new Intent(getActivity(), EditWorkoutActivity.class));
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();

    }


}
