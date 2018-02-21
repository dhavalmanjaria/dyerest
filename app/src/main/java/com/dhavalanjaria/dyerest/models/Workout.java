package com.dhavalanjaria.dyerest.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dhaval Anjaria on 2/6/2018.
 */

public class Workout {

    private String name;
    private Date dateCreated;
    private int totalPoints;

    public Workout() {
    }

    public Workout(String name, Date dateCreated) {
        this.name = name;
        this.dateCreated = dateCreated;

        // If a new workout is created, the totalPoints should be 0
        this.totalPoints = 0;
    }

    public Workout(String name, Date dateCreated, int totalPoints) {
        this.name = name;
        this.dateCreated = dateCreated;
        this.totalPoints = totalPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", getName());
        map.put("dateCreated", getDateCreated());
        map.put("totalPoints", getTotalPoints());

        return map;
    }

    //TODO: See toMap();
    // Especially to exclude things when creating a new workout.

}
