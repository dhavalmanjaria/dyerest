package com.dhavalanjaria.dyerest.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dhaval Anjaria on 2/26/2018.
 */

//  Class used for Points History. Name could be worked on.
public class ExercisePoints {

    public String exerciseName;
    public int points;

    public ExercisePoints() {
    }

    public ExercisePoints(String exerciseName, int points) {
        this.exerciseName = exerciseName;
        this.points = points;
    }
    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> retval = new HashMap<>();
        retval.put("exerciseName", getExerciseName());
        retval.put("points", getPoints());

        return retval;
    }
}
