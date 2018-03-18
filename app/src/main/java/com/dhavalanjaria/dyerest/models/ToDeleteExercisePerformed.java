package com.dhavalanjaria.dyerest.models;

import java.util.Date;

/**
 * Created by Dhaval Anjaria on 2/26/2018.
 */

@Deprecated
public class ToDeleteExercisePerformed {

    public Date datePerformed;
    public String exerciseKey;
    public String dayKey; // Day Key may not be needed
    public int points;

    public ToDeleteExercisePerformed() {
    }

    public ToDeleteExercisePerformed(Date datePerformed, String exerciseKey, String dayKey, int points) {
        this.datePerformed = datePerformed;
        this.exerciseKey = exerciseKey;
        this.dayKey = dayKey;
        this.points = points;
    }

    public Date getDatePerformed() {
        return datePerformed;
    }

    public void setDatePerformed(Date datePerformed) {
        this.datePerformed = datePerformed;
    }

    public String getExerciseKey() {
        return exerciseKey;
    }

    public void setExerciseKey(String exerciseKey) {
        this.exerciseKey = exerciseKey;
    }

    public String getDayKey() {
        return dayKey;
    }

    public void setDayKey(String dayKey) {
        this.dayKey = dayKey;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
