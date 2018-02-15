package com.dhavalanjaria.dyerest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dhavalanjaria.dyerest.BaseActivity;
import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.fragments.NewDayDialogFragment;
import com.dhavalanjaria.dyerest.fragments.NewWorkoutDialogFragment;
import com.dhavalanjaria.dyerest.models.MockData;
import com.dhavalanjaria.dyerest.models.Workout;
import com.dhavalanjaria.dyerest.models.WorkoutDay;
import com.dhavalanjaria.dyerest.viewholders.WorkoutDayViewHolder;

import java.util.ArrayList;
import java.util.List;

import static com.dhavalanjaria.dyerest.MainActivity.DIALOG_NEW_WORKOUT;

/**
 * Created by Dhaval Anjaria on 2/7/2018.
 */

public class WorkoutDetailActivity extends BaseActivity {

    public static final String EXTRA_WORKOUT_NAME = "WorkoutDetailActivity.WorkoutDay";
    public static final String DIALOG_NEW_WORKOUT_DAY = "NewWorkoutDay";

    private TextView mDayName;
    private Button mLaunchButton;
    private RecyclerView mRecyclerContainer;
    private WorkoutDayAdapter mDayAdapter;
    private TextView mNoDayText;

    public static Intent newIntent(Context context, String workoutName) {
        Intent intent = new Intent(context, WorkoutDetailActivity.class);
        intent.putExtra(EXTRA_WORKOUT_NAME, workoutName);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail);

        mRecyclerContainer = (RecyclerView) findViewById(R.id.workout_detail_container);
        //TODO: Set empty text view

        mRecyclerContainer.setLayoutManager(new LinearLayoutManager(this));

        mDayAdapter = new WorkoutDayAdapter();
        updateUI();
    }

    private class WorkoutDayAdapter extends RecyclerView.Adapter<WorkoutDayViewHolder> {

        private List<WorkoutDay> mWorkoutDayModel;

        public WorkoutDayAdapter() {
            mWorkoutDayModel = MockData.getWorkoutDays();
        }

        @Override
        public WorkoutDayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View workoutDayView = getLayoutInflater().inflate(R.layout.workout_detail_item, parent,
                    false);

            return new WorkoutDayViewHolder(workoutDayView, getLayoutInflater());
        }

        @Override
        public void onBindViewHolder(WorkoutDayViewHolder holder, int position) {
            WorkoutDay day = mWorkoutDayModel.get(position);
            holder.bind(day);
        }

        @Override
        public int getItemCount() {
            return mWorkoutDayModel.size();
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


}
