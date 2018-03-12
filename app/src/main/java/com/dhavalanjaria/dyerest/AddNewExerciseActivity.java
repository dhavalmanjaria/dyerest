package com.dhavalanjaria.dyerest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dhavalanjaria.dyerest.fragments.EditDialogFragment;
import com.dhavalanjaria.dyerest.models.Exercise;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AddNewExerciseActivity extends BaseActivity {

    public static final String TAG = "AddNewExerciseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_exercises);

        FragmentManager manager = getSupportFragmentManager();
        EditDialogFragment fragment = EditDialogFragment.newInstance("New Exercise", new OnDialogCompletedListener() {
            @Override
            public void onDialogComplete(String text) {
                DatabaseReference exerciseRef = getRootDataReference().child("exercises").push().getRef();
                exerciseRef.child("name").setValue(text);

                Toast.makeText(AddNewExerciseActivity.this, "New exercise created", Toast.LENGTH_SHORT)
                        .show();
                Intent intent = EditExerciseActivity.newIntent(AddNewExerciseActivity.this,
                        exerciseRef.toString());
                Log.d(TAG, exerciseRef.toString());
                startActivity(intent);
            }
        });

        fragment.show(manager, TAG);
        fragment.setCancelListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AddNewExerciseActivity.class);
        // Makes sure this activity doesn't stay alive in the backstack.
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        return intent;
    }

    @Override
    public Query getQuery() {
        return null;
    }
}
