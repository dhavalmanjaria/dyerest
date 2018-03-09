package com.dhavalanjaria.dyerest.viewholders;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.dhavalanjaria.dyerest.EditExerciseActivity;
import com.dhavalanjaria.dyerest.OnDialogCompletedListener;
import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.fragments.EditDialogFragment;
import com.dhavalanjaria.dyerest.models.ExerciseField;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

/**
 * Created by Dhaval Anjaria on 2/23/2018.
 */

public class ExerciseFieldViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

    private TextView mExerciseFieldName;
    private ImageButton mMenuButton;
    private List<ExerciseField> mExerciseFieldList;
    private RecyclerView.Adapter mAdapter;

    public ExerciseFieldViewHolder(View itemView, List<ExerciseField> exerciseFields, RecyclerView.Adapter adapter) {
        super(itemView);
        this.mExerciseFieldList = exerciseFields;
        this.mAdapter = adapter;

        mExerciseFieldName = itemView.findViewById(R.id.exercise_field_name_text);
        mMenuButton = itemView.findViewById(R.id.exercise_field_menu_button);

        itemView.setOnCreateContextMenuListener(this);
    }

    public void bind(final ExerciseField exerciseField) {

        mExerciseFieldName.setText(exerciseField.getName());
        mMenuButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(itemView.getContext(), mMenuButton);
                menu.inflate(R.menu.edit_delete_popup_menu);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit_item:
                                FragmentManager manager = ((EditExerciseActivity) itemView.getContext())
                                        .getSupportFragmentManager();
                                EditDialogFragment fragment = EditDialogFragment.newInstance("Edit Field",
                                        new OnDialogCompletedListener() {
                                            @Override
                                            public void onDialogComplete(String text) {
                                                exerciseField.setName(text);
                                                mExerciseFieldName.setText(text);
                                            }
                                        });
                                fragment.show(manager, "ExerciseFieldVH");
                                return true;
                            case R.id.delete_item:
                                // the itemView is set to INVISIBLE so that an item is not deleted
                                // twice. We also remove it from the dataset to make extra sure.
                                itemView.setVisibility(View.INVISIBLE);
                                mExerciseFieldList.remove(getLayoutPosition());
                                mAdapter.notifyItemRemoved(getAdapterPosition());
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

    @Deprecated
    public void bind(final ExerciseField exerciseField, DatabaseReference exerciseFieldRef, DatabaseReference exerciseRef) {
        mExerciseFieldName.setText(exerciseField.getName());
        mMenuButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(itemView.getContext(), mMenuButton);
                menu.inflate(R.menu.edit_delete_popup_menu);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit_item:

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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

    }
}
