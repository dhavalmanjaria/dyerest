package com.dhavalanjaria.dyerest.models;

/**
 * Created by Dhaval Anjaria on 2/15/2018.
 */

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the Map between an ExerciseField and it's value for a given date.
 */
public class ActiveExerciseField {
    private String fieldName;
    private int value;

    public ActiveExerciseField(String fieldName, int value) {
        this.fieldName = fieldName;
        this.value = value;
    }

    public ActiveExerciseField() { }

    public String getFieldName() {
        return fieldName;
    }

    public int getValue() {
        return value;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Map<String, Object> toMap() {

        Map<String, Object> map = new HashMap<>();
        map.put(getFieldName(), getValue());
        return map;
    }

    @Override
    public String toString() {
        return getFieldName() + ": " + getValue();
    }

}
