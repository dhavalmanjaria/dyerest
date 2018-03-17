package com.dhavalanjaria.dyerest.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

        LinkedList<ExerciseMap> map = new LinkedList<>();
        map.add(new ExerciseMap("Max Sets", 2));
        map.add(new ExerciseMap("Poundage", 15));

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
            e.setExerciseMaps(map);
        }

        return retVal;
    }

    public static List<ToDeleteExercise> getCardioExercises() {
        List<ToDeleteExercise> retVal = new ArrayList<>();

        LinkedList<ExerciseMap> map = new LinkedList<>();
        map.add(new ExerciseMap("Duration", 15));
        map.add(new ExerciseMap("Distance", 150));
        map.add(new ExerciseMap("Intensity", 3));

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
            e.setExerciseMaps(map);
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
            sequenceItem.setExerciseType("LIFTING");
            sequenceItem.setExerciseKey(e.getName());

            retVal.add(sequenceItem);
        }
        return retVal;
    }

    public static List<DayPerformed> getDayPerformed() {
        List<ToDeleteExercise> toDeleteExercises = getRandomExercises();

        WorkoutDay day = getWorkoutDays().get(0);

        final Calendar cal = Calendar.getInstance();

        ArrayList<DayPerformed> retval = new ArrayList<DayPerformed>();
        int i = toDeleteExercises.size();
        for (ToDeleteExercise e: toDeleteExercises) {
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
        List<ToDeleteExercise> toDeleteExercises = getRandomExercises();

        WorkoutDay day = getWorkoutDays().get(0);

        ArrayList<ExercisePerformed> retval = new ArrayList<ExercisePerformed>();
        for (ToDeleteExercise e: toDeleteExercises) {
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
