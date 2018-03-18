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

    private LinkedList<ActiveExerciseField> mActiveExerciseField;

    public LiftingToDeleteExercise() {
        mActiveExerciseField = new LinkedList<>();

        mActiveExerciseField.add(new ActiveExerciseField("poundage", 0));
        mActiveExerciseField.add(new ActiveExerciseField("poundage", 0));
        mActiveExerciseField.add(new ActiveExerciseField("poundage", 0));
    }

    @Override
    public LinkedList<ActiveExerciseField> getActiveExerciseFields() {
        return mActiveExerciseField;
    }

    @Override
    public String toString() {
        StringBuffer retVal = new StringBuffer();
        for (ActiveExerciseField activeExerciseField : mActiveExerciseFields) {
            retVal.append(activeExerciseField.getFieldName() + ": " + activeExerciseField.getValue());
        }
        return retVal.toString();
    }

    // No setter because this is temporary and should not be used to write data.
}
