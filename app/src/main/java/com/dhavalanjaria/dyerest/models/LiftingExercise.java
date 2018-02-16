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
public class LiftingExercise extends Exercise {

    private LinkedList<ExerciseMap> mExerciseMap;

    public LiftingExercise() {
        mExerciseMap = new LinkedList<>();

        mExerciseMap.add(new ExerciseMap("poundage", 0));
        mExerciseMap.add(new ExerciseMap("poundage", 0));
        mExerciseMap.add(new ExerciseMap("poundage", 0));
    }

    @Override
    public LinkedList<ExerciseMap> getExerciseMaps() {
        return mExerciseMap;
    }

    // No setter because this is temporary and should not be used to write data.
}
