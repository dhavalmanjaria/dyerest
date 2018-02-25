package com.dhavalanjaria.dyerest.fragments;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dhavalanjaria.dyerest.BaseActivity;
import com.dhavalanjaria.dyerest.OnDialogCompletedListener;
import com.dhavalanjaria.dyerest.R;
import com.google.firebase.database.Query;

public class ExerciseCommentsActivity extends BaseActivity {

    private static final String DIALOG_NEW_COMMENT = "ExerciseCommentsActvitity.NewComment";
    private FloatingActionButton mNewCommentButton;
    private RecyclerView mCommentsRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_comments);

        mNewCommentButton = findViewById(R.id.add_comment_button);
        mCommentsRecycler = findViewById(R.id.exercise_comments_recycler);

        mNewCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                EditDialogFragment dialog = EditDialogFragment.newInstance("New Comment", new OnDialogCompletedListener() {
                    @Override
                    public void onDialogComplete(String text) {
                        addNewComment(text);
                    }
                });
                dialog.show(fragmentManager, DIALOG_NEW_COMMENT);
            }
        });
    }

    public void addNewComment(String newCommentText) {
        // Add new comment to database.
    }

    @Override
    public Query getQuery() {
        return null;
    }
}

/*
*Made some major changes with the UI and Dialogs

This commit brings several updates. The first being that Exercise Comments are now wired up to the rest of the Activities and so is the Exercise Guide feature. These activities are not yet hooked up to Firebase however. This comment also brings changes to the default Button style which has now been updated for all Activities. Structural changes are also made in the way the code uses DialogFragments. There is now a common EditDialogFragment that connects
 */