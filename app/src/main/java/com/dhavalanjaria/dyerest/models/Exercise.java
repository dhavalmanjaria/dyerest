package com.dhavalanjaria.dyerest.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dhaval Anjaria on 3/7/2018.
 */

public class Exercise {

    public String name;
    public String exerciseType;
    public int totalPoints;
    public Map<String, Object> exerciseFields;

    public Exercise() {
        // For DataSnapshot
    }

    public Exercise(String name, String exerciseType, int totalPoints, Map<String, Object> exerciseFields) {
        this.name = name;
        this.exerciseType = exerciseType;
        this.totalPoints = totalPoints;
        this.exerciseFields = exerciseFields;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Map<String, Object> getExerciseFields() {
        return exerciseFields;
    }

    public void setExerciseFields(Map<String, Object> exerciseFields) {
        this.exerciseFields = exerciseFields;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> exerciseDetails = new HashMap<>();

        exerciseDetails.put("name", getName());
        exerciseDetails.put("exerciseFields", getExerciseFields()); // Null for now. To be updated with proper exercise fields later
        exerciseDetails.put("totalPoints", getTotalPoints());
        exerciseDetails.put("exerciseType", getExerciseType());

        return exerciseDetails;
    }
}
