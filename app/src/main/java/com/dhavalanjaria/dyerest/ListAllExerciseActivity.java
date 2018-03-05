package com.dhavalanjaria.dyerest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dhavalanjaria.dyerest.viewholders.AddExerciseToDayViewHolder;
import com.dhavalanjaria.dyerest.viewholders.ExerciseListViewHolder;
import com.dhavalanjaria.dyerest.viewholders.ListAllExercisesViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by Dhaval Anjaria on 3/5/2018.
 */

public class ListAllExerciseActivity extends ExerciseListActivity {


    public static Intent newIntent(Context context, DatabaseReference workoutDayRef) {
        Intent intent = new Intent(context, ListAllExerciseActivity.class);
        //TODO: See Option A for Schema.
        intent.putExtra(EXTRA_WORKOUT_DAY, workoutDayRef.toString());
        intent.putExtra(EXTRA_LIST_TYPE, LIST_TYPE.ALL_EXERCISE_LIST);
        return intent;
    }

    @Override
    public Query getQuery() {
        return null;
    }
}
