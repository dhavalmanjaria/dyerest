package com.dhavalanjaria.dyerest;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.dhavalanjaria.dyerest.models.ExercisePoints;
import com.dhavalanjaria.dyerest.models.MockData;
import com.dhavalanjaria.dyerest.viewholders.ExerciseHistoryViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExerciseHistoryActivity extends BaseActivity {

    public static final String TAG = "ExerciseHistoryActivity";
    public static final String EXTRA_WORKOUT_DAY = TAG + ".WorkoutDay";

    private RecyclerView mPointsByExerciseRecycler;
    private DatabaseReference mReference;
    private FragmentPagerAdapter mPagerAdapter;
    private ExerciseHistoryAdapter adapter;
    private List<DatabaseReference> mExerciseRefs;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ExerciseHistoryActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_history);

        DatabaseReference allExercisesRef = BaseActivity.getRootDataReference().child("exercises");

        mPointsByExerciseRecycler = findViewById(R.id.points_by_exercise_recycler);
        mPointsByExerciseRecycler.setLayoutManager(new LinearLayoutManager(this));

        mExerciseRefs = new ArrayList<>();
        allExercisesRef.orderByKey()
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot exerciseSnap: dataSnapshot.getChildren()) {
                    mExerciseRefs.add(exerciseSnap.getRef());

                }

                mPointsByExerciseRecycler.setAdapter(new ExerciseHistoryAdapter(mExerciseRefs));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public Query getQuery() {
        return null;
    }

    private class ExerciseHistoryAdapter extends RecyclerView.Adapter<ExerciseHistoryViewHolder> {

        private List<DatabaseReference> mModel;

        public ExerciseHistoryAdapter(List<DatabaseReference> model) {
            mModel = model;
        }

        @Override
        public ExerciseHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = getLayoutInflater().inflate(R.layout.points_by_exercise_item, parent, false);

            return new ExerciseHistoryViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ExerciseHistoryViewHolder holder, int position) {
            holder.bind(mModel.get(position));
        }

        @Override
        public int getItemCount() {
            return mModel.size();
        }
    }
}
