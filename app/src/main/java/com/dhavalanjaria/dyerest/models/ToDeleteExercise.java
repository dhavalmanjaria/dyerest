package com.dhavalanjaria.dyerest.models;

import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by Dhaval Anjaria on 2/6/2018.
 */

@Deprecated
public class ToDeleteExercise {

    public String name;
    public int points;

    public List<ActiveExerciseField> mActiveExerciseFields;

    // Here we see the covariance-contravariance thing
    public List<ActiveExerciseField> getActiveExerciseFields() {
        return mActiveExerciseFields;
    }

    protected void setActiveExerciseFields(List<ActiveExerciseField> activeExerciseFields) {
        this.mActiveExerciseFields = activeExerciseFields;
    }

    @Nullable
    public int totalSets;

    @Nullable
    public int poundage;

    @Nullable
    public int duration;

    @Nullable
    public int distance;

    @Nullable
    public int intensity;

    @Nullable
    public int averageSpeed;

    @Nullable
    public int incline;

    public String [] days;

    public ToDeleteExercise() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Nullable
    public int getTotalSets() {
        return totalSets;
    }

    public void setTotalSets(@Nullable int totalSets) {
        this.totalSets = totalSets;
    }

    @Nullable
    public int getPoundage() {
        return poundage;
    }

    public void setPoundage(@Nullable int poundage) {
        this.poundage = poundage;
    }

    @Nullable
    public int getDuration() {
        return duration;
    }

    public void setDuration(@Nullable int duration) {
        this.duration = duration;
    }

    @Nullable
    public int getDistance() {
        return distance;
    }

    public void setDistance(@Nullable int distance) {
        this.distance = distance;
    }

    @Nullable
    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(@Nullable int intensity) {
        this.intensity = intensity;
    }

    @Nullable
    public int getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(@Nullable int averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    @Nullable
    public int getIncline() {
        return incline;
    }

    public void setIncline(@Nullable int incline) {
        this.incline = incline;
    }

    public String[] getDays() {
        return days;
    }

    public void setDays(String[] days) {
        this.days = days;
    }

    public ToDeleteExercise(String name) {
        this.name = name;
    }


    //TODO: Map Sets to points somehow.

    //TODO: See toMap() to exclude fields from an exercise.
}
