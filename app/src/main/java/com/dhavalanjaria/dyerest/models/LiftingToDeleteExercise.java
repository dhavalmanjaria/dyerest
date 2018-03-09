package com.dhavalanjaria.dyerest.models;

import java.util.LinkedList;

/**
 * Created by Dhaval Anjaria on 2/15/2018.
 */

/**
 * This is a temporary class used to represent a JSON schema of exercises with their fields and their
 * values.
 */
@Deprecated
public class LiftingToDeleteExercise extends ToDeleteExercise {

    private LinkedList<ExerciseMap> mExerciseMap;

    public LiftingToDeleteExercise() {
        mExerciseMap = new LinkedList<>();

        mExerciseMap.add(new ExerciseMap("poundage", 0));
        mExerciseMap.add(new ExerciseMap("poundage", 0));
        mExerciseMap.add(new ExerciseMap("poundage", 0));
    }

    @Override
    public LinkedList<ExerciseMap> getExerciseMaps() {
        return mExerciseMap;
    }

    @Override
    public String toString() {
        StringBuffer retVal = new StringBuffer();
        for (ExerciseMap exerciseMap: mExerciseMaps) {
            retVal.append(exerciseMap.getFieldName() + ": " + exerciseMap.getValue());
        }
        return retVal.toString();
    }

    // No setter because this is temporary and should not be used to write data.
}
