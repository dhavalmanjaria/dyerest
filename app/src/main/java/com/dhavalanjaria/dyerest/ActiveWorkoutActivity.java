package com.dhavalanjaria.dyerest;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

import com.dhavalanjaria.dyerest.fragments.ActiveExerciseFragment;
import com.dhavalanjaria.dyerest.models.DayPerformed;
import com.dhavalanjaria.dyerest.models.Exercise;
import com.dhavalanjaria.dyerest.models.ExercisePerformed;
import com.dhavalanjaria.dyerest.models.WorkoutDay;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActiveWorkoutActivity extends BaseActivity {

    public static final String EXTRA_WORKOUT_DAY_REF_URL = "ActiveWorkoutActivity.WorkoutDay";
    private static final String TAG = "ActiveWorkoutActivity";

    private WorkoutDay mWorkoutDayModel;
    private ViewPager mActiveExerciseViewPager;
    private FragmentStatePagerAdapter mStatePagerAdapter;
    private DatabaseReference mDayReference;

    public static Intent newIntent(Context context, String workoutDayRefUrl) {
        Intent intent = new Intent(context, ActiveWorkoutActivity.class);
        intent.putExtra(EXTRA_WORKOUT_DAY_REF_URL, workoutDayRefUrl);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_workout);

        // This should be replaced by a getId() type of thing
        String workoutDayUrl = (String) getIntent().getSerializableExtra(EXTRA_WORKOUT_DAY_REF_URL);
        mDayReference = FirebaseDatabase.getInstance().getReferenceFromUrl(workoutDayUrl);

        // Create a new Active Workout
        DatabaseReference daysPerformedRef = getRootDataReference().child("daysPerformed").push();
        DayPerformed dayPerformed = new DayPerformed(new Date(), mDayReference.getKey(), 0);
        //daysPerformedRef.updateChildren(dayPerformed.toMap());

        // Create a List<> of exercises to be performed.
        final List<PagerAdapterModel> fragmentModelList = new ArrayList<>();
        mDayReference.child("exercises").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mStatePagerAdapter = new ExercisePagerAdapter(getSupportFragmentManager(), fragmentModelList);
                for (DataSnapshot exerciseSnap: dataSnapshot.getChildren()) {
                    Log.d(TAG, exerciseSnap.getRef().getKey());

                    DatabaseReference exerciseRef = getRootDataReference().child("exercises")
                            .child(exerciseSnap.getRef().getKey());
                    exerciseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(final DataSnapshot dataSnapshot) {
                            Log.d(TAG, dataSnapshot.getKey());
                            Exercise ex = dataSnapshot.getValue(Exercise.class);
                            int sets = ex.getMaxSets();

                            int seqNo = 1; // sequence of the exercise to perform
                            for (int i = 1; i <= sets; i++) {
                                Log.d(TAG, "Ex. key: " + dataSnapshot.getKey());
                                ExercisePerformed performed = new ExercisePerformed();
                                performed.setSetNo(i);
                                performed.setSequenceNo(seqNo++);
                                fragmentModelList.add(new PagerAdapterModel(
                                        dataSnapshot.getRef().toString(),
                                        i,
                                        seqNo));
                                mStatePagerAdapter.notifyDataSetChanged();
                                // This loop is executed asynchronously, i.e. the fragmentModelList
                                // is populated at an indeterminate time.
                                // Due to this, passing the list to the PagerAdapter once is not enough.
                                // The Adapter also has to be notified when the data is changed. Since
                                // data may be added at any time.

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d(TAG, databaseError.getMessage());
                            Log.d(TAG, databaseError.getDetails());
                        }
                    });
                }
                mActiveExerciseViewPager = findViewById(R.id.active_exercise_pager);
                mActiveExerciseViewPager.setAdapter(mStatePagerAdapter);
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
                Log.d(TAG, databaseError.getDetails());
            }
        });
    }

    /**
     * This class is used as a Map to store the exerciseKey, sequenceNo, and setNo when passing to
     * the ActiveExerciseFragment. We need those three properties for each exercise and store them in
     * a List/Array type structure. Rather than store them in a Map<>, I'm putting them here.
     */
    private class PagerAdapterModel {
        private String exerciseKey;
        private int setNo;
        private int sequenceNo;

        public PagerAdapterModel() {
        }

        public PagerAdapterModel(String exerciseKey, int setNo, int sequenceNo) {
            this.exerciseKey = exerciseKey;
            this.setNo = setNo;
            this.sequenceNo = sequenceNo;
        }

        public String getExerciseKey() {
            return exerciseKey;
        }

        public void setExerciseKey(String exerciseKey) {
            this.exerciseKey = exerciseKey;
        }

        public int getSetNo() {
            return setNo;
        }

        public void setSetNo(int setNo) {
            this.setNo = setNo;
        }

        public int getSequenceNo() {
            return sequenceNo;
        }

        public void setSequenceNo(int sequenceNo) {
            this.sequenceNo = sequenceNo;
        }

        @Override
        public String toString() {
            return "{ExerciseKey=" + getExerciseKey() + ",SequenceNo=" + getSequenceNo() + ",SetNo=" + getSetNo() +"}";
        }
    }

    private class ExercisePagerAdapter extends FragmentStatePagerAdapter {

        private List<PagerAdapterModel> mModel;

        public ExercisePagerAdapter(FragmentManager fm, List<PagerAdapterModel> model) {
            super(fm);
            this.mModel = model;
        }

        @Override
        public Fragment getItem(int position) {
            PagerAdapterModel currentModel = mModel.get(position);
            // TODO: Sequence No.
            // TODO: Date
            Log.d(TAG, "CurrentModel:"+currentModel.toString());
            return ActiveExerciseFragment.newInstance(currentModel.exerciseKey, currentModel.getSetNo(), null);
        }

        @Override
        public int getCount() {
            return mModel.size();
        }
    }

    @Override
    public Query getQuery() {
        return null;
    }
}
