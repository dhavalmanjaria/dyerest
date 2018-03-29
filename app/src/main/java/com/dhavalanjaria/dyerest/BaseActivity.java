package com.dhavalanjaria.dyerest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by Dhaval Anjaria on 2/6/2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public static String getUserId() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    /**
     * This function returns the reference for where the data for the "DYEREST" app starts. This tree
     * is separate from the users tree and any other tree that might exist.
     * @return DatabaseReference -- The root of the app data tree.
     */
    public static DatabaseReference getRootDataReference() {
        // Not sure what the consequences of all of these functions being static are.
        return FirebaseDatabase.getInstance().getReference().child("dyerest").child(getUserId());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.user_menu, menu);
        getMenuInflater().inflate(R.menu.view_histroy_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.sign_out_menu_item:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, SignInActivity.class));
                return true;
            case R.id.view_history_menu_item:
                intent = ExerciseHistoryActivity.newIntent(this);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
