package com.dhavalanjaria.dyerest.viewholders;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.dhavalanjaria.dyerest.ActiveWorkoutActivity;
import com.dhavalanjaria.dyerest.AddExerciseToDayActivity;
import com.dhavalanjaria.dyerest.BaseActivity;
import com.dhavalanjaria.dyerest.EditDaySequenceActivity;
import com.dhavalanjaria.dyerest.OnDialogCompletedListener;
import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.fragments.EditDialogFragment;
import com.dhavalanjaria.dyerest.models.DayExercise;
import com.dhavalanjaria.dyerest.models.ToDeleteExercise;
import com.dhavalanjaria.dyerest.models.WorkoutDay;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Dhaval Anjaria on 2/7/2018.
 */

public class WorkoutDayViewHolder extends RecyclerView.ViewHolder {

    private TextView mDayNameTextView;
    public static final String TAG = "WorkoutDayViewHolder";

    // Notes:
    // Might want to use a Fragment for the day detail views.
    // There is a way to use a RecyclerView inside a RecyclerView, however see the issues at:
    // https://stackoverflow.com/questions/34569217/how-to-add-a-recyclerview-inside-another-recyclerview
    // Also:
    // https://irpdevelop.wordpress.com/2016/02/10/horizontal-recyclerview-inside-a-vertical-recyclerview/
    private RecyclerView mExerciseListRecycler;
    private Button mLaunchButton;
    private Button mEditDayButton;
    private LayoutInflater mLayoutInflater;
    private Map<String, Object> lastPerformedData;
    private Map<String, Object> mTargetMap;

    public WorkoutDayViewHolder(final View itemView, LayoutInflater layoutInflater) {
        super(itemView);

        this.mLayoutInflater = layoutInflater;

        mDayNameTextView = itemView.findViewById(R.id.day_name);
        mExerciseListRecycler = itemView.findViewById(R.id.exercise_list_recycler);
        mLaunchButton = itemView.findViewById(R.id.launch_button);
        mEditDayButton = itemView.findViewById(R.id.edit_day_button);

    }

