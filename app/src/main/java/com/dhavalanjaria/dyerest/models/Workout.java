package com.dhavalanjaria.dyerest.models;

import java.util.Date;

/**
 * Created by Dhaval Anjaria on 2/6/2018.
 */

public class Workout {

    public String userId;
    public String name;
    public Date dateCreated;
    public int totalPoints;

    public Workout() {
    }

    public Workout(String userId, String name, Date dateCreated) {
        this.userId = userId;
        this.name = name;
        this.dateCreated = dateCreated;

        // If a new workout is created, the totalPoints should be 0
        this.totalPoints = 0;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    //TODO: See toMap();
    // Especially to exclude things when creating a new workout.

}
