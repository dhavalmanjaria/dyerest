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
import android.widget.TextView;

import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.models.Exercise;
import com.dhavalanjaria.dyerest.models.ExerciseMap;
import com.dhavalanjaria.dyerest.viewholders.ExerciseDetailViewHolder;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Dhaval Anjaria on 2/15/2018.
 */

public class ActiveWorkoutExerciseFragment extends Fragment {

    protected static Exercise mExercise;
    private RecyclerView mExerciseRecycler;
    private TextView mExerciseNameText;

    // The exercise position should really be retrieved from the actual model
    public static ActiveWorkoutExerciseFragment newInstance(Exercise exercise) {

        Bundle args = new Bundle();
        mExercise = exercise;
        ActiveWorkoutExerciseFragment fragment = new ActiveWorkoutExerciseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_active_workout_exercise, container, false);

        mExerciseNameText = v.findViewById(R.id.exercise_name_card_text);
        mExerciseNameText.setText(mExercise.getName());

        mExerciseRecycler = (RecyclerView) v.findViewById(R.id.active_exercise_detail_recycler);
        mExerciseRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        ExerciseDetailAdapter adapter = new ExerciseDetailAdapter();
        mExerciseRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return v;
    }

    private class ExerciseDetailAdapter extends RecyclerView.Adapter<ExerciseDetailViewHolder> {

        private List<ExerciseMap> mExerciseMapList;

        public ExerciseDetailAdapter() {
            mExerciseMapList = mExercise.getExerciseMaps();
        }

        @Override
        public ExerciseDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View itemView = inflater.inflate(R.layout.active_exercise_detail_item, parent, false);

            return new ExerciseDetailViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ExerciseDetailViewHolder holder, int position) {
            ExerciseMap exerciseMap = mExerciseMapList.get(position);
            holder.bind(exerciseMap.getFieldName(), exerciseMap.getValue());
        }

        @Override
        public int getItemCount() {
            return mExerciseMapList.size();
        }
    }
}
