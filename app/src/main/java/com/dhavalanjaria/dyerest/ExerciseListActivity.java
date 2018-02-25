package com.dhavalanjaria.dyerest;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.dhavalanjaria.dyerest.fragments.EditDayCardioExercisesFragment;
import com.dhavalanjaria.dyerest.fragments.EditDayLiftingExercisesFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

//TODO: Perhaps rename this to AddExerciseActivity (since you're adding these exercises to the workout)
public class ExerciseListActivity extends BaseActivity {

    public static final String EXTRA_WORKOUT_DAY = "ExerciseListActivity.WorkoutDay";
    private static final String EXTRA_ADDING_TO_DAY = "ExerciseListActivity.AddingToDay";

    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private DatabaseReference mReference;
    private FloatingActionButton mFloatingActionButton;
    private boolean mAddingToDay;

    public static Intent newIntent(Context context, DatabaseReference workoutDayRef, boolean addingToDay) {
        Intent intent = new Intent(context, ExerciseListActivity.class);
        //TODO: See Option A for Schema.
        intent.putExtra(EXTRA_WORKOUT_DAY, workoutDayRef.toString());
        intent.putExtra(EXTRA_ADDING_TO_DAY, addingToDay);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_exercises);

        mAddingToDay = (Boolean) getIntent().getSerializableExtra(EXTRA_ADDING_TO_DAY);
        // Get workout day from id passed to intent
        String workoutDayId = (String) getIntent().getSerializableExtra(EXTRA_WORKOUT_DAY);
        mReference = FirebaseDatabase.getInstance().getReferenceFromUrl(workoutDayId);

        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            private final Fragment[] mFragments = new Fragment[] {
                    // Add the WorkoutDay to each fragment.
                    EditDayLiftingExercisesFragment.newInstance(mAddingToDay),
                    EditDayCardioExercisesFragment.newInstance(mAddingToDay)
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

        mFloatingActionButton = findViewById(R.id.add_exercise_button);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EditExerciseActivity.newIntent(ExerciseListActivity.this);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // super.onCreateOptionsMenu(menu);
        MenuInflater inflater;
        inflater = getMenuInflater();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.edit_all_exercises_menu_item:
                startActivity(new Intent(this, EditExerciseActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Query getQuery() {
        return null;
    }


}
