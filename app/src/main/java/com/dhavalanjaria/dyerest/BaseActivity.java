package com.dhavalanjaria.dyerest;

import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by Dhaval Anjaria on 2/6/2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public String getUserId() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    /**
     * This function returns the reference for where the data for the "DYEREST" app starts. This tree
     * is separate from the users tree and any other tree that might exist.
     * @return DatabaseReference -- The root of the app data tree.
     */
    protected static DatabaseReference getRootDataReference() {
        return FirebaseDatabase.getInstance().getReference().child("dyerest");
    }

    /**
     * This function returns a reference to just the users tree. This is separate from the app data
     * tree.
     * @return DatabaseReference -- Reference to the "users" tree.
     */
    protected static DatabaseReference getUsersReference() {
        return FirebaseDatabase.getInstance().getReference().child("users");
    }

    public abstract Query getQuery();
}
