package com.dhavalanjaria.dyerest.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.WorkoutDetailActivity;

/**
 * Created by Dhaval Anjaria on 2/23/2018.
 */
@Deprecated
public class NewCommentDialogFragment extends DialogFragment {


    private String mNewCommentText;

    public String getNewCommentText() {
        return mNewCommentText;
    }

    private void setNewCommentText(String newCommentText) {
        mNewCommentText = newCommentText;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        final EditText commentText = new EditText(getActivity());
        commentText.setHint(R.string.new_comment);

        return new AlertDialog.Builder(getActivity())
                .setView(commentText)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setNewCommentText(commentText.getText().toString());
                        // ((ExerciseCommentsActivity) getActivity()).addNewComment(getNewCommentText());
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

}

