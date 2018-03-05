package com.dhavalanjaria.dyerest.models;

import java.util.ArrayList;
import java.util.Calendar;
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

        LinkedList<ExerciseMap> map = new LinkedList<>();
        map.add(new ExerciseMap("Max Sets", 2));
        map.add(new ExerciseMap("Poundage", 15));

        Exercise squats = new Exercise("Squats");
        squats.setPoundage(20);

        Exercise lunges = new Exercise("Lunges");
        lunges.setPoundage(20);

        Exercise legpress = new Exercise("Leg Press");
        legpress.setPoundage(20);

        retVal.add(legpress);
        retVal.add(squats);
        retVal.add(lunges);

        for (Exercise e: retVal) {
            e.setExerciseMaps(map);
        }

        return retVal;
    }

    public static List<Exercise> getCardioExercises() {
        List<Exercise> retVal = new ArrayList<>();

        LinkedList<ExerciseMap> map = new LinkedList<>();
        map.add(new ExerciseMap("Duration", 15));
        map.add(new ExerciseMap("Distance", 150));
        map.add(new ExerciseMap("Intensity", 3));

        Exercise treadmill = new Exercise("Treadmill");
        treadmill.setIntensity(6);
        treadmill.setDuration(15);

        Exercise bicycle = new Exercise("Bicycle");
        bicycle.setIntensity(6);
        bicycle.setDuration(15);

        Exercise elliptical = new Exercise("Elliptical");
        elliptical.setIntensity(15);
        elliptical.setDuration(15);

        retVal.add(bicycle);
        retVal.add(treadmill);
        retVal.add(elliptical);

        for (Exercise e: retVal) {
            e.setExerciseMaps(map);
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

    public static List<DayExercise> getWorkoutSequence() {
        List<Exercise> exercises = getRandomExercises();

        List<DayExercise> retVal = new ArrayList<>();

        int i = 1;
        for (Exercise e: exercises) {
            DayExercise sequenceItem = new DayExercise();
            sequenceItem.setSequenceNumber(i++);
            sequenceItem.setExerciseType("LIFTING");
            sequenceItem.setExerciseKey(e.getName());

            retVal.add(sequenceItem);
        }
        return retVal;
    }

    public static List<DayPerformed> getDayPerformed() {
        List<Exercise> exercises = getRandomExercises();

        WorkoutDay day = getWorkoutDays().get(0);

        final Calendar cal = Calendar.getInstance();

        ArrayList<DayPerformed> retval = new ArrayList<DayPerformed>();
        int i = exercises.size();
        for (Exercise e: exercises) {
            DayPerformed dayPerformed = new DayPerformed();

            cal.add(Calendar.DATE, i--);
            dayPerformed.setDatePerformed(cal.getTime());
            dayPerformed.setPoints(200);
            dayPerformed.setExerciseKey(e.getName());
            dayPerformed.setDayKey(day.getName());

            retval.add(dayPerformed);
        }
        return retval;
    }

    public static User getUser() {
        User retVal = new User();

        retVal.setUserId("1");
        retVal.setUsername("mail");

        return retVal;
    }

    public static List<ExercisePerformed> getExercisesPerformed() {
        List<Exercise> exercises = getRandomExercises();

        WorkoutDay day = getWorkoutDays().get(0);

        ArrayList<ExercisePerformed> retval = new ArrayList<ExercisePerformed>();
        for (Exercise e: exercises) {
            ExercisePerformed exercisePerformed = new ExercisePerformed();
            exercisePerformed.setDatePerformed(new Date());
            exercisePerformed.setPoints(20);
            exercisePerformed.setExerciseKey(e.getName());
            exercisePerformed.setDayKey(day.getName());

            retval.add(exercisePerformed);
        }
        return retval;
    }
}
