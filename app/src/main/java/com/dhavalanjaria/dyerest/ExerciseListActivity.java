package com.dhavalanjaria.dyerest;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dhavalanjaria.dyerest.models.Exercise;
import com.dhavalanjaria.dyerest.models.MockData;
import com.dhavalanjaria.dyerest.models.WorkoutDay;

public class ExerciseListActivity extends AppCompatActivity {

    public static final String EXTRA_WORKOUT_DAY = "ExerciseListActivity.WorkoutDay";

    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    public static Intent newIntent(Context context, int workoutDayId) {
        Intent intent = new Intent(context, ExerciseListActivity.class);
        intent.putExtra(EXTRA_WORKOUT_DAY, workoutDayId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        // Get workout day from id passed to intent
        int workoutDayId = (int)getIntent().getSerializableExtra(EXTRA_WORKOUT_DAY);
        WorkoutDay workoutDay = MockData.getWorkoutDays().get(workoutDayId);

        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            private final Fragment[] mFragments = new Fragment[] {
                    // Add the WorkoutDay to each fragment.
                    new LiftingExerciseListFragment(),
                    new CardioExerciseListFragment()
            };

            private final String[] tabHeadings = new String[] {
                    getString(R.string.heading_lifting_exercise),
                    getString(R.string.heading_cardio_exercise)
            };

            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

            @Override
            public int getCount() {
                return mFragments.length;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return tabHeadings[position];
            }
        };

        mViewPager = findViewById(R.id.exercise_page);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.exercise_tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }
}