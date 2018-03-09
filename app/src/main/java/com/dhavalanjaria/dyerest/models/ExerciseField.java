package com.dhavalanjaria.dyerest.models;

/**
 * Created by Dhaval Anjaria on 2/23/2018.
 */

public class ExerciseField {

    private String name;
    private boolean isTrue;

    public ExerciseField(){}

    public ExerciseField(String name, boolean isTrue) {
        this.name = name;
        this.isTrue = isTrue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }

    public boolean getTrue() {
        return isTrue;
    }
}
