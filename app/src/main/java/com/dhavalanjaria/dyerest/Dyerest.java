package com.dhavalanjaria.dyerest;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Dhaval Anjaria on 5/16/2018.
 */

public class Dyerest extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
