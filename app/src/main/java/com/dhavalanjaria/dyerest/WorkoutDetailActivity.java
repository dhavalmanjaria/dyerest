package com.dhavalanjaria.dyerest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dhavalanjaria.dyerest.fragments.EditDialogFragment;
import com.dhavalanjaria.dyerest.models.MockData;
import com.dhavalanjaria.dyerest.models.WorkoutDay;
import com.dhavalanjaria.dyerest.viewholders.WorkoutDayViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dhaval Anjaria on 2/7/2018.
 */

public class WorkoutDetailActivity extends BaseActivity implements OnDialogCompletedListener {

    public static final String EXTRA_WORKOUT_ID = "WorkoutDetailActivity.WorkoutDay";
    public static final String DIALOG_NEW_WORKOUT_DAY = "NewWorkoutDay";
    public static final String KEY_WORKOUT_ID = "WorkoutDetailActivity.mWorkoutDaysReference";
    private static final String TAG = "WorkoutDetailActivity";

    private DatabaseReference mWorkoutDaysReference;
    private TextView mDayName;
    private Button mLaunchButton;
    private RecyclerView mRecyclerContainer;
    //private WorkoutDayAdapter mDayAdapter;
    private TextView mNoDayText;
    private String mWorkoutId;
    private List<String> mDayRefList;

    public static Intent newIntent(Context context, String workoutId) {
        Intent intent = new Intent(context, WorkoutDetailActivity.class);
        intent.putExtra(EXTRA_WORKOUT_ID, workoutId);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail);

        mWorkoutId = (String) getIntent().getSerializableExtra(EXTRA_WORKOUT_ID);
        mDayRefList = new ArrayList<>();

        if (savedInstanceState != null) {
            mWorkoutId = savedInstanceState.getString(KEY_WORKOUT_ID);
        }

        mWorkoutDaysReference = getRootDataReference()
                .child("workouts")
                .child(mWorkoutId)
                .child("days");

        mRecyclerContainer = (RecyclerView) findViewById(R.id.workout_detail_container);
        mRecyclerContainer.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<WorkoutDay> options = new FirebaseRecyclerOptions.Builder<WorkoutDay>()
                .setQuery(getQuery(), WorkoutDay.class)
                .build();

        final List<String> model = new ArrayList<>();

        mWorkoutDaysReference.addChildEventListener(new WorkoutChildListener(model));

        mRecyclerContainer.setAdapter(new DayRefListAdapter(model));
        // mRecyclerContainer.setAdapter(new GetScreenshotOfDetailAdapter());
        mRecyclerContainer.setLayoutManager(new LinearLayoutManager(this));

    }

    public void addWorkoutDay(String workoutDayName) {
        WorkoutDay newDay = new WorkoutDay(workoutDayName);

        String newWorkoutDayKey = mWorkoutDaysReference.push().getKey();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(newWorkoutDayKey, newDay.toMap());
        mWorkoutDaysReference.updateChildren(childUpdates);

        DatabaseReference workoutRef = BaseActivity.getRootDataReference()
                .child("workouts")
                .child(mWorkoutId);
        workoutRef.child("days").child(newWorkoutDayKey).setValue(true);

        BaseActivity.getRootDataReference()
                .child("days")
                .child(newWorkoutDayKey)
                .child("name")
                .setValue(workoutDayName);
    }

    private class DayRefListAdapter extends RecyclerView.Adapter<WorkoutDayViewHolder> {
        private List<String> mDayRefList;

        public DayRefListAdapter(List<String> model) {
            mDayRefList = model;
        }

        @Override
        public WorkoutDayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View workoutDayView = getLayoutInflater().inflate(R.layout.workout_detail_item, parent,
                    false);

            return new WorkoutDayViewHolder(workoutDayView, getLayoutInflater());
        }

        @Override
        public void onBindViewHolder(WorkoutDayViewHolder holder, int position) {
            holder.bind(mDayRefList.get(position));
        }

        @Override
        public int getItemCount() {
            return mDayRefList.size();
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
                Intent intent = ListAllExerciseActivity.newIntent(this, mWorkoutDaysReference);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
        Query retval = BaseActivity.getRootDataReference()
                .child("workouts")
                .child(mWorkoutId)
                .child("days");

        retval.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnap : dataSnapshot.getChildren()) {
                    //Log.d(TAG, (String)childSnap.getKey());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return retval;

    }

    @Override
    public void onDialogComplete(String text) {
        addWorkoutDay(text);
    }

    private class WorkoutChildListener implements ChildEventListener {

        List<String> mModel;

        public WorkoutChildListener(List<String> model) {
            this.mModel = model;
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            mModel.remove(dataSnapshot.getKey());
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            return;
        }

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            mModel.add(dataSnapshot.getKey());
            Log.d(TAG, dataSnapshot.getKey());
        }
    }

}
