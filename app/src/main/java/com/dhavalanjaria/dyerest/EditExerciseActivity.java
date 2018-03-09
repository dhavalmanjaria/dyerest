package com.dhavalanjaria.dyerest;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.dhavalanjaria.dyerest.fragments.ExerciseCommentsActivity;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EditExerciseActivity extends BaseActivity {

    private static final String TAG = "EditExerciseActivity";
    private static final String EXTRA_EXERCISE_ID_URL = "EditExerciseActivity.ExerciseIdUrl";
    private EditText mNameEdit;
    private RecyclerView mExerciseFieldRecycler;
    private EditText mAddFieldEdit;
    private ImageButton mAddExerciseFieldButton;
    private RadioButton mCardioRadio;
    private RadioButton mLiftingRadio;
    private Button mGuideButton;
    private Button mViewCommentButton;
    private FloatingActionButton mSaveButton;
    private ExerciseFieldAdapter mExerciseFieldAdapter;
    private List<ExerciseField> mExerciseFieldList; // Once the save button is clicked, this will be
    // saved as well.

    private ExerciseFieldAdapter adapter;
    private DatabaseReference mExerciseRef;

    private List<ExerciseField> mModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exercise);

        mNameEdit = findViewById(R.id.exercise_name_edit);
        mExerciseFieldRecycler = findViewById(R.id.added_fields_recycler);
        mExerciseFieldList = new ArrayList<>();

        mExerciseFieldAdapter = new ExerciseFieldAdapter(mExerciseFieldList);
        mExerciseFieldRecycler.setAdapter(mExerciseFieldAdapter);
        mExerciseFieldRecycler.setLayoutManager(new LinearLayoutManager(this));

        final String exerciseUrl = (String) getIntent().getSerializableExtra(EXTRA_EXERCISE_ID_URL);


        mGuideButton = findViewById(R.id.edit_exercise_guide_button);
        mGuideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditExerciseActivity.this, ExerciseGuideActivity.class));
            }
        });

        mViewCommentButton = findViewById(R.id.exercise_comments_button);
        mViewCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent should carry exercise ID
                startActivity(new Intent(EditExerciseActivity.this, ExerciseCommentsActivity.class));
            }
        });

        mCardioRadio = findViewById(R.id.cardio_radio);
        mLiftingRadio = findViewById(R.id.lifting_radio);
        mCardioRadio.setOnCheckedChangeListener(new CheckChangedListener());
        mLiftingRadio.setOnCheckedChangeListener(new CheckChangedListener());

        mSaveButton = findViewById(R.id.save_exercise_button);

        mSaveButton.setOnClickListener(new SaveClickListener());

        mAddFieldEdit = findViewById(R.id.add_field_edit);
        mAddExerciseFieldButton = findViewById(R.id.add_exercise_field_button);
        mAddExerciseFieldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newField = mAddFieldEdit.getText().toString();
                mExerciseFieldList.add(new ExerciseField(newField, true));
                mExerciseFieldAdapter.notifyDataSetChanged();
            }
        });

        // This is for when we're adding a new exercise
        if (exerciseUrl != null) {
            mExerciseRef = FirebaseDatabase.getInstance().getReferenceFromUrl(exerciseUrl);
            setFieldsFromReference(mExerciseRef);
        }
        else
            mExerciseRef = null;

    }

    public static Intent newIntent(Context context, String exerciseIdUrl) {
        Intent intent = new Intent(context, EditExerciseActivity.class);
        intent.putExtra(EXTRA_EXERCISE_ID_URL, exerciseIdUrl);
        return intent;
    }

    // If adding a new exercise altogether.
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, EditExerciseActivity.class);
        return intent;
    }

    private class ExerciseFieldAdapter extends RecyclerView.Adapter<ExerciseFieldViewHolder> {

        private List<ExerciseField> mExerciseFields;

        public ExerciseFieldAdapter(List<ExerciseField> exerciseFields) {
            this.mExerciseFields = exerciseFields;
        }

        @Override
        public ExerciseFieldViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = getLayoutInflater().inflate(R.layout.exercise_fields_item, parent, false);
            return new ExerciseFieldViewHolder(v, mExerciseFieldList, this);
        }

        @Override
        public void onBindViewHolder(ExerciseFieldViewHolder holder, int position) {
            if (mExerciseFields.get(position).getTrue()) {
                holder.bind(mExerciseFields.get(position));
            }
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

    private class CheckChangedListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        }
    }

    private class SaveClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            String nameText = mNameEdit.getText().toString();
            String exerciseType = "";

            if (nameText.trim().length() <= 0) {
                Toast.makeText(EditExerciseActivity.this, "The name cannot be empty", Toast.LENGTH_SHORT)
                        .show();
                return;
            }

            // This should probably use the ToDeleteExercise Types ENUM later
            if (mCardioRadio.isChecked()) {
                exerciseType = "CARDIO";
            } else if (mLiftingRadio.isChecked()) {
                exerciseType = "LIFTING";
            } else if (mCardioRadio.isChecked() == false && mLiftingRadio.isChecked() == false) {
                Toast.makeText(EditExerciseActivity.this, "Please select exercise type", Toast.LENGTH_SHORT)
                        .show();
                return;
            }

            // A proper model class isn't created yet, so we create our own map.
            Exercise exercise = new Exercise();
            exercise.setName(mNameEdit.getText().toString());
            exercise.setExerciseType(exerciseType);
            exercise.setTotalPoints(0);

            Map<String, Object> fieldMap = new HashMap<>();

            for (ExerciseField field: mExerciseFieldList) {
                fieldMap.put(field.getName(), field.getTrue());
            }

            exercise.setExerciseFields(fieldMap); // For Now
            Map<String, Object> exerciseDetails = exercise.toMap();

            // Create a new exercise if it doesn't exist.
            if (mExerciseRef == null) {
                String newExercise = BaseActivity.getRootDataReference().child("exercises").push().getKey();
                mExerciseRef = BaseActivity.getRootDataReference().child("exercises").child(newExercise);
            }
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
                            mExerciseFieldList.add(new ExerciseField(fieldIter.next(), true));
                        }
                    }

                    if (exercise.getExerciseType().equalsIgnoreCase("lifting")) {
                        mLiftingRadio.setChecked(true);
                    } else if (exercise.getExerciseType().equalsIgnoreCase("cardio")) {
                        mCardioRadio.setChecked(true);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, databaseError.getMessage());
                    Log.e(TAG, databaseError.getDetails());
                }
            });
        }
    }


