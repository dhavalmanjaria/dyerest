package com.dhavalanjaria.dyerest.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dhavalanjaria.dyerest.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by Dhaval Anjaria on 4/10/2018.
 */

public class ExerciseCommentsViewHolder extends RecyclerView.ViewHolder {

    private TextView mCommentText;
    private Button mDelButton;

    public ExerciseCommentsViewHolder(View itemView) {
        super(itemView);

        mCommentText = itemView.findViewById(R.id.exercise_comment_text);
        mDelButton = itemView.findViewById(R.id.exercise_comment_del_button);
    }

    public void bind(final DatabaseReference exerciseCommentRef, View.OnClickListener onDeleteListener) {
        exerciseCommentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = (String) dataSnapshot.child("text").getValue();
                mCommentText.setText(text);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDelButton.setOnClickListener(onDeleteListener);
    }
}
