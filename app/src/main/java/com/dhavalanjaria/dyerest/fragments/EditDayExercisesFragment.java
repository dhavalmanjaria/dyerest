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
import com.dhavalanjaria.dyerest.models.Exercise;
import com.dhavalanjaria.dyerest.models.MockData;
import com.dhavalanjaria.dyerest.viewholders.ExerciseListViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.List;

/**
 * Created by Dhaval Anjaria on 2/14/2018.
 */

public abstract class EditDayExercisesFragment extends Fragment {

    public static final String TAG = "EditDayExercisesFragment";
    protected static final String KEY_LIST_TYPE = "EditDayExercisesFragment.ListType";

    private RecyclerView mExerciseListRecycler;
    protected List<Exercise> mExerciseList;
    protected ExerciseListActivity.LIST_TYPE mListType;

    public EditDayExercisesFragment() {
        mExerciseList = MockData.getLiftingExercises();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_exercise_list, container, false);

        mListType = (ExerciseListActivity.LIST_TYPE) getArguments().getSerializable(KEY_LIST_TYPE);
        mExerciseListRecycler = (RecyclerView) v.findViewById(R.id.add_exercise_recycler);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // There should be a constructor taking an ExerciseList, perhaps
        mExerciseListRecycler.setAdapter(new AddExerciseAdapter());

        // Set up layout manager
        mExerciseListRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Also, notifyDataSetChanged wouldn't really matter here since the exercise list isn't
        // changing
    }

    private class AddExerciseAdapter extends RecyclerView.Adapter<ExerciseListViewHolder> {
        @Override
        public ExerciseListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View v = inflater.inflate(R.layout.add_exercise_item, parent,false);
            return ((ExerciseListActivity)getContext()).createExerciseListViewHolder(mListType, v);
        }

        @Override
        public void onBindViewHolder(ExerciseListViewHolder holder, int position) {
            holder.bind(mExerciseList.get(position), "");
        }

        @Override
        public int getItemCount() {
            return mExerciseList.size();
        }

    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public abstract Query getQuery(DatabaseReference databaseReference);
}