    public void bind(final String workoutDayKey) {

        final DatabaseReference ref = BaseActivity.getRootDataReference()
                .child("days")
                .child(workoutDayKey);

        Log.d(TAG, workoutDayKey);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                WorkoutDay day = dataSnapshot.getValue(WorkoutDay.class);
                mDayNameTextView.setText(day.getName());

                if (dataSnapshot.child("exercises").getValue() == null) {
                    mLaunchButton.setEnabled(false);
                    mLaunchButton.setFocusable(false);
                } else {
                    mLaunchButton.setEnabled(true);
                    mLaunchButton.setFocusable(true);
                }
                // Other Views go here
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
                Log.d(TAG, databaseError.getDetails());
            }
        });

        Query query = ref.child("exercises").orderByChild("sequenceNumber");
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<DayExercise>()
                .setQuery(query, DayExercise.class)
                .build();

        ExercisePreviewAdapter exercisePreviewAdapter = new ExercisePreviewAdapter(options);
        exercisePreviewAdapter.startListening();

        mExerciseListRecycler.setAdapter(exercisePreviewAdapter);
        mExerciseListRecycler.setLayoutManager(new LinearLayoutManager(itemView.getContext(),
                LinearLayoutManager.HORIZONTAL, false));


        mLaunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getAdapterPosition should technically work.
                // However, expect errors
                // Replace with String workoutKey
                Intent intent = ActiveWorkoutActivity.newIntent(v.getContext(), ref.toString());
                v.getContext().startActivity(intent);
            }
        });

        mEditDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(itemView.getContext(), mEditDayButton);
                menu.inflate(R.menu.edit_workout_day_menu);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent;
                        switch (item.getItemId()) {
                            case R.id.edit_workout_day_name_item:
                                FragmentManager manager = ((AppCompatActivity)itemView.getContext())
                                        .getSupportFragmentManager();
                                EditDialogFragment editDialogFragment = EditDialogFragment.newInstance("Edit Day", new OnDialogCompletedListener() {
                                    @Override
                                    public void onDialogComplete(String text) {
                                        ref.child("name").setValue(text);
                                    }
                                });
                                editDialogFragment.show(manager, "WorkoutDayViewHolder");
                                return true;
                            case R.id.edit_workout_day_sequence_item:
                                intent = EditDaySequenceActivity.newIntent(itemView.getContext(), ref.toString());
                                itemView.getContext().startActivity(intent);
                                return true;
                            case R.id.edit_workout_day_exercises_item:
                                intent = AddExerciseToDayActivity.newIntent(itemView.getContext(),
                                        ref);
                                itemView.getContext().startActivity(intent);
                                return true;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                menu.show();
            }
        });
    }


    private class ExercisePreviewAdapter extends FirebaseRecyclerAdapter<DayExercise, DayExercisePreviewViewHolder> {

        public ExercisePreviewAdapter(@NonNull FirebaseRecyclerOptions<DayExercise> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull DayExercisePreviewViewHolder holder, int position,
                                        @NonNull DayExercise model) {
            holder.bind(model, getRef(position));

        }

        @Override
        public DayExercisePreviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View exerciseView = mLayoutInflater.inflate(R.layout.workout_detail_exercise_item, parent,
                    false);

            return new DayExercisePreviewViewHolder(exerciseView);
        }
    }

    private class DayExercisePreviewViewHolder extends RecyclerView.ViewHolder {

        private TextView mExercisePoints;
        private TextView mExerciseName;
        private TextView mExerciseTarget;
        private TextView mPreviousDate;

        public DayExercisePreviewViewHolder(View itemView) {
            super(itemView);

            mExercisePoints = itemView.findViewById(R.id.exercise_points);
            mExerciseName = itemView.findViewById(R.id.exercise_name);
            mExerciseTarget = itemView.findViewById(R.id.exercise_target);
            mPreviousDate = itemView.findViewById(R.id.previous_date);

        }

        public void bind(DayExercise exercise, DatabaseReference ref) {
            String exerciseKey = ref.getKey();

            final DatabaseReference exerciseRef = BaseActivity.getRootDataReference().child("exercises")
                    .child(exerciseKey);

            Map<String, Object> targetMap = getTargetMap(exerciseRef);

            exerciseRef.child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = (String) dataSnapshot.getValue();
                    mExerciseName.setText(name);
                    // Other Exercise Views here.
                    Map<String, Object> targetMap = getTargetMap(exerciseRef);

                    if (targetMap != null) {
                        setTargetPointsFromMap(targetMap , mExercisePoints);
                        setTargetValuesFromMap(targetMap , mExerciseTarget);
                        setLastPerformedFromMap(targetMap , mPreviousDate);
                    } else {

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    /**
     * Stores the exercise target node into a Map<> so that queries don't need to go out to Firebase
     * @param exerciseRef Reference to the exercise used to find the target.
     * @return Map<String, Object> Structure containing the exercise target node with one
     * modification: the Map<> also has a node "date" containing the date.
     */
    public Map<String, Object> getTargetMap(DatabaseReference exerciseRef) {

            DatabaseReference ref = BaseActivity.getRootDataReference()
                    .child("targets")
                    .child(exerciseRef.getKey());

            if (ref.getKey() == null) {
                mTargetMap = null;
                return null;
            }

            final Map<String, Object> retval = new HashMap<>();

            ref.orderByKey().limitToLast(1)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() == null) {
                                mTargetMap = null;
                            } else {
                                dataSnapshot = dataSnapshot.getChildren().iterator().next();

                                retval.put("date", dataSnapshot.getRef().getKey());
                                retval.putAll((Map<String, Object>) dataSnapshot.getValue());

                                mTargetMap = retval;
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
            return mTargetMap;
    }

    public void setTargetPointsFromMap(Map<String, Object> targetMap, final TextView v) {
        String pointsStr = (long) targetMap.get("points") + "";
        v.setText(pointsStr);
    }

    public void setTargetValuesFromMap(Map<String, Object> targetMap, final TextView v) {


        Map<String, Object> valuesMap = (Map<String, Object>) targetMap.get("values");

        List<String> bufstring = new LinkedList<>();
        for (String key: valuesMap.keySet()) {
            bufstring.add(valuesMap.get(key).toString());
            bufstring.add(" | ");
        }
        bufstring.remove(bufstring.size() - 1);

        String targetString = "";
        for (String s: bufstring) {
            targetString += s;
        }
        v.setText(targetString);
    }

    public void setLastPerformedFromMap(Map<String, Object> targetMap, final TextView v) {

        String dateStr = targetMap.get("date") + "";
        long dateLong = Long.parseLong(dateStr);

        Date date = new Date(dateLong);

        String formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(date);

        v.setText(formattedDate);
    }
}
