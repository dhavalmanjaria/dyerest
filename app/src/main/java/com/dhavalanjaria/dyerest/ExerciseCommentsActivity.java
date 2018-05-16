package com.dhavalanjaria.dyerest;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.dhavalanjaria.dyerest.helpers.OnDialogCompletedListener;
import com.dhavalanjaria.dyerest.fragments.EditDialogFragment;
import com.dhavalanjaria.dyerest.viewholders.ExerciseCommentsViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExerciseCommentsActivity extends BaseActivity {

    private static final String TAG = "ExerciseCommentsActivity";
    private static final String EXTRA_EXERCISE_URL = TAG + "ExerciseUrl";
    private FloatingActionButton mNewCommentButton;
    private RecyclerView mCommentsRecycler;
    private DatabaseReference mExerciseRef;
    private ExerciseCommentsAdapter mCommentsAdapter;
    private List<DatabaseReference> mCommentsRefList;

    public static Intent newIntent(Context context, String exerciseUrl) {
        Intent intent = new Intent(context, ExerciseCommentsActivity.class);
        intent.putExtra(EXTRA_EXERCISE_URL, exerciseUrl);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_comments);

        String exerciseUrl = (String) getIntent().getSerializableExtra(EXTRA_EXERCISE_URL);
        mExerciseRef = FirebaseDatabase.getInstance().getReferenceFromUrl(exerciseUrl);

        mNewCommentButton = findViewById(R.id.add_comment_button);
        mCommentsRecycler = findViewById(R.id.exercise_comments_recycler);

        mCommentsRecycler.setLayoutManager(new LinearLayoutManager(this));

        mCommentsRefList = new ArrayList<>();

        mExerciseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("comments").getValue() == null) {
                    return;
                }

                for (DataSnapshot childSnap: dataSnapshot.child("comments").getChildren()) {
                    mCommentsRefList.add(childSnap.getRef());
                }
                mCommentsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mCommentsAdapter = new ExerciseCommentsAdapter(mCommentsRefList);
        mCommentsRecycler.setAdapter(mCommentsAdapter);

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
                dialog.show(fragmentManager, TAG);
            }
        });
    }

    public void addNewComment(String newCommentText) {
        DatabaseReference newCommentRef = mExerciseRef.child("comments").push();

        newCommentRef.child("text").setValue(newCommentText);
        mCommentsRefList.add(newCommentRef);
        mCommentsAdapter.notifyDataSetChanged();
    }


    private class ExerciseCommentsAdapter extends RecyclerView.Adapter<ExerciseCommentsViewHolder> {

        private List<DatabaseReference> mModel;

        public ExerciseCommentsAdapter(List<DatabaseReference> model) {
            mModel = model;
        }

        @Override
        public ExerciseCommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = getLayoutInflater().inflate(R.layout.exercise_comment_item, parent, false);

            return new ExerciseCommentsViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ExerciseCommentsViewHolder holder, final int position) {
            holder.bind(mModel.get(position), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mModel.get(position).removeValue();
                    mModel.remove(position);
                    mCommentsAdapter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mModel.size();
        }
    }
    public Query getQuery() {
        return null;
    }
}
