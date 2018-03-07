package com.dhavalanjaria.dyerest;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.dhavalanjaria.dyerest.models.DayExercise;
import com.dhavalanjaria.dyerest.models.MockData;
import com.dhavalanjaria.dyerest.viewholders.DayExerciseSequenceViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

public class EditDaySequenceActivity extends BaseActivity {

    public static final String EXTRA_WORKOUT_DAY = "EditDaySequenceActivity.WorkoutDay";
    public static final String TAG = "EditDaySequenceActivity";

    public DatabaseReference mWorkoutDayReference;

    private RecyclerView mSequenceItemRecycler;
    private SequenceItemAdapter mAdapter;

    public static Intent newIntent(Context context, String workoutDayKey) {
        Intent intent = new Intent(context, EditDaySequenceActivity.class);
        intent.putExtra(EXTRA_WORKOUT_DAY, workoutDayKey);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_day_sequence);

        String workoutDayKey = (String) getIntent().getSerializableExtra(EXTRA_WORKOUT_DAY);

        mWorkoutDayReference = FirebaseDatabase.getInstance().getReferenceFromUrl(workoutDayKey);

        mSequenceItemRecycler = findViewById(R.id.edit_day_sequence_recycler);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<DayExercise>()
                .setQuery(getQuery(), DayExercise.class)
                .build();

        mAdapter = new SequenceItemAdapter(options);

        GetScreenshotAdapter adapter = new GetScreenshotAdapter();

        mSequenceItemRecycler.setAdapter(adapter);
        mSequenceItemRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public Query getQuery() {
        return mWorkoutDayReference.orderByKey();
    }

    private class GetScreenshotAdapter extends RecyclerView.Adapter<DayExerciseSequenceViewHolder> {

        private List<DayExercise> mModel;

        public GetScreenshotAdapter() {
            mModel = MockData.getWorkoutSequence();
        }

        @Override
        public DayExerciseSequenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = getLayoutInflater().inflate(R.layout.edit_day_sequence_item, parent, false);

            return new DayExerciseSequenceViewHolder(v);
        }

        @Override
        public void onBindViewHolder(DayExerciseSequenceViewHolder holder, int position) {
            holder.bind(mModel.get(position), mModel.get(position).getExerciseKey());
        }

        @Override
        public int getItemCount() {
            return mModel.size();
        }
    }

    private class SequenceItemAdapter extends FirebaseRecyclerAdapter<DayExercise, DayExerciseSequenceViewHolder> {

        public SequenceItemAdapter(@NonNull FirebaseRecyclerOptions<DayExercise> options) {
            super(options);
        }

        @Override
        public DayExerciseSequenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = getLayoutInflater().inflate(R.layout.edit_day_sequence_item, parent, false);

            return new DayExerciseSequenceViewHolder(v);
        }

        @Override
        protected void onBindViewHolder(@NonNull DayExerciseSequenceViewHolder holder, int position, @NonNull DayExercise model) {
            holder.bind(model, mWorkoutDayReference, getRef(position));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }
}
