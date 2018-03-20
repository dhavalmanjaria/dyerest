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
import com.dhavalanjaria.dyerest.models.Exercise;
import com.dhavalanjaria.dyerest.models.WorkoutDay;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        long currentUnixTime = System.currentTimeMillis();

        // Temporary value so that a reference exists and we don't have to deal with "if (null)" type
        // statements. This makes sense because launching a workout should create a node anyway.
        getRootDataReference().child("daysPerformed")
                .child(mDayReference.getKey())
                .child("" + currentUnixTime)
                .setValue(0);

        final DatabaseReference exerciseTimestampRef = getRootDataReference().child("daysPerformed")
                .child(mDayReference.getKey())
                .child("" + currentUnixTime);


        //TODO: Once this is done, the ViewHolder or the Adapter should create nodes for each
        // exercise that is to be performed. The workout data (the poundage, reps, etc.) for the
        // exercise should be filled in when the user saves.

        // Create a List<> of exercises to be performed.
        final List<PagerAdapterModel> fragmentModelList = new ArrayList<>();
        mDayReference.child("exercises").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mStatePagerAdapter = new ExercisePagerAdapter(getSupportFragmentManager(), fragmentModelList);
                for (DataSnapshot exerciseSnap: dataSnapshot.getChildren()) {

                    Log.d(TAG, exerciseSnap.getRef().getKey());
                    final DatabaseReference exerciseRef = getRootDataReference().child("exercises")
                            .child(exerciseSnap.getRef().getKey());

                    final Integer[] sequenceNo = {1}; // sequence of the exercise to perform

                    exerciseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(final DataSnapshot dataSnapshot) {
                            Log.d(TAG, dataSnapshot.getKey());
                            Exercise ex = dataSnapshot.getValue(Exercise.class);
                            int sets = ex.getMaxSets();


                            // This loop is executed asynchronously, i.e. the fragmentModelList
                            // is populated at an indeterminate time.
                            // Due to this, passing the list to the PagerAdapter once is not enough.
                            // The Adapter also has to be notified when the data is changed. Since
                            // data may be added at any time.
                            for (int setNo = 1; setNo <= sets; setNo++) {
                                Log.d(TAG, "Ex. key: " + dataSnapshot.getKey());

                                PagerAdapterModel data = new PagerAdapterModel();

                                data.setExerciseUrl(dataSnapshot.getRef().toString());
                                data.setSetNo(setNo);
                                data.setSequenceNo(sequenceNo[0]);

                                // Also create a node in the database that will be filled in later by
                                // the user
                                DatabaseReference newExerciseToPerform =  createDataForExerciseTimestamp(
                                        data, exerciseTimestampRef);
                                data.setExerciseToPerformUrl(newExerciseToPerform.toString());

                                fragmentModelList.add(data) ;
                                mStatePagerAdapter.notifyDataSetChanged();

                                sequenceNo[0]++;
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
     * This class is used as a Map to store the exerciseUrl, sequenceNo, and setNo when passing to
     * the ActiveExerciseFragment. We need those three properties for each exercise and store them in
     * a List/Array type structure. Rather than store them in a Map<>, I'm putting them here.
     */
    private class PagerAdapterModel {
        private String exerciseUrl;
        private int setNo;
        private int sequenceNo;
        private String exerciseToPerformUrl;

        public PagerAdapterModel() {
        }

        public PagerAdapterModel(String exerciseUrl, int setNo, int sequenceNo, String exerciseToPerformUrl) {
            this.exerciseUrl = exerciseUrl;
            this.setNo = setNo;
            this.sequenceNo = sequenceNo;
            this.exerciseToPerformUrl = exerciseToPerformUrl;
        }

        public String getExerciseUrl() {
            return exerciseUrl;
        }

        public void setExerciseUrl(String exerciseUrl) {
            this.exerciseUrl = exerciseUrl;
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

        public String getExerciseToPerformUrl() {
            return exerciseToPerformUrl;
        }

        public void setExerciseToPerformUrl(String exerciseToPerformUrl) {
            this.exerciseToPerformUrl = exerciseToPerformUrl;
        }

        @Override
        public String toString() {
            return "{ExerciseKey=" + getExerciseUrl() + ",SequenceNo=" + getSequenceNo() + ",SetNo=" + getSetNo() +"}";
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
            // Exercise Url passed to Fragment so that we don't have to retrieve it again.
            return ActiveExerciseFragment.newInstance(currentModel.getExerciseToPerformUrl(),
                    currentModel.getExerciseUrl(),
                    currentModel.getSetNo());
        }

        @Override
        public int getCount() {
            return mModel.size();
        }
    }

    /**
     * Creates exercises for an exercise date (timestamp) in the "daysPerformed" node.
     * @param exerciseData Model containing the default data for an exercise to be performed
     * @param dateRef DatabaseReference to the timestamp that this data should be created for
     * @return DatabaseReference to the newly created exercise (that is to be performed).
     */
    private DatabaseReference createDataForExerciseTimestamp(PagerAdapterModel exerciseData, DatabaseReference dateRef) {

        DatabaseReference exRef = FirebaseDatabase.getInstance()
                .getReferenceFromUrl(exerciseData.getExerciseUrl());

        DatabaseReference exercisePerformedRef = dateRef.push();

        Map<String, Object> exerciseMap = new HashMap<>();
        exerciseMap.put("exerciseKey", exRef.getKey());
        exerciseMap.put("sequenceNo", exerciseData.getSequenceNo());
        exerciseMap.put("setNo", exerciseData.getSetNo());
        exerciseMap.put("points", 0);

        exercisePerformedRef.updateChildren(exerciseMap);

        return exercisePerformedRef;
    }

    @Override
    public Query getQuery() {
        return null;
    }
}
