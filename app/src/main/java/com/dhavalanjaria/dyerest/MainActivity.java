package com.dhavalanjaria.dyerest;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dhavalanjaria.dyerest.models.MockData;
import com.dhavalanjaria.dyerest.models.Workout;
import com.dhavalanjaria.dyerest.viewholders.WorkoutCardViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private List<Workout> mWorkoutList;
    private RecyclerView mRecyclerView;
    private Button [] mViewWorkoutButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWorkoutList = MockData.getWorkouts();
        mRecyclerView = (RecyclerView) findViewById(R.id.card_container);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new WorkoutCardAdapter());


    }

    private class WorkoutCardAdapter extends RecyclerView.Adapter<WorkoutCardViewHolder> {
        @Override
        public WorkoutCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View cardView = inflater.inflate(R.layout.workout_card_view, parent, false);

            return new WorkoutCardViewHolder(cardView);
        }

        @Override
        public void onBindViewHolder(WorkoutCardViewHolder holder, int position) {
            holder.bind(mWorkoutList.get(position));
        }

        @Override
        public int getItemCount() {
            return mWorkoutList.size();
        }
    }
}
