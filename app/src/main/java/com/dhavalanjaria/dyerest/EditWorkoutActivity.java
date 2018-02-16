package com.dhavalanjaria.dyerest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

@Deprecated
public class EditWorkoutActivity extends AppCompatActivity {

    private Button mAddDayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);

        mAddDayButton = (Button) findViewById(R.id.add_day_button);
        mAddDayButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditWorkoutActivity.this, ExerciseListActivity.class));
            }
        });
    }
}
