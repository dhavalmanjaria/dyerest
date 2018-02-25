package com.dhavalanjaria.dyerest.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.models.ExerciseField;
import com.dhavalanjaria.dyerest.models.ExerciseMap;

/**
 * Created by Dhaval Anjaria on 2/23/2018.
 */

public class ExerciseFieldViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

    private TextView mExerciseFieldName;
    private TextView mPopupMenuText;
    private Menu mPopupMenu;
    private Button mDeleteButton;

    public ExerciseFieldViewHolder(View itemView) {
        super(itemView);

        mExerciseFieldName = itemView.findViewById(R.id.exercise_field_name_text);
        mPopupMenuText = itemView.findViewById(R.id.options_menu_text);
        mDeleteButton = itemView.findViewById(R.id.exercise_field_delete_button);

        itemView.setOnCreateContextMenuListener(this);
    }

    // This method is for use with Firebase.
    public void bind(ExerciseField model, String fieldKey) {
        //TODO: Bind to fields
    }

    public void bind(final ExerciseField exerciseField) {
        mExerciseFieldName.setText(exerciseField.getName());
//        mPopupMenuText.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                PopupMenu menu = new PopupMenu(itemView.getContext(), mPopupMenuText);
//                menu.inflate(R.menu.edit_delete_popup_menu);
//
//                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.edit_item:
//                                // Handle menu edit click
//                                return true;
//                            case R.id.delete_item:
//                                return true;
//                            default:
//                                break;
//                        }
//                        return false;
//                    }
//                });
//                menu.show();
//            }
//        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

    }
}
