package com.dhavalanjaria.dyerest.models;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Dhaval Anjaria on 2/7/2018.
 */

public class WorkoutDay {

    private String name;
    private Map<String, Object> exercises;

    public WorkoutDay() {
        // For DataSnapshot.getValue();
    }

    public WorkoutDay(String name) {
        this.name = name;
    }

    public WorkoutDay(String name, Map<String, Object> exercises) {
        this.name = name;
        this.exercises = exercises;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object>getExercises() {
        return exercises;
    }

    public void setExercises(Map<String, Object> exercises) {
        this.exercises = exercises;
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", getName());
        // add exercises

        return map;
    }
}
