package com.dhavalanjaria.dyerest.models;

import android.support.annotation.Nullable;

/**
 * Created by Dhaval Anjaria on 2/6/2018.
 */

public class Exercise {

    public String name;
    public int points;

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

    public int [] setPoundage;

    public String [] days;

    public Exercise() {
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

    public int[] getSetPoundage() {
        return setPoundage;
    }

    public void setSetPoundage(int[] setPoundage) {
        this.setPoundage = setPoundage;
    }

    public String[] getDays() {
        return days;
    }

    public void setDays(String[] days) {
        this.days = days;
    }

    public Exercise(String name) {
        this.name = name;
    }


    //TODO: Map Sets to points somehow.

    //TODO: See toMap() to exclude fields from an exercise.
}
