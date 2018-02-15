package com.dhavalanjaria.dyerest.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dhavalanjaria.dyerest.ActiveWorkoutActivity;
import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.WorkoutDetailActivity;
import com.dhavalanjaria.dyerest.models.Exercise;
import com.dhavalanjaria.dyerest.models.MockData;
import com.dhavalanjaria.dyerest.models.Workout;
import com.dhavalanjaria.dyerest.models.WorkoutDay;

import java.util.Date;
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
    private LayoutInflater mLayoutInflater;

    public WorkoutDayViewHolder(final View itemView, LayoutInflater layoutInflater) {
        super(itemView);

        this.mLayoutInflater = layoutInflater;

        mDayNameTextView = itemView.findViewById(R.id.day_name);
        mExerciseListRecycler = itemView.findViewById(R.id.exercise_list_recycler);
        mLaunchButton = itemView.findViewById(R.id.launch_button);

        mExerciseListRecycler.setLayoutManager(new LinearLayoutManager(itemView.getContext(),
                LinearLayoutManager.HORIZONTAL, false));

    }

    public void bind(final WorkoutDay workoutDay) {
        mDayNameTextView.setText(workoutDay.getName());

        ExerciseAdapter adapter = new ExerciseAdapter(workoutDay);
        mExerciseListRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        mLaunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getAdapterPosition should technically work.
                // However, expect errors
                Intent intent = ActiveWorkoutActivity.newIntent(v.getContext(), getAdapterPosition());
                v.getContext().startActivity(intent);
            }
        });
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
            mExerciseTarget.setText("" + exercise.getPoundage());
            mExercisePoints.setText("" + exercise.getPoints());
            mPreviousDate.setText(new Date().toString());
        }
    }

    private class ExerciseAdapter extends RecyclerView.Adapter<ExerciseViewHolder> {

        private List<Exercise> mExerciseModel;

        // This constructor stays because we need to get exercise from that particular day
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
