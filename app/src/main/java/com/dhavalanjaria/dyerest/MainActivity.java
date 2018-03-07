package com.dhavalanjaria.dyerest;



import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dhavalanjaria.dyerest.fragments.EditDialogFragment;
import com.dhavalanjaria.dyerest.models.Workout;
import com.dhavalanjaria.dyerest.viewholders.WorkoutCardViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {

    public static final String DIALOG_NEW_WORKOUT = "NewWorkout";
    private static final int REQUEST_WORKOUT_NAME = 1 ;
    public static final String TAG = "MainActivity";

    private DatabaseReference mUserWorkoutsReference;

    private RecyclerView mRecyclerView;
    private WorkoutCardAdapter mWorkoutCardAdapter;
    private FloatingActionButton mNewWorkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Point directly to the current user's workouts.
        mUserWorkoutsReference = getRootDataReference().child("workouts");

        mRecyclerView = (RecyclerView) findViewById(R.id.card_container);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Workout>()
                .setQuery(getQuery(), Workout.class)
                .build();

        mWorkoutCardAdapter = new WorkoutCardAdapter(options);
        mRecyclerView.setAdapter(mWorkoutCardAdapter);
        mNewWorkoutButton = (FloatingActionButton) findViewById(R.id.add_workout_fab);

        // This may possibly be a bad strategy to handle data exchange between a dialog and an activity
        mNewWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                EditDialogFragment dialogFragment = EditDialogFragment.newInstance("New Workout", new OnDialogCompletedListener() {
                    @Override
                    public void onDialogComplete(String text) {
                        addWorkout(text);
                    }
                });
                dialogFragment.show(fragmentManager, TAG);
            }
        });
    }

    private class WorkoutCardAdapter extends FirebaseRecyclerAdapter<Workout, WorkoutCardViewHolder> {

        public WorkoutCardAdapter(@NonNull FirebaseRecyclerOptions<Workout> options) {
            super(options);
        }

        @Override
        public WorkoutCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View cardView = inflater.inflate(R.layout.workout_card_item, parent, false);

            return new WorkoutCardViewHolder(cardView);
        }


        @Override
        protected void onBindViewHolder(@NonNull WorkoutCardViewHolder holder, int position, @NonNull Workout model) {
            holder.bind(model, getRef(position).getKey());
        }
    }

    /**
     * This function adds a new workout with the workout name, which should be called by the
     * DialogFragment. For now this seems like more expedient solution than implementing a listener,
     * or using Fragments for all Activities which I should've done in the first place.
     * @param workoutName
     */
    public void addWorkout(String workoutName) {
        String newWorkoutKey = mUserWorkoutsReference
                .push().getKey();

        String formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

        Workout newWorkout = new Workout(workoutName, new Date(), 0, new HashMap<String, Object>());
        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put(newWorkoutKey, newWorkout.toMap());
        mUserWorkoutsReference.updateChildren(childUpdates);
        mWorkoutCardAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mWorkoutCardAdapter != null) {
            mWorkoutCardAdapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mWorkoutCardAdapter != null) {
            mWorkoutCardAdapter.stopListening();
        }
    }

    @Override
    public Query getQuery() {
        Query query = mUserWorkoutsReference.orderByChild("dateCreated");
        return query;
    }
}
