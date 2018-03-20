package com.dhavalanjaria.dyerest.viewholders;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.fragments.ActiveExerciseFragment;
import com.dhavalanjaria.dyerest.models.ActiveExerciseField;
import com.dhavalanjaria.dyerest.models.Exercise;
import com.dhavalanjaria.dyerest.models.ExerciseField;
import com.dhavalanjaria.dyerest.models.ExercisePerformed;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by Dhaval Anjaria on 2/15/2018.
 */

public class ExerciseDetailViewHolder extends RecyclerView.ViewHolder {

    public static final String TAG = "ExerciseDetailVH";

    private TextView mExerciseFieldText;
    private EditText mExerciseValueEdit;
    private DatabaseReference mExercisePerformedReference;
    List<ActiveExerciseField> mActiveExerciseFields;

    public ExerciseDetailViewHolder(View itemView, List<ActiveExerciseField> exerciseFieldList) {
        super(itemView);


        mExerciseFieldText = (TextView) itemView.findViewById(R.id.exercise_detail_text);
        mExerciseValueEdit = (EditText) itemView.findViewById(R.id.exercise_detail_edit);

        this.mActiveExerciseFields = exerciseFieldList;

    }

    /**
     * This function binds one field of an exercise to one ViewHolder in the RecyclerView. Each
     * ViewHolder will contain the name of one field and it's target value
     * @param activeExerciseField ActiveExerciseField Holds the field name and value for that field, eg: poundage: 12
     *
     */
    public void bind(final ActiveExerciseField activeExerciseField) {
        mExerciseFieldText.setText(activeExerciseField.getFieldName());
        mExerciseValueEdit.setHint("" + activeExerciseField.getValue());

        mExerciseValueEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {
                    Integer newValue = Integer.parseInt(s.toString());

                    mActiveExerciseFields.get(getAdapterPosition()).setValue(newValue);
                }
                catch (NumberFormatException ex) {
                    Toast.makeText(itemView.getContext(), "Illegal characters entered", Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });
    }
}
