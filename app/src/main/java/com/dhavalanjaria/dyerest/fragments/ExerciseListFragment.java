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

import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.models.Exercise;
import com.dhavalanjaria.dyerest.models.MockData;
import com.dhavalanjaria.dyerest.viewholders.AddExerciseViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.List;

/**
 * Created by Dhaval Anjaria on 2/14/2018.
 */

public abstract class ExerciseListFragment extends Fragment {

    public static final String TAG = "ExerciseListFragment";

    private RecyclerView mExerciseListRecycler;
    protected List<Exercise> mExerciseList;

    private class AddExerciseAdapter extends RecyclerView.Adapter<AddExerciseViewHolder> {
        @Override
        public AddExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View v = inflater.inflate(R.layout.add_exercise_item, parent,false);
            return new AddExerciseViewHolder(v);
        }

        @Override
        public void onBindViewHolder(AddExerciseViewHolder holder, int position) {
            holder.bind(mExerciseList.get(position));
        }

        @Override
        public int getItemCount() {
            return mExerciseList.size();
        }
    }

    public ExerciseListFragment() {
        mExerciseList = MockData.getLiftingExercises();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_exercise_list, container, false);

        mExerciseListRecycler = (RecyclerView) v.findViewById(R.id.add_exercise_recycler);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up layout manager
        mExerciseListRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        // There should be a constructor taking an ExerciseList, perhaps
        mExerciseListRecycler.setAdapter(new AddExerciseAdapter());

        // Also, notifyDataSetChanged wouldn't really matter here since the exercise list isn't
        // changing
    }



    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public abstract Query getQuery(DatabaseReference databaseReference);
}
