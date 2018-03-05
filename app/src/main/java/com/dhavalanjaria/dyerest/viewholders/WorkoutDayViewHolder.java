package com.dhavalanjaria.dyerest.viewholders;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.dhavalanjaria.dyerest.ActiveWorkoutActivity;
import com.dhavalanjaria.dyerest.AddExerciseToDayActivity;
import com.dhavalanjaria.dyerest.EditDaySequenceActivity;
import com.dhavalanjaria.dyerest.ExerciseListActivity;
import com.dhavalanjaria.dyerest.ListAllExerciseActivity;
import com.dhavalanjaria.dyerest.OnDialogCompletedListener;
import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.fragments.EditDialogFragment;
import com.dhavalanjaria.dyerest.models.Exercise;
import com.dhavalanjaria.dyerest.models.ExerciseMap;
import com.dhavalanjaria.dyerest.models.WorkoutDay;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dhaval Anjaria on 2/7/2018.
 */

public class WorkoutDayViewHolder extends RecyclerView.ViewHolder {

    private TextView mDayNameTextView;

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

    public WorkoutDayViewHolder(final View itemView, LayoutInflater layoutInflater) {
        super(itemView);

        this.mLayoutInflater = layoutInflater;

        mDayNameTextView = itemView.findViewById(R.id.day_name);
        mExerciseListRecycler = itemView.findViewById(R.id.exercise_list_recycler);
        mLaunchButton = itemView.findViewById(R.id.launch_button);
        mEditDayButton = itemView.findViewById(R.id.edit_day_button);

        mExerciseListRecycler.setLayoutManager(new LinearLayoutManager(itemView.getContext(),
                LinearLayoutManager.HORIZONTAL, false));

    }

    public void bind(final WorkoutDay workoutDay, final DatabaseReference workoutDayRef) {
        mDayNameTextView.setText(workoutDay.getName());

        ExerciseAdapter adapter = new ExerciseAdapter(workoutDay);
        mExerciseListRecycler.setAdapter(adapter);

        mLaunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getAdapterPosition should technically work.
                // However, expect errors
                // Replace with String workoutKey
                Intent intent = ActiveWorkoutActivity.newIntent(v.getContext(), 0);
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
                                        workoutDayRef.child("name").setValue(text);
                                    }
                                });
                                editDialogFragment.show(manager, "WorkoutDayViewHolder");
                                return true;
                            case R.id.edit_workout_day_sequence_item:
                                intent = EditDaySequenceActivity.newIntent(itemView.getContext(), workoutDayRef.toString());
                                itemView.getContext().startActivity(intent);
                                return true;
                            case R.id.edit_workout_day_exercises_item:
                                intent = AddExerciseToDayActivity.newIntent(itemView.getContext(),
                                        workoutDayRef);
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

        adapter.notifyDataSetChanged();
    }

    private class ExerciseViewHolder extends RecyclerView.ViewHolder {

        private TextView mExercisePoints;
        private TextView mExerciseName;
        private TextView mExerciseTarget;
        private TextView mPreviousDate;

        public ExerciseViewHolder(View itemView) {
            super(itemView);

            mExercisePoints = itemView.findViewById(R.id.exercise_points);
            mExerciseName = itemView.findViewById(R.id.exercise_name);
            mExerciseTarget = itemView.findViewById(R.id.exercise_target);
            mPreviousDate = itemView.findViewById(R.id.previous_date);

        }

        public void bind(Exercise exercise) {
            mExerciseName.setText(exercise.getName());

            StringBuffer targetText = new StringBuffer();

            // Should be getLastExerciseMap or something
//            List<ExerciseMap> targets = exercise.getExerciseMaps();
//            for (ExerciseMap t: targets) {
//                targetText.append(t.getValue() + " ");
//            }

            targetText.append("0 - 0 - 0");
            mExerciseTarget.setText(targetText.toString());
            mExercisePoints.setText("" + exercise.getPoints());

            String formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
            mPreviousDate.setText(formattedDate);
        }
    }

    private class ExerciseAdapter extends RecyclerView.Adapter<ExerciseViewHolder> {

        private List<Exercise> mExerciseModel;

        // This constructor stays because we need to get exercise from that particular day
        // Using nothing now but it needs to change once we add exercises to the WorkoutDay Firebase
        // schema
        public ExerciseAdapter(WorkoutDay day) {
            mExerciseModel = day.getExercises();
        }

        @Override
        public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View exerciseView = mLayoutInflater.inflate(R.layout.workout_detail_exercise_item, parent,
                    false);

            return new ExerciseViewHolder(exerciseView);
        }

        @Override
        public void onBindViewHolder(ExerciseViewHolder holder, int position) {
            holder.bind(mExerciseModel.get(position));
        }

        @Override
        public int getItemCount() {
           return mExerciseModel.size();
        }
    }


}
