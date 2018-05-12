package com.dhavalanjaria.dyerest;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.dhavalanjaria.dyerest.fragments.EditDialogFragment;
import com.dhavalanjaria.dyerest.models.Exercise;
import com.dhavalanjaria.dyerest.models.ExerciseField;
import com.dhavalanjaria.dyerest.viewholders.ExerciseFieldViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EditExerciseActivity extends BaseActivity {

    private static final String TAG = "EditExerciseActivity";
    private static final String EXTRA_EXERCISE_ID_URL = "EditExerciseActivity.ExerciseIdUrl";
    private EditText mNameEdit;
    private RecyclerView mExerciseFieldRecycler;
    private EditText mAddFieldEdit;
    private Button mAddExerciseFieldButton;
    private RadioButton mCardioRadio;
    private EditText mMaxSetsEdit;
    private RadioButton mLiftingRadio;
    private Button mGuideButton;
    private Button mViewCommentButton;
    private FloatingActionButton mSaveButton;
    private ExerciseFieldAdapter mExerciseFieldAdapter;
    private List<String> mExerciseFieldList; // Once the save button is clicked, this will be
    // saved as well.

    private ExerciseFieldAdapter adapter;
    private DatabaseReference mExerciseRef;

    private List<ExerciseField> mModel;
    private String mExerciseType;


    public static Intent newIntent(Context context, String exerciseIdUrl) {
        Intent intent = new Intent(context, EditExerciseActivity.class);
        intent.putExtra(EXTRA_EXERCISE_ID_URL, exerciseIdUrl);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exercise);

        // Before we add a new exercise, launch a Dialog box to set the name so we have a reference
        // that is not null.
        mNameEdit = findViewById(R.id.exercise_name_edit);
        mMaxSetsEdit = findViewById(R.id.max_sets_edit);

        mExerciseFieldRecycler = findViewById(R.id.added_fields_recycler);
        mExerciseFieldList = new ArrayList<>();

        String exerciseUrl = (String) getIntent().getSerializableExtra(EXTRA_EXERCISE_ID_URL);
        mExerciseRef = FirebaseDatabase.getInstance().getReferenceFromUrl(exerciseUrl);

        mExerciseFieldAdapter = new ExerciseFieldAdapter(mExerciseFieldList);
        mExerciseFieldRecycler.setAdapter(mExerciseFieldAdapter);
        mExerciseFieldRecycler.setLayoutManager(new LinearLayoutManager(this));

        mGuideButton = findViewById(R.id.edit_exercise_guide_button);
        mGuideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ExerciseGuideActivity.newIntent(EditExerciseActivity.this,
                        mExerciseRef.toString()));
            }
        });

        mViewCommentButton = findViewById(R.id.exercise_comments_button);
        mViewCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ExerciseCommentsActivity.newIntent(EditExerciseActivity.this, mExerciseRef.toString());
                startActivity(intent);
            }
        });

        mCardioRadio = findViewById(R.id.cardio_radio);
        mLiftingRadio = findViewById(R.id.lifting_radio);

        mLiftingRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mMaxSetsEdit.setText("" + 1);
                    mMaxSetsEdit.setEnabled(true);
                    mExerciseType = "LIFTING";
                }
            }
        });
        mCardioRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mMaxSetsEdit.setEnabled(false);
                    mMaxSetsEdit.setText("1");
                    mExerciseType = "CARDIO";
                }
            }
        });

        mSaveButton = findViewById(R.id.save_exercise_button);

        mSaveButton.setOnClickListener(new SaveClickListener());

        mAddExerciseFieldButton = findViewById(R.id.add_exercise_field_button);
        mAddExerciseFieldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                EditDialogFragment fragment = EditDialogFragment.newInstance("Add Field",
                        new OnDialogCompletedListener() {
                            @Override
                            public void onDialogComplete(String text) {
                                mExerciseFieldList.add(text);
                                mExerciseFieldAdapter.notifyDataSetChanged();
                            }
                        });
                fragment.show(manager, TAG);
            }
        });

        mExerciseRef = FirebaseDatabase.getInstance().getReferenceFromUrl(exerciseUrl);
        setFieldsFromReference(mExerciseRef);

    }

    // This should perhaps go in its own package. However having adapters within Activites
    // has its own advantages.
    public class ExerciseFieldAdapter extends RecyclerView.Adapter<ExerciseFieldViewHolder> {

        private List<String> mExerciseFields;

        public ExerciseFieldAdapter(List<String> exerciseFields) {
            this.mExerciseFields = exerciseFields;
        }

        @Override
        public ExerciseFieldViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = getLayoutInflater().inflate(R.layout.exercise_fields_item, parent, false);
            return new ExerciseFieldViewHolder(v, mExerciseFieldList, this);
        }

        @Override
        public void onBindViewHolder(ExerciseFieldViewHolder holder, int position) {
            holder.bind(mExerciseFields.get(position));
        }

        @Override
        public int getItemCount() {
            return mExerciseFields.size();
        }
    }
    /**
     * Returns query for exercise fields
     * @return Query
     */
    @Override
    public Query getQuery() {
        return mExerciseRef.orderByChild("exerciseFields");
    }

    private class SaveClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            final Exercise exercise = new Exercise();

                mExerciseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Exercise ex = dataSnapshot.getValue(Exercise.class);
                        exercise.setTotalPoints(ex.getTotalPoints());
                        exercise.setExerciseType(ex.getExerciseType());
                        // So that we don't change the total points.
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(TAG, databaseError.getMessage());
                        Log.d(TAG, databaseError.getDetails());
                    }
                });

            String nameText = mNameEdit.getText().toString();

            if (nameText.trim().length() <= 0) {
                Toast.makeText(EditExerciseActivity.this, "The name cannot be empty", Toast.LENGTH_SHORT)
                        .show();
                return;
            } else {
                exercise.setName(nameText);
            }

            // Default
            int maxSets = 1;
            try {
                maxSets = Integer.parseInt(mMaxSetsEdit.getText().toString());
                exercise.setMaxSets(maxSets);
            }
            catch (NumberFormatException ex) {
                Toast.makeText(EditExerciseActivity.this, "Invalid characters in 'Max Sets' field",
                        Toast.LENGTH_SHORT)
                        .show();
            }

            Map<String, Object> fieldMap = new HashMap<>();

            for (String field: mExerciseFieldList) {
                fieldMap.put(field, true);
            }

            if (mCardioRadio.isChecked() == false && mLiftingRadio.isChecked() == false) {
                Toast.makeText(EditExerciseActivity.this, "Please select exercise type", Toast.LENGTH_SHORT)
                        .show();
                return;
            }

            exercise.setExerciseType(mExerciseType);
            exercise.setExerciseFields(fieldMap);
            Map<String, Object> exerciseDetails = exercise.toMap();

            mExerciseRef.updateChildren(exerciseDetails);

            Toast.makeText(EditExerciseActivity.this, "Exercise Added", Toast.LENGTH_SHORT).show();
        }
    }

    private void setFieldsFromReference(DatabaseReference exerciseRef) {
        if (exerciseRef == null)
            return;;

        exerciseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Exercise exercise = dataSnapshot.getValue(Exercise.class);

                mNameEdit.setText(exercise.getName());

                // This if really means that it should go in an AddExercise activity
                if (exercise.getExerciseFields() != null) {
                    Iterator<String> fieldIter = exercise.getExerciseFields().keySet().iterator();
                    while (fieldIter.hasNext()) {
                        mExerciseFieldList.add(fieldIter.next());
                    }
                }

                if (exercise.getExerciseType() != null){
                    if (exercise.getExerciseType().equalsIgnoreCase("lifting")) {
                        mLiftingRadio.setChecked(true);
                    } else if (exercise.getExerciseType().equalsIgnoreCase("cardio")) {
                        mCardioRadio.setChecked(true);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
                Log.e(TAG, databaseError.getDetails());
            }
        });
        }

        protected DatabaseReference getExerciseRef() {
            String exerciseUrl = (String) getIntent().getSerializableExtra(EXTRA_EXERCISE_ID_URL);
            return FirebaseDatabase.getInstance().getReferenceFromUrl(exerciseUrl);
        }
    }


