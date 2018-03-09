package com.dhavalanjaria.dyerest.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dhavalanjaria.dyerest.ExerciseListActivity;
import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.models.DayExercise;
import com.dhavalanjaria.dyerest.models.Exercise;
import com.dhavalanjaria.dyerest.models.ToDeleteExercise;
import com.dhavalanjaria.dyerest.models.MockData;
import com.dhavalanjaria.dyerest.viewholders.AddExerciseToDayViewHolder;
import com.dhavalanjaria.dyerest.viewholders.ExerciseListViewHolder;
import com.dhavalanjaria.dyerest.viewholders.ListAllExercisesViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

/**
 * Created by Dhaval Anjaria on 2/14/2018.
 */

public abstract class EditDayExercisesFragment extends Fragment {

    public static final String TAG = "EditDayExercisesFragment";
    protected static final String KEY_LIST_TYPE = "EditDayExercisesFragment.ListType";
    protected static final String KEY_WORKOUT_DAY_REF_URL = "EditDayExercisesFragment.WorkoutDayRefUrl";

    private RecyclerView mExerciseListRecycler;
    protected List<ToDeleteExercise> mToDeleteExerciseList;
    protected ExerciseListActivity.LIST_TYPE mListType;
    protected AddExerciseToDayAdapter mAddExerciseToDayAdapter;
    protected DatabaseReference mDayReference;
    private ListExercisesAdapter mListExercisesAdapter;

    public EditDayExercisesFragment() {
        mToDeleteExerciseList = MockData.getLiftingExercises();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_exercise_list, container, false);

        mListType = (ExerciseListActivity.LIST_TYPE) getArguments().getSerializable(KEY_LIST_TYPE);
        mExerciseListRecycler = (RecyclerView) v.findViewById(R.id.add_exercise_recycler);

        // Create the Adapters
        FirebaseRecyclerOptions addToDayOptions = new FirebaseRecyclerOptions.Builder<DayExercise>()
                .setQuery(getQuery(), DayExercise.class)
                .build();

        FirebaseRecyclerOptions listExerciseOptions = new FirebaseRecyclerOptions.Builder<Exercise>()
                .setQuery(getQuery(), Exercise.class)
                .build();

        mAddExerciseToDayAdapter = new AddExerciseToDayAdapter(addToDayOptions);
        mListExercisesAdapter = new ListExercisesAdapter(listExerciseOptions);

        String workoutDayRefUrl = getArguments().getString(KEY_WORKOUT_DAY_REF_URL);
        // this will be workoutId.days
        mDayReference = FirebaseDatabase.getInstance().getReferenceFromUrl(workoutDayRefUrl);

        // Note: the query is incomplete because it doesn't account for the exercises that already
        // exist in the day.

        mExerciseListRecycler.setAdapter(createAdapter(mListType));
        mExerciseListRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    /**
     * Factory method to return proper Adapter with proper ViewHolder based on which kind of list
     * of exercises was requested.
     * @param type
     * @return
     */
    private RecyclerView.Adapter createAdapter(ExerciseListActivity.LIST_TYPE type) {
        switch (type) {
            case ADD_TO_DAY:
                return mAddExerciseToDayAdapter;
            case ALL_EXERCISE_LIST:
                return mListExercisesAdapter;
            default:
                break;
        }
        return null;
    }

    private class AddExerciseToDayAdapter extends FirebaseRecyclerAdapter<DayExercise, AddExerciseToDayViewHolder> {

        public AddExerciseToDayAdapter(@NonNull FirebaseRecyclerOptions<DayExercise> options) {
            super(options);
        }

        @Override
        public AddExerciseToDayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View v = inflater.inflate(R.layout.add_exercise_item, parent,false);
            return new AddExerciseToDayViewHolder(v);
        }

        @Override
        protected void onBindViewHolder(@NonNull AddExerciseToDayViewHolder holder, int position, @NonNull DayExercise model) {
            holder.bind(model, getRef(position), mDayReference);
        }
    }

    private class ListExercisesAdapter extends FirebaseRecyclerAdapter<Exercise, ListAllExercisesViewHolder> {
        public ListExercisesAdapter(@NonNull FirebaseRecyclerOptions<Exercise> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull ListAllExercisesViewHolder holder, int position, @NonNull Exercise model) {
            holder.bind(model, getRef(position));
        }

        @Override
        public ListAllExercisesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View v = inflater.inflate(R.layout.add_exercise_item, parent,false);
            return new ListAllExercisesViewHolder(v);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAddExerciseToDayAdapter.startListening();
        mListExercisesAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAddExerciseToDayAdapter.stopListening();
        mListExercisesAdapter.stopListening();
    }



    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public abstract Query getQuery();
}
