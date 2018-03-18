package com.dhavalanjaria.dyerest.models;

/**
 * Created by Dhaval Anjaria on 3/17/2018.
 */

public class ExercisePerformed {
    private int sequenceNo;
    private int setNo;
    private int points;
    // There will also be a map called "values" or something


    public ExercisePerformed() {
        // DataSnapshot
    }

    public ExercisePerformed(int sequenceNo, int setNo, int points) {
        this.sequenceNo = sequenceNo;
        this.setNo = setNo;
        this.points = points;
    }

    public int getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(int sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public int getSetNo() {
        return setNo;
    }

    public void setSetNo(int setNo) {
        this.setNo = setNo;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
