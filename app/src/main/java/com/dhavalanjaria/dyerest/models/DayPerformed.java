package com.dhavalanjaria.dyerest.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dhaval Anjaria on 2/26/2018.
 */

//  Class used for Points History. Name could be worked on.
public class DayPerformed {
    public Date datePerformed;
    public String dayKey;
    public int points;

    public DayPerformed() {
    }

    public DayPerformed(Date datePerformed, String dayKey, int points) {
        this.datePerformed = datePerformed;
        this.dayKey = dayKey;
        this.points = points;
    }

    public Date getDatePerformed() {
        return datePerformed;
    }

    public void setDatePerformed(Date datePerformed) {
        this.datePerformed = datePerformed;
    }

    public String getDayKey() {
        return dayKey;
    }

    public void setDayKey(String dayKey) {
        this.dayKey = dayKey;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> retval = new HashMap<>();
        retval.put("datePerformed", getDatePerformed());
        retval.put("dayKey", getDayKey());
        retval.put("points", getPoints());

        return retval;
    }
}
