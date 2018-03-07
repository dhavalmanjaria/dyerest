package com.dhavalanjaria.dyerest;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.dhavalanjaria.dyerest.fragments.ExerciseCommentsActivity;
import com.dhavalanjaria.dyerest.fragments.ExerciseGuideFragment;
import com.dhavalanjaria.dyerest.models.ExerciseField;
import com.dhavalanjaria.dyerest.models.ExerciseMap;
import com.dhavalanjaria.dyerest.viewholders.ExerciseFieldViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
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

    private ToDeleteExerciseFieldAdapter adapter;
    private DatabaseReference mReference;

    private List<ExerciseField> mModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exercise);

        mNameEdit = findViewById(R.id.exercise_name_edit);
        mExerciseFieldRecycler = findViewById(R.id.added_fields_recycler);
        adapter = new ToDeleteExerciseFieldAdapter();
        mExerciseFieldRecycler.setAdapter(adapter);
        mExerciseFieldRecycler.setLayoutManager(new LinearLayoutManager(this));

        String exerciseUrl = (String) getIntent().getSerializableExtra(EXTRA_EXERCISE_ID_URL);
        mReference = FirebaseDatabase.getInstance().getReferenceFromUrl(exerciseUrl);

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

        mSaveButton = findViewById(R.id.save_exercise_button);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameText = mNameEdit.getText().toString();
                String exerciseType = "";

                if (nameText.trim().length() <= 0) {
                    Toast.makeText(EditExerciseActivity.this, "The name cannot be empty", Toast.LENGTH_SHORT)
                        .show();
                    return;
                }

                // This should probably use the Exercise Types ENUM later
                if (mCardioRadio.isChecked()) {
                    exerciseType = "CARDIO";
                } else if (mLiftingRadio.isChecked()) {
                    exerciseType = "LIFTING";
                }

                // A proper model class isn't created yet, so we create our own map.
                Map<String, Object> exerciseDetails = new HashMap<>();
                exerciseDetails.put("name", nameText);
                exerciseDetails.put("fields", null); // Null for now. To be updated with proper exercise fields later
                exerciseDetails.put("totalPoints", 0);
                exerciseDetails.put("exerciseType", exerciseType);

                mReference.updateChildren(exerciseDetails);
            }
        });

    }

    public static Intent newIntent(Context context, String exerciseIdUrl) {
        Intent intent = new Intent(context, EditExerciseActivity.class);
        intent.putExtra(EXTRA_EXERCISE_ID_URL, exerciseIdUrl);
        return intent;
    }

    private class ToDeleteExerciseFieldAdapter extends RecyclerView.Adapter<ExerciseFieldViewHolder> {

        private List<ExerciseField> mExerciseFields;

        public ToDeleteExerciseFieldAdapter() {
            mExerciseFields = new LinkedList<>();

            mExerciseFields.add(new ExerciseField("Duration"));
            mExerciseFields.add(new ExerciseField("Distance"));
            mExerciseFields.add(new ExerciseField("Intensity"));
            mExerciseFields.add(new ExerciseField("Incline"));
        }

        @Override
        public ExerciseFieldViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = getLayoutInflater().inflate(R.layout.exercise_fields_item, parent, false);

            return new ExerciseFieldViewHolder(v);
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
        return    mReference.orderByChild("fiels");
    }


}
