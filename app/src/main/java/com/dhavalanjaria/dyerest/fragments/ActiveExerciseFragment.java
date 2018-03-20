package com.dhavalanjaria.dyerest.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.models.ActiveExerciseField;
import com.dhavalanjaria.dyerest.models.Exercise;
import com.dhavalanjaria.dyerest.viewholders.ExerciseDetailViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dhaval Anjaria on 2/15/2018.
 */
//TODO: Review this class for extrenous members
public class ActiveExerciseFragment extends Fragment {

    public static final String TAG = "AWExerciseFragment";
    private static final String KEY_EXERCISE_URL = TAG + ".ExerciseUrl";
    private static final String KEY_SET_NO = TAG + ".SetNo";
    private static final String KEY_PREVIOUS_DATE = TAG + ".PreviousDate";
    private static final String KEY_DAY_URL = TAG + ".DayUrl";
    private static final String KEY_EXERCISE_PERFORMED_URL = TAG + ".ExercisePerformedUrl";
    protected static Exercise mExercise;
    private RecyclerView mExerciseRecycler;
    private TextView mExerciseNameText;
    private FloatingActionButton mSaveButton;

    private DatabaseReference mExerciseRef;
    private DatabaseReference mDayPerformedReference;
    private DatabaseReference mExerciseTimestampReference;

    private List<Integer> mNewValuesList; // Used for points calculation

    // The exercise position should really be retrieved from the actual model
    public static ActiveExerciseFragment newInstance(String exerciseRefUrl, int setNo, String dayRefUrl) {

        Bundle args = new Bundle();
        ActiveExerciseFragment fragment = new ActiveExerciseFragment();
        args.putString(KEY_EXERCISE_URL, exerciseRefUrl);
        args.putInt(KEY_SET_NO, setNo);
        args.putString(KEY_DAY_URL, dayRefUrl);
        fragment.setArguments(args);
        return fragment;
    }

    public static ActiveExerciseFragment newInstance(String exerciseToPerformUrl, String exerciseUrl) {
        Bundle args = new Bundle();
        ActiveExerciseFragment fragment = new ActiveExerciseFragment();
        args.putString(KEY_EXERCISE_PERFORMED_URL, exerciseToPerformUrl);
        args.putString(KEY_EXERCISE_URL, exerciseUrl);
        /**
         * This is a new instance method that will deliver the newly formed ExercisePerformed key
         * to the Fields ViewHolder which will then add the new values accordingly.
         */
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_active_exercise, container, false);
        final int setNo = (int)getArguments().getInt(KEY_SET_NO);

        String exercisePerformedUrl = (String) getArguments().get(KEY_EXERCISE_PERFORMED_URL);
        mExerciseTimestampReference = FirebaseDatabase.getInstance().getReferenceFromUrl(exercisePerformedUrl);

        String exerciseUrl = (String) getArguments().get(KEY_EXERCISE_URL);
        mExerciseRef = FirebaseDatabase.getInstance().getReferenceFromUrl(exerciseUrl);

        mDayPerformedReference = mExerciseTimestampReference.getParent();

        if (mExerciseRef == null || mDayPerformedReference == null)
            return null;

        mExerciseNameText = v.findViewById(R.id.exercise_name_card_text);

        mExerciseRecycler = (RecyclerView) v.findViewById(R.id.active_exercise_detail_recycler);

        // This is more code to deal with the Asynchrocity of Firebase
        final List<ActiveExerciseField> adapterModel = new ArrayList<>();
        final ActiveExerciseFieldAdapter adapter = new ActiveExerciseFieldAdapter(adapterModel);

        mExerciseRecycler.setAdapter(adapter);
        mExerciseRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        mExercise = new Exercise();
        mExerciseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Exercise ex = dataSnapshot.getValue(Exercise.class);
                mExercise.setName(ex.getName());
                mExercise.setMaxSets(ex.getMaxSets());
                mExercise.setExerciseType(ex.getExerciseType());
                mExercise.setExerciseFields(ex.getExerciseFields());

                mExerciseNameText.setText(mExercise.getName());
                // Other Views also go here.

                adapterModel.addAll(generateFieldValues(mExercise, setNo));
                adapter.notifyDataSetChanged();

                createExerciseFields(mExerciseTimestampReference, mExercise);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
                Log.d(TAG, databaseError.getDetails());
            }
        });

        mSaveButton = (FloatingActionButton) v.findViewById(R.id.save_exercise_data_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save whatever..
            }
        });

        return v;
    }

    private List<ActiveExerciseField> generateFieldValues(Exercise exercise, int setNo) {
        List<ActiveExerciseField> retval = new ArrayList<>();

        // First add the set number
        retval.add(new ActiveExerciseField("Set", setNo));

        for (String key: exercise.getExerciseFields().keySet()) {
            ActiveExerciseField field = new ActiveExerciseField();
            field.setFieldName(key);

            // Initially it is set to 0.
            field.setValue(0);
            retval.add(field);
        }
        return retval;
    }

    private class ActiveExerciseFieldAdapter extends RecyclerView.Adapter<ExerciseDetailViewHolder> {

        private List<ActiveExerciseField> mExerciseFieldsList;

        public ActiveExerciseFieldAdapter(List<ActiveExerciseField> activeExerciseFields) {
            this.mExerciseFieldsList = activeExerciseFields;
        }

        @Override
        public ExerciseDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View itemView = inflater.inflate(R.layout.active_exercise_detail_item, parent, false);

            return new ExerciseDetailViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final ExerciseDetailViewHolder holder, int position) {
            final ActiveExerciseField activeExerciseField = mExerciseFieldsList.get(position);

            // First we need to get the targets, i.e. the values for when the last time the exercise
            // was performed.
            mDayPerformedReference.orderByKey().limitToLast(2)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            DataSnapshot lastDateSnapshot = dataSnapshot.getChildren().iterator().next();
                            Log.d(TAG, dataSnapshot.getKey());
                            // It is important to note that this method will be a deferred action
                            // meaning that it won't execute immediately, which is fine.
                            holder.bind(activeExerciseField.getFieldName(), activeExerciseField.getValue(),
                                    lastDateSnapshot.getRef());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }

        @Override
        public int getItemCount() {
            return mExerciseFieldsList.size();
        }
    }

    private void createExerciseFields(DatabaseReference exercisePerformedReference, Exercise exercise) {
        Map<String, Object> exerciseFields = new HashMap<>();

        for (String key: exercise.getExerciseFields().keySet()) {
            // Initially 0
            exerciseFields.put(key, 0);
        }

        exercisePerformedReference.child("values")
                .updateChildren(exerciseFields);
    }
}
