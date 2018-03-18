package com.dhavalanjaria.dyerest.models;

import java.util.ArrayList;

/**
 * Created by Dhaval Anjaria on 2/15/2018.
 */

/**
 * Class is temporary and should not be used. This will be replaced by a Firebase JSON Schema
 */
@Deprecated
public class CardioToDeleteExercise extends ToDeleteExercise {

    private ArrayList<ActiveExerciseField> mActiveExerciseFields;

    public CardioToDeleteExercise() {
        mActiveExerciseFields = new ArrayList<>();
        mActiveExerciseFields.add(new ActiveExerciseField("duration", 0));
        mActiveExerciseFields.add(new ActiveExerciseField("distance", 0));
        mActiveExerciseFields.add(new ActiveExerciseField("intensity", 0));
        mActiveExerciseFields.add(new ActiveExerciseField("incline", 0));
    }

    @Override
    public String toString() {
        StringBuffer retVal = new StringBuffer();
        for (ActiveExerciseField activeExerciseField : mActiveExerciseFields) {
            retVal.append(activeExerciseField.getFieldName() + ": " + activeExerciseField.getValue());
        }
        return retVal.toString();
    }

    public ArrayList<ActiveExerciseField> getActiveExerciseFields() {
        return mActiveExerciseFields;
    }

    // No setter because this is temporary and should not be used to write data.
}
