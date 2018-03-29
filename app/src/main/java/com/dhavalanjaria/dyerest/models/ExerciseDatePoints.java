package com.dhavalanjaria.dyerest.models;

import java.util.Date;

/**
 * Created by Dhaval Anjaria on 3/29/2018.
 */

public class ExerciseDatePoints {
    private Date date;
    private int points;

    public ExerciseDatePoints(Date date, int points) {
        this.date = date;
        this.points = points;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
