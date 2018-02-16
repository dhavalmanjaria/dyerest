package com.dhavalanjaria.dyerest.models;

/**
 * Created by Dhaval Anjaria on 2/15/2018.
 */

import java.util.HashMap;

/**
 * Super class of Exercise Map, containing the proper fields and it's type. The type may change
 * however for now it is an int.
 */
@Deprecated
public class ExerciseMap {
    private String fieldName;
    private int value;

    public ExerciseMap(String fieldName, int value) {
        this.fieldName = fieldName;
        this.value = value;
    }

    public ExerciseMap() { }

    public String getFieldName() {
        return fieldName;
    }

    public int getValue() {
        return value;
    }

    // No setter because this is temporary and should not be used to write data.
}
