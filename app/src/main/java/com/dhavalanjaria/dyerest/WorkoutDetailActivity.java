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

import com.dhavalanjaria.dyerest.fragments.EditDialogFragment;
import com.dhavalanjaria.dyerest.models.WorkoutDay;
import com.dhavalanjaria.dyerest.viewholders.WorkoutDayViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dhaval Anjaria on 2/7/2018.
 */

public class WorkoutDetailActivity extends BaseActivity implements OnDialogCompletedListener {

    public static final String EXTRA_WORKOUT_ID = "WorkoutDetailActivity.WorkoutDay";
    public static final String DIALOG_NEW_WORKOUT_DAY = "NewWorkoutDay";
    public static final String KEY_WORKOUT_ID = "WorkoutDetailActivity.mWorkoutDaysReference";

    private DatabaseReference mWorkoutDaysReference;
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

        String workoutId = (String) getIntent().getSerializableExtra(EXTRA_WORKOUT_ID);

        if (savedInstanceState != null) {
            workoutId = savedInstanceState.getString(KEY_WORKOUT_ID);
        }

        mWorkoutDaysReference = getRootDataReference()
                .child("workouts")
                .child(getUserId())
                .child(workoutId)
                .child("days");

        mRecyclerContainer = (RecyclerView) findViewById(R.id.workout_detail_container);
        mRecyclerContainer.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<WorkoutDay> options = new FirebaseRecyclerOptions.Builder<WorkoutDay>()
                .setQuery(getQuery(), WorkoutDay.class)
                .build();

        mDayAdapter = new WorkoutDayAdapter(options);
        updateUI();
    }

    public void addWorkoutDay(String workoutDayName) {
        WorkoutDay newDay = new WorkoutDay(workoutDayName);

        String newWorkoutDayKey = mWorkoutDaysReference.push().getKey();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(newWorkoutDayKey, newDay.toMap());
        mWorkoutDaysReference.updateChildren(childUpdates);
        mDayAdapter.notifyDataSetChanged();
    }

    private class WorkoutDayAdapter extends FirebaseRecyclerAdapter<WorkoutDay, WorkoutDayViewHolder> {

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
           holder.bind(model, getRef(position));
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
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.add_day_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.add_day_menu_item:
                FragmentManager fragmentManager = getSupportFragmentManager();
                EditDialogFragment fragment = EditDialogFragment.newInstance("New Day Name", new OnDialogCompletedListener() {
                    @Override
                    public void onDialogComplete(String text) {
                        addWorkoutDay(text);
                    }
                });
                fragment.show(fragmentManager, "WorkoutDetailActivity");
                return true;
            case R.id.edit_all_exercises_menu_item:
                Intent intent = ExerciseListActivity.newIntent(this, mWorkoutDaysReference, false);
                startActivity(intent);
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
    protected void onStart() {
        super.onStart();
        if (mDayAdapter != null) {
            mDayAdapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mDayAdapter != null) {
            mDayAdapter.stopListening();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // getParent because the reference points to the "days" child
        outState.putSerializable(KEY_WORKOUT_ID, mWorkoutDaysReference.getParent().getKey());
    }

    @Override
    public Query getQuery() {
        return mWorkoutDaysReference.orderByChild("name");
    }



    @Override
    public void onDialogComplete(String text) {
        addWorkoutDay(text);
    }
}
