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
    public int sequenceNumber;
    public String name;
    public int totalPoints;

    public DayExercise() {
        // For DataSnapshot
    }

    public DayExercise(int sequenceNumber, String name, int totalPoints) {
        this.sequenceNumber = sequenceNumber;
        this.name = name;
        this.totalPoints = totalPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
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
        map.put("name", getName());

        return map;
    }
}
