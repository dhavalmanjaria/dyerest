package com.dhavalanjaria.dyerest;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.dhavalanjaria.dyerest.models.ExerciseDatePoints;
import com.dhavalanjaria.dyerest.viewholders.ExerciseDatePointsViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExercisePointsByDateActivity extends BaseActivity {

    public static final String TAG = "ExercisePointsByDateActivity";
    public static final String EXTRA_EXERCISE_TARGET_URL = "ExercisePointsByDateActivity.ExerciseTargetUrl";
    private List<ExerciseDatePoints> mExerciseDatePointsList;
    private DatabaseReference mExerciseTargetsReference;
    private ExerciseDatePointsAdapter mAdapter;

    private ViewPager mViewPager;
    private RecyclerView mExercisePerformedRecycler;

    public static Intent newIntent(Context context, String exerciseTargetUrl) {
        Intent intent = new Intent(context, ExercisePointsByDateActivity.class);
        intent.putExtra(EXTRA_EXERCISE_TARGET_URL, exerciseTargetUrl);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_points_by_date);

        final String exerciseTargetUrl = (String) getIntent().getSerializableExtra(EXTRA_EXERCISE_TARGET_URL);
        DatabaseReference exerciseTargetRef = FirebaseDatabase.getInstance().getReferenceFromUrl(
                exerciseTargetUrl);

        mExercisePerformedRecycler = findViewById(R.id.points_by_date_recycler);
        mExercisePerformedRecycler.setLayoutManager(new LinearLayoutManager(this));

        mExerciseDatePointsList = new ArrayList<>();
        mAdapter = new ExerciseDatePointsAdapter(mExerciseDatePointsList);


        exerciseTargetRef.orderByKey()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot exerciseDateSnap: dataSnapshot.getChildren()) {
                            String dateKey = exerciseDateSnap.getKey();
                            Long dateLong = Long.valueOf(dateKey);
                            Date date = new Date(dateLong);

                            String pointsStr = exerciseDateSnap.child("points").getValue() + "";

                            int points = Integer.valueOf(pointsStr);
                            mExerciseDatePointsList.add(new ExerciseDatePoints(date, points));
                        }
                        mExercisePerformedRecycler.setAdapter(mAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }


    private class ExerciseDatePointsAdapter extends RecyclerView.Adapter<ExerciseDatePointsViewHolder> {

        private List<ExerciseDatePoints> mModel;

        public ExerciseDatePointsAdapter(List<ExerciseDatePoints> model) {
            mModel = model;
        }

        @Override
        public ExerciseDatePointsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = getLayoutInflater().inflate(R.layout.points_by_exercise_date_item, parent, false);

            return new ExerciseDatePointsViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ExerciseDatePointsViewHolder holder, int position) {
            holder.bind(mModel.get(position));
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
