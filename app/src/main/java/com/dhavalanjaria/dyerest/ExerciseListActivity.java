package com.dhavalanjaria.dyerest;

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
import com.dhavalanjaria.dyerest.viewholders.AddExerciseToDayViewHolder;
import com.dhavalanjaria.dyerest.viewholders.ExerciseListViewHolder;
import com.dhavalanjaria.dyerest.viewholders.ListAllExercisesViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

//TODO: Perhaps rename this to AddExerciseActivity (since you're adding these exercises to the workout)
public abstract class ExerciseListActivity extends BaseActivity {

    public static final String EXTRA_WORKOUT_DAY = "ExerciseListActivity.WorkoutDay";
    protected static final String EXTRA_LIST_TYPE = "ExerciseListActivity.ListType";

    public static enum LIST_TYPE {
        ADD_TO_DAY,
        ALL_EXERCISE_LIST,
        NONE
    };

    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private DatabaseReference mReference;
    private FloatingActionButton mAddExerciseButton;
    protected LIST_TYPE mListType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_exercises);

        mListType = (LIST_TYPE) getIntent().getSerializableExtra(EXTRA_LIST_TYPE);
        // Get workout day from id passed to intent
        String workoutDayId = (String) getIntent().getSerializableExtra(EXTRA_WORKOUT_DAY);
        mReference = FirebaseDatabase.getInstance().getReferenceFromUrl(workoutDayId);

        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            private final Fragment[] mFragments = new Fragment[] {
                    // Add the WorkoutDay to each fragment.
                    EditDayLiftingExercisesFragment.newInstance(mListType, mReference.toString()),
                    EditDayCardioExercisesFragment.newInstance(mListType, mReference.toString())
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

        mAddExerciseButton = findViewById(R.id.add_exercise_button);
        mAddExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = BaseActivity.getRootDataReference();
                reference = reference.child("exercises").push().getRef();
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


//    public ExerciseListViewHolder createExerciseListViewHolder(LIST_TYPE type, View v) {
//        switch (type) {
//            case ADD_TO_DAY:
//                return new AddExerciseToDayViewHolder(v);
//            case ALL_EXERCISE_LIST:
//                return new ListAllExercisesViewHolder(v);
//            case NONE:
//                return null;
//        }
//        return null;
//        // Note: an ENUM is used here because it is easily serializable and can be passed to the
//        // Lifting / Cardio exercise fragments so that they can get the ViewHolder they require.
//    }

    @Override
    public Query getQuery() {
        return null;
    }
}
