package com.dhavalanjaria.dyerest.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dhaval Anjaria on 2/7/2018.
 */

public class MockData {

    public static List<Workout> getWorkouts() {
        List<Workout> retVal = new ArrayList<>();

        for(int i = 1; i < 5; i++) {
            Workout workout = new Workout("Workout " + i, new Date());
            workout.setTotalPoints(1730);
            retVal.add(workout);

        }
        return retVal;
    }

    public static LinkedList<Exercise> getLiftingExercises() {
        LinkedList<Exercise> retVal = new LinkedList<>();

        for(int i = 1; i < 4; i++) {
            Exercise newExercise = new LiftingExercise();
            newExercise.setTotalSets(3);
            newExercise.setPoints(10);
            newExercise.setPoundage(15);
            newExercise.setName("Lifting Exercise " + i + " name");

            retVal.add(newExercise);
        }
        return retVal;
    }

    public static List<Exercise> getCardioExercises() {
        List<Exercise> retVal = new ArrayList<>();

        for(int i = 1; i < 4; i++) {
            Exercise newExercise = new CardioExercise();
            newExercise.setDuration(15);
            newExercise.setIntensity(3);
            newExercise.setName("Cardio Exercise " + i + " name");

            retVal.add(newExercise);
        }
        return retVal;
    }

    public static LinkedList<Exercise> getRandomExercises() {
        List<Exercise> exercises = getLiftingExercises();
        exercises.addAll(getCardioExercises());

        return (LinkedList<Exercise>) exercises;
    }

    public static List<WorkoutDay> getWorkoutDays() {
        List<WorkoutDay> retval = new ArrayList<>();

        for(int i = 1; i < 4; i++) {
            WorkoutDay day = new WorkoutDay();
            day.setName("Day " + i);
            day.setExercises(getRandomExercises());
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
