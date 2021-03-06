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

    public static LinkedList<ToDeleteExercise> getLiftingExercises() {
        LinkedList<ToDeleteExercise> retVal = new LinkedList<>();

        LinkedList<ActiveExerciseField> map = new LinkedList<>();
        map.add(new ActiveExerciseField("Max Sets", 2));
        map.add(new ActiveExerciseField("Poundage", 15));

        ToDeleteExercise squats = new ToDeleteExercise("Squats");
        squats.setPoundage(20);

        ToDeleteExercise lunges = new ToDeleteExercise("Lunges");
        lunges.setPoundage(20);

        ToDeleteExercise legpress = new ToDeleteExercise("Leg Press");
        legpress.setPoundage(20);

        retVal.add(legpress);
        retVal.add(squats);
        retVal.add(lunges);

        for (ToDeleteExercise e: retVal) {
            e.setActiveExerciseFields(map);
        }

        return retVal;
    }

    public static List<ToDeleteExercise> getCardioExercises() {
        List<ToDeleteExercise> retVal = new ArrayList<>();

        LinkedList<ActiveExerciseField> map = new LinkedList<>();
        map.add(new ActiveExerciseField("Duration", 15));
        map.add(new ActiveExerciseField("Distance", 150));
        map.add(new ActiveExerciseField("Intensity", 3));

        ToDeleteExercise treadmill = new ToDeleteExercise("Treadmill");
        treadmill.setIntensity(6);
        treadmill.setDuration(15);

        ToDeleteExercise bicycle = new ToDeleteExercise("Bicycle");
        bicycle.setIntensity(6);
        bicycle.setDuration(15);

        ToDeleteExercise elliptical = new ToDeleteExercise("Elliptical");
        elliptical.setIntensity(15);
        elliptical.setDuration(15);

        retVal.add(bicycle);
        retVal.add(treadmill);
        retVal.add(elliptical);

        for (ToDeleteExercise e: retVal) {
            e.setActiveExerciseFields(map);
        }

        return retVal;
    }

    public static List<ToDeleteExercise> getRandomExercises() {
        List<ToDeleteExercise> toDeleteExercises = getLiftingExercises();
        toDeleteExercises.addAll(getCardioExercises());

        return toDeleteExercises;
    }

    public static List<WorkoutDay> getWorkoutDays() {
        List<WorkoutDay> retval = new ArrayList<>();

        for(int i = 1; i < 4; i++) {
            WorkoutDay day = new WorkoutDay();
            day.setName("Day " + i);
            //day.setExercises(new HashMap<String, Object>());
            retval.add(day);
        }
        return retval;
    }

    public static List<DayExercise> getWorkoutSequence() {
        List<ToDeleteExercise> toDeleteExercises = getRandomExercises();

        List<DayExercise> retVal = new ArrayList<>();

        int i = 1;
        for (ToDeleteExercise e: toDeleteExercises) {
            DayExercise sequenceItem = new DayExercise();
            sequenceItem.setSequenceNumber(i++);

            retVal.add(sequenceItem);
        }
        return retVal;
    }

    public static List<ExercisePoints> getDayPerformed() {
        List<ToDeleteExercise> toDeleteExercises = getRandomExercises();

        WorkoutDay day = getWorkoutDays().get(0);

        final Calendar cal = Calendar.getInstance();

        ArrayList<ExercisePoints> retval = new ArrayList<ExercisePoints>();
        int i = toDeleteExercises.size();
        for (ToDeleteExercise e: toDeleteExercises) {
            ExercisePoints exercisePoints = new ExercisePoints();

            cal.add(Calendar.DATE, i--);
            exercisePoints.setPoints(200);
            exercisePoints.setExerciseName(day.getName());

            retval.add(exercisePoints);
        }
        return retval;
    }

    public static User getUser() {
        User retVal = new User();

        retVal.setUserId("1");
        retVal.setUsername("mail");

        return retVal;
    }

    public static List<ToDeleteExercisePerformed> getExercisesPerformed() {
        List<ToDeleteExercise> toDeleteExercises = getRandomExercises();

        WorkoutDay day = getWorkoutDays().get(0);

        ArrayList<ToDeleteExercisePerformed> retval = new ArrayList<ToDeleteExercisePerformed>();
        for (ToDeleteExercise e: toDeleteExercises) {
            ToDeleteExercisePerformed toDeleteExercisePerformed = new ToDeleteExercisePerformed();
            toDeleteExercisePerformed.setDatePerformed(new Date());
            toDeleteExercisePerformed.setPoints(20);
            toDeleteExercisePerformed.setExerciseKey(e.getName());
            toDeleteExercisePerformed.setDayKey(day.getName());

            retval.add(toDeleteExercisePerformed);
        }
        return retval;
    }
}
