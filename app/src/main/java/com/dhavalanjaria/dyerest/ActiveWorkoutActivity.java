package com.dhavalanjaria.dyerest;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dhavalanjaria.dyerest.BaseActivity;
import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.fragments.ActiveWorkoutExerciseFragment;
import com.dhavalanjaria.dyerest.models.Exercise;
import com.dhavalanjaria.dyerest.models.MockData;
import com.dhavalanjaria.dyerest.models.WorkoutDay;
import com.google.firebase.database.Query;

import java.util.LinkedList;

public class ActiveWorkoutActivity extends BaseActivity {

    public static final String EXTRA_WORKOUT_DAY = "ActiveWorkoutActivity.WorkoutDay";

    private WorkoutDay mWorkoutDayModel;
    private ViewPager mActiveExerciseViewPager;
    private FragmentStatePagerAdapter mStatePagerAdapter;

    public static Intent newIntent(Context context, int workoutDayId) {
        Intent intent = new Intent(context, ActiveWorkoutActivity.class);
        intent.putExtra(EXTRA_WORKOUT_DAY, workoutDayId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_workout);

        // This should be replaced by a getId() type of thing
        int workoutDayId = (int) getIntent().getSerializableExtra(EXTRA_WORKOUT_DAY);
        mWorkoutDayModel = MockData.getWorkoutDays().get(workoutDayId);

        mActiveExerciseViewPager = findViewById(R.id.active_exercise_pager);
        mStatePagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                // Important bit here. This is where the current exercise is determined
                // basically. It is then passed eventually ExerciseDetailViewHolder.

                // / change this
                Exercise currentExercise = MockData.getCardioExercises().get(position);
                return ActiveWorkoutExerciseFragment.newInstance(currentExercise);
            }

            @Override
            public int getCount() {
                return mWorkoutDayModel.getExercises().size();
            }
        };

        mActiveExerciseViewPager.setAdapter(mStatePagerAdapter);
    }

    @Override
    public Query getQuery() {
        return null;
    }
}
