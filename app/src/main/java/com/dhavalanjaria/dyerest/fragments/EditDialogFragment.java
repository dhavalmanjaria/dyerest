package com.dhavalanjaria.dyerest.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

import com.dhavalanjaria.dyerest.OnDialogCompletedListener;

import static android.text.style.TtsSpan.ARG_TEXT;

/**
 * Created by Dhaval Anjaria on 2/26/2018.
 */

/**
 * Provides a DialogFragment with an EditText and OK/Cancel buttons. When OK is clicked this fragment
 * calls the OnDialogCompletedListener.OnDialogComplete which should be defined by the Activity that
 * is creating this Dialog.
 */
public class EditDialogFragment extends DialogFragment {
    private static final String ARG_TITLE = "EditDialogFragment.Title";
    private static final String ARG_LISTENER = "EditDialogFragment.Listener";
    private static final String ARG_CONTEXT = "EditDialogFragment.Context";
    private String mTitle;
    private OnDialogCompletedListener mDialogCompletedListener;
    private EditText mEditText;
    private DialogInterface.OnClickListener mCancelListener;

    public EditDialogFragment(OnDialogCompletedListener listener) {
        this.mDialogCompletedListener = listener;
    }

    public static EditDialogFragment newInstance(String title, OnDialogCompletedListener listener) throws ClassCastException {
        Bundle args = new Bundle();

        EditDialogFragment fragment = new EditDialogFragment(listener);
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public OnDialogCompletedListener getDialogCompletedListener() {
        return mDialogCompletedListener;
    }

    public void setCancelListener(DialogInterface.OnClickListener cancelListener) {
        mCancelListener = cancelListener;
    }

    public void setDialogCompletedListener(OnDialogCompletedListener dialogCompletedListener) {
        mDialogCompletedListener = dialogCompletedListener;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        mTitle = getArguments().getString(ARG_TITLE);
        mEditText = new EditText(getContext());


        return new AlertDialog.Builder(getActivity())
                .setTitle(getTitle())
                .setView(mEditText)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getArguments().get(ARG_TEXT);
                        String text = mEditText.getText().toString();
                        if (text != null)
                            mDialogCompletedListener.onDialogComplete(text);
                        else
                        {
                            throw new IllegalStateException("No text from EditText in Dialog");
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, mCancelListener)
                .create();
    }
}
