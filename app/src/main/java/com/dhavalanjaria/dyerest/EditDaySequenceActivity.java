package com.dhavalanjaria.dyerest;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.dhavalanjaria.dyerest.models.DayExercise;
import com.dhavalanjaria.dyerest.models.MockData;
import com.dhavalanjaria.dyerest.viewholders.DayExerciseSequenceViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
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
        mSequenceItemRecycler.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<DayExercise>()
                .setQuery(getQuery(), DayExercise.class)
                .build();

        final List<DatabaseReference> model = new ArrayList<>();


        mAdapter = new SequenceItemAdapter(model);
        getQuery().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot exerciseSnap: dataSnapshot.getChildren()) {
                    model.add(exerciseSnap.getRef());
                }

                mSequenceItemRecycler.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
                Log.e(TAG, databaseError.getDetails());
            }
        });


        mSequenceItemRecycler.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.START | ItemTouchHelper.END);
                // See also: makeMovementFlags()
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                Collections.swap(mAdapter.getModel(), viewHolder.getAdapterPosition(), target.getAdapterPosition());
                // And now for the fun stuff...

                int i = 1;
                for (DatabaseReference dayExerciseRef: mAdapter.getModel()) {
                    dayExerciseRef.child("sequenceNumber").setValue(i);
                    i++;
                }

                mAdapter.notifyItemMoved(viewHolder.getLayoutPosition(), target.getAdapterPosition());

                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });

        itemTouchHelper.attachToRecyclerView(mSequenceItemRecycler);
    }

    @Override
    public Query getQuery() {
        return mWorkoutDayReference.child("exercises").orderByChild("sequenceNumber");
    }

    private class SequenceItemAdapter extends RecyclerView.Adapter<DayExerciseSequenceViewHolder> {

        List<DatabaseReference> mModel;

        public SequenceItemAdapter(List<DatabaseReference> model) {
            mModel = model;
        }

        public List<DatabaseReference> getModel() {
            return mModel;
        }

        @Override
        public DayExerciseSequenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = getLayoutInflater().inflate(R.layout.edit_day_sequence_item, parent,
                    false);

            return new DayExerciseSequenceViewHolder(v);
        }

        @Override
        public void onBindViewHolder(DayExerciseSequenceViewHolder holder, int position) {
            holder.bind(mModel.get(position));
        }

        @Override
        public int getItemCount() {
            return mModel.size();
        }
    }

}
