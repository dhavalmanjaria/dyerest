package com.dhavalanjaria.dyerest.models;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dhaval Anjaria on 2/7/2018.
 */

public class WorkoutDay {

    private String name;
    private LinkedList<Exercise> exercises;

    public WorkoutDay() {
        // For DataSnapshot.getValue();
    }

    public WorkoutDay(String name, LinkedList<Exercise> exercises) {
        this.name = name;
        this.exercises = exercises;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(LinkedList<Exercise> exercises) {
        this.exercises = exercises;
    }
}
