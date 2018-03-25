package com.dhavalanjaria.dyerest.points;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dhaval Anjaria on 3/22/2018.
 */

public class ExercisePointsCache {

    private HashMap<String, Integer> cache;

    public ExercisePointsCache(HashMap<String, Integer> cache) {
        this.cache = cache;
    }

    public HashMap<String, Integer> getCache() {
        return cache;
    }

    public void setCache(HashMap<String, Integer> cache) {
        this.cache = cache;
    }
}
