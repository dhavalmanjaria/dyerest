package com.dhavalanjaria.dyerest.models;

/**
 * Created by Dhaval Anjaria on 2/26/2018.
 */

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This model contains metadata for a specific exercise performed on a specific day. This will be
 * similar to the tree that would exist in Firebase
 */
public class DayExercise {
    public String exerciseKey;
    public int sequenceNumber;
    public String exerciseType;
    public int totalPoints;

    public DayExercise() {
        // For DataSnapshot
    }

    public DayExercise(String exerciseKey, int sequenceNumber, String exerciseType, int totalPoints) {
        this.exerciseKey = exerciseKey;
        this.sequenceNumber = sequenceNumber;
        this.exerciseType = exerciseType;
        this.totalPoints = totalPoints;
    }

    public String getExerciseKey() {
        return exerciseKey;
    }

    public void setExerciseKey(String exerciseKey) {
        this.exerciseKey = exerciseKey;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
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

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("sequenceNumber", getSequenceNumber());
        map.put("totalPoints", getTotalPoints());

        return map;
    }
}
