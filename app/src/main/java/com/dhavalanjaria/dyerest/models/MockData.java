package com.dhavalanjaria.dyerest.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Dhaval Anjaria on 2/7/2018.
 */

public class MockData {

    public static List<Workout> getWorkouts() {
        List<Workout> retVal = new ArrayList<>();

        for(int i = 1; i < 4; i++) {
            Workout workout = new Workout(getUser().getUserId(), "Workout " + i, new Date());
            workout.setTotalPoints(1730);
            retVal.add(workout);

        }
        return retVal;
    }

    public static List<Exercise> getLiftingExercises() {
        List<Exercise> retVal = new ArrayList<>();

        for(int i = 1; i < 4; i++) {
            Exercise newExercise = new Exercise();
            newExercise.setTotalSets(3);
            newExercise.setPoints(10);
            newExercise.setPoundage(15);
            newExercise.setName("Exercise " + i + " name");
            retVal.add(newExercise);
        }
        return retVal;
    }

    public static List<WorkoutDay> getWorkoutDays() {
        List<WorkoutDay> retval = new ArrayList<>();

        for(int i = 1; i < 4; i++) {
            WorkoutDay day = new WorkoutDay();
            day.setName("Day " + i);
            day.setExercises(getLiftingExercises());
            retval.add(day);
        }
        return retval;
    }

    public static User getUser() {
        User retVal = new User();

        retVal.setUserId("1");
        retVal.setUsername("mail");

        return retVal;
    }
}
