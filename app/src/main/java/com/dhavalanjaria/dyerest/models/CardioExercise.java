package com.dhavalanjaria.dyerest.models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Dhaval Anjaria on 2/15/2018.
 */

/**
 * Class is temporary and should not be used. This will be replaced by a Firebase JSON Schema
 */
@Deprecated
public class CardioExercise extends Exercise {

    private ArrayList<ExerciseMap> mExerciseMaps;

    public CardioExercise() {
        mExerciseMaps = new ArrayList<>();
        mExerciseMaps.add(new ExerciseMap("duration", 0));
        mExerciseMaps.add(new ExerciseMap("distance", 0));
        mExerciseMaps.add(new ExerciseMap("intensity", 0));
        mExerciseMaps.add(new ExerciseMap("incline", 0));
    }

    @Override
    public String toString() {
        StringBuffer retVal = new StringBuffer();
        for (ExerciseMap exerciseMap: mExerciseMaps) {
            retVal.append(exerciseMap.getFieldName() + ": " + exerciseMap.getValue());
        }
        return retVal.toString();
    }

    @Override
    public ArrayList<ExerciseMap> getExerciseMaps() {
        return mExerciseMaps;
    }

    // No setter because this is temporary and should not be used to write data.
}
