package com.dhavalanjaria.dyerest;

import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Dhaval Anjaria on 2/6/2018.
 */

public class BaseActivity extends AppCompatActivity {

    public String getUserId() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
