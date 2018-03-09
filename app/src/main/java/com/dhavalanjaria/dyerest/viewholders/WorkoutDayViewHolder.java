package com.dhavalanjaria.dyerest.viewholders;

import android.support.annotation.NonNull;
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

    }

    public void bind(final WorkoutDay workoutDay, final DatabaseReference workoutDayRef) {
        mDayNameTextView.setText(workoutDay.getName());

        GetScreenshotExerciseAdapter adapter = new GetScreenshotExerciseAdapter(workoutDay);

        Query query = workoutDayRef.child("exercises").orderByChild("sequenceNumber");
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

        public void bind(DayExercise exercise) {
            DatabaseReference ref = BaseActivity.getRootDataReference().child("exercises")
                    .child(exercise.getExerciseKey());


            ref.child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = (String) dataSnapshot.getValue();
                    mExerciseName.setText(name);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            // This method will map the last performed exercise
            // For now. It will simply show the name.


//            StringBuffer targetText = new StringBuffer();
//
//            // Should be getLastExerciseMap or something
////            List<ExerciseMap> targets = exercise.getExerciseMaps();
////            for (ExerciseMap t: targets) {
////                targetText.append(t.getValue() + " ");
////            }
//
//            targetText.append("0 - 0 - 0");
//            mExerciseTarget.setText(targetText.toString());
//            mExercisePoints.setText("" + exercise.getPoints());
//
//            String formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
//            mPreviousDate.setText(formattedDate);
        }
    }

    private class ExercisePreviewAdapter extends FirebaseRecyclerAdapter<DayExercise, DayExercisePreviewViewHolder> {

        public ExercisePreviewAdapter(@NonNull FirebaseRecyclerOptions<DayExercise> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull DayExercisePreviewViewHolder holder, int position, @NonNull DayExercise model) {
            holder.bind(model);

        }

        @Override
        public DayExercisePreviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View exerciseView = mLayoutInflater.inflate(R.layout.workout_detail_exercise_item, parent,
                    false);

            return new DayExercisePreviewViewHolder(exerciseView);
        }
    }

    @Deprecated
    private class GetScreenshotExerciseAdapter extends RecyclerView.Adapter<DayExercisePreviewViewHolder> {

        private List<ToDeleteExercise> mToDeleteExerciseModel;

        // This constructor stays because we need to get exercise from that particular day
        // Using nothing now but it needs to change once we add exercises to the WorkoutDay Firebase
        // schema
        public GetScreenshotExerciseAdapter(WorkoutDay day) {

        }

        @Override
        public DayExercisePreviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View exerciseView = mLayoutInflater.inflate(R.layout.workout_detail_exercise_item, parent,
                    false);

            return new DayExercisePreviewViewHolder(exerciseView);
        }

        @Override
        public void onBindViewHolder(DayExercisePreviewViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
           return 0;
        }
    }


}
