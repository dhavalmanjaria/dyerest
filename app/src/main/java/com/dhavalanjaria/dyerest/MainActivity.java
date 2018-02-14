package com.dhavalanjaria.dyerest;



import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dhavalanjaria.dyerest.models.MockData;
import com.dhavalanjaria.dyerest.models.Workout;
import com.dhavalanjaria.dyerest.viewholders.WorkoutCardViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    public static final String DIALOG_NEW_WORKOUT = "NewWorkout";

    private List<Workout> mWorkoutList;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mNewWorkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWorkoutList = MockData.getWorkouts();
        mRecyclerView = (RecyclerView) findViewById(R.id.card_container);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new WorkoutCardAdapter());
        mNewWorkoutButton = (FloatingActionButton) findViewById(R.id.add_workout_fab);

        // This may possibly be a bad strategy to handle data exchange between a dialog and an activity
        mNewWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                NewWorkoutDialogFragment dialog = new NewWorkoutDialogFragment();
                dialog.show(fragmentManager, DIALOG_NEW_WORKOUT);

                String newWorkoutName = dialog.getWorkoutName();
                if (newWorkoutName != null) {
                    //TODO: Create a new workout with the name and then edit it.

                }
            }
        });
    }

    private class WorkoutCardAdapter extends RecyclerView.Adapter<WorkoutCardViewHolder> {
        @Override
        public WorkoutCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View cardView = inflater.inflate(R.layout.workout_card_view, parent, false);

            return new WorkoutCardViewHolder(cardView);
        }

        @Override
        public void onBindViewHolder(WorkoutCardViewHolder holder, int position) {
            holder.bind(mWorkoutList.get(position));
        }

        @Override
        public int getItemCount() {
            return mWorkoutList.size();
        }
    }
}
