package com.dhavalanjaria.dyerest;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.dhavalanjaria.dyerest.models.DayPerformed;
import com.dhavalanjaria.dyerest.models.MockData;
import com.dhavalanjaria.dyerest.viewholders.DayPerformedViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.List;

public class ExerciseHistoryActivity extends BaseActivity {

    public static final String TAG = "ExerciseHistoryActivity";
    public static final String EXTRA_WORKOUT_DAY = TAG + ".WorkoutDay";

    private RecyclerView mPointsByDateRecycler;
    private DatabaseReference mReference;
    private FragmentPagerAdapter mPagerAdapter;
    private GetScreenshotAdapter adapter;

    public static Intent newIntent(Context context, DatabaseReference workoutDayRef) {
        Intent intent = new Intent(context, ExerciseHistoryActivity.class);
        //TODO: See Option A for Schema.
        intent.putExtra(EXTRA_WORKOUT_DAY, workoutDayRef.toString());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_history);

        mPointsByDateRecycler = findViewById(R.id.points_by_date_recycler);
        mPointsByDateRecycler.setAdapter(new GetScreenshotAdapter());
        mPointsByDateRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public Query getQuery() {
        return null;
    }

    private class GetScreenshotAdapter extends RecyclerView.Adapter<DayPerformedViewHolder> {

        private List<DayPerformed> mModel;

        public GetScreenshotAdapter() {
            mModel = MockData.getDayPerformed();
        }

        @Override
        public DayPerformedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = getLayoutInflater().inflate(R.layout.points_by_date_item, parent, false);

            return new DayPerformedViewHolder(v);
        }

        @Override
        public void onBindViewHolder(DayPerformedViewHolder holder, int position) {
            holder.bind(mModel.get(position));
        }

        @Override
        public int getItemCount() {
            return mModel.size();
        }
    }
}
