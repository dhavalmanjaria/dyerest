package com.dhavalanjaria.dyerest.viewholders;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.models.Exercise;

/**
 * Created by Dhaval Anjaria on 3/4/2018.
 */

public class ListAllExercisesViewHolder extends ExerciseListViewHolder {

    private ImageButton mMenuButton;

    public ListAllExercisesViewHolder(View itemView) {
        super(itemView);
        mMenuButton = itemView.findViewById(R.id.add_edit_exercise_button);
        mMenuButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void bind(Exercise exercise, String exerciseKey) {
        super.bind(exercise, exerciseKey);
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
                                // Handle menu edit click
                                return true;
                            case R.id.delete_item:
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
