package com.dhavalanjaria.dyerest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dhavalanjaria.dyerest.fragments.NewDayDialogFragment;
import com.dhavalanjaria.dyerest.models.Workout;
import com.dhavalanjaria.dyerest.models.WorkoutDay;
import com.dhavalanjaria.dyerest.viewholders.WorkoutDayViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.List;

/**
 * Created by Dhaval Anjaria on 2/7/2018.
 */

public class WorkoutDetailActivity extends BaseActivity {

    public static final String EXTRA_WORKOUT_ID = "WorkoutDetailActivity.WorkoutDay";
    public static final String DIALOG_NEW_WORKOUT_DAY = "NewWorkoutDay";

    private DatabaseReference mWorkoutReference;
    private TextView mDayName;
    private Button mLaunchButton;
    private RecyclerView mRecyclerContainer;
    private WorkoutDayAdapter mDayAdapter;
    private TextView mNoDayText;

    public static Intent newIntent(Context context, String workoutId) {
        Intent intent = new Intent(context, WorkoutDetailActivity.class);
        intent.putExtra(EXTRA_WORKOUT_ID, workoutId);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail);

        mWorkoutReference = getRootDataReference().child(getUserId());

        mRecyclerContainer = (RecyclerView) findViewById(R.id.workout_detail_container);

        mRecyclerContainer.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<WorkoutDay> options = new FirebaseRecyclerOptions.Builder<WorkoutDay>()
                .setQuery(getQuery(), WorkoutDay.class)
                .build();

        mDayAdapter = new WorkoutDayAdapter(options);
        updateUI();
    }

    private class WorkoutDayAdapter extends FirebaseRecyclerAdapter<WorkoutDay, WorkoutDayViewHolder> {

        private List<WorkoutDay> mWorkoutDayModel;

        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public WorkoutDayAdapter(@NonNull FirebaseRecyclerOptions<WorkoutDay> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull WorkoutDayViewHolder holder, int position, @NonNull WorkoutDay model) {
            holder.bind(model);
        }

        @Override
        public WorkoutDayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View workoutDayView = getLayoutInflater().inflate(R.layout.workout_detail_item, parent,
                    false);

            return new WorkoutDayViewHolder(workoutDayView, getLayoutInflater());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.add_day_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_day_menu_item:
                FragmentManager fragmentManager = getSupportFragmentManager();
                NewDayDialogFragment dialog = new NewDayDialogFragment();
                dialog.show(fragmentManager, DIALOG_NEW_WORKOUT_DAY);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {

        mRecyclerContainer.setAdapter(mDayAdapter);
        mDayAdapter.notifyDataSetChanged();
    }

    @Override
    public Query getQuery() {

        // This returns the wrong tree (FOR NOW)
        //TODO: Fix tree
        String workoutId = (String) getIntent().getSerializableExtra(EXTRA_WORKOUT_ID);

        return mWorkoutReference.child(workoutId);
    }
}
