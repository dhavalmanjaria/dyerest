package com.dhavalanjaria.dyerest.viewholders;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.dhavalanjaria.dyerest.BaseActivity;
import com.dhavalanjaria.dyerest.EditExerciseActivity;
import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.models.DayExercise;
import com.dhavalanjaria.dyerest.models.Exercise;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Dhaval Anjaria on 3/4/2018.
 */

public class ListAllExercisesViewHolder extends RecyclerView.ViewHolder {

    public static final String TAG = "ListAllExercisesVH";

    private ImageButton mMenuButton;
    private TextView mExerciseName;

    public ListAllExercisesViewHolder(View itemView) {
        super(itemView);
        mExerciseName = itemView.findViewById(R.id.exercise_name_text);
        mMenuButton = itemView.findViewById(R.id.add_edit_exercise_button);
        mMenuButton.setVisibility(View.VISIBLE);
    }

    public void bind(final Exercise exercise, final DatabaseReference exerciseRef) {
        mExerciseName.setText(exercise.getName());

        mMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code smell, code repeated from ExerciseFieldViewHolder
                PopupMenu menu = new PopupMenu(itemView.getContext(), mMenuButton);
                menu.inflate(R.menu.edit_delete_popup_menu);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit_item:
                                Intent intent = EditExerciseActivity.newIntent(itemView.getContext(), exerciseRef.toString());
                                itemView.getContext().startActivity(intent);
                                return true;
                            case R.id.delete_item:
                                final DatabaseReference daysRef = BaseActivity.getRootDataReference().child("days");

                                daysRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Iterator<DataSnapshot> daySnapIter = dataSnapshot.getChildren().iterator();

                                        while (daySnapIter.hasNext()) {
                                            DataSnapshot daySnap = daySnapIter.next();

                                            Query query = daySnap.child("exercises").getRef().orderByChild("exerciseKey")
                                                    .equalTo(exerciseRef.getKey());

                                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    Log.d(TAG, "exerciseKey:" + dataSnapshot.getValue());
                                                    Log.d(TAG, "exerciseRef:" + exerciseRef.getKey());

                                                    // DataSnapshot still points to parent element, which is "exercises"
                                                    // So by iterating to the next, we get a reference to the first child
                                                    // which, because of the Query, is our only child. This way we can get
                                                    // the exact key to delete.
                                                    String dayExerciseKey;
                                                    if (dataSnapshot.getValue() != null)
                                                         dayExerciseKey = (String) dataSnapshot.getChildren()
                                                                .iterator()
                                                                .next()
                                                                .getKey();
                                                    else
                                                        return;

                                                    dataSnapshot.getRef().child(dayExerciseKey).removeValue();
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {
                                                    Log.e(TAG, databaseError.getMessage());
                                                    Log.e(TAG, databaseError.getDetails());
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Log.e(TAG, databaseError.getMessage());
                                        Log.e(TAG, databaseError.getDetails());
                                    }
                                });

                                // Also delete the actual exercise. The code to delete an exercise
                                // from a day should be tied to the ChildRemoved method of the listener
                                // However for now we're doing it manually.
                                exerciseRef.removeValue();

                                return true;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                menu.show();
            }
        });

    }
}
