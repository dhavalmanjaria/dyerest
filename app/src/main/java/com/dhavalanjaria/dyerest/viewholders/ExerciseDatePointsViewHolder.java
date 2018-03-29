package com.dhavalanjaria.dyerest.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.models.ExerciseDatePoints;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dhaval Anjaria on 2/26/2018.
 */

public class ExerciseDatePointsViewHolder extends RecyclerView.ViewHolder{
    private TextView mExercisePeformedNameText;
    private TextView mPointsText;

    public ExerciseDatePointsViewHolder(View itemView) {
        super(itemView);

        mExercisePeformedNameText = itemView.findViewById(R.id.exercise_performed_date_text);
        mPointsText = itemView.findViewById(R.id.exercise_performed_points_text);
    }

    public void bind(ExerciseDatePoints model) {
        Date date = model.getDate();

        String formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(date);

        mExercisePeformedNameText.setText(formattedDate);
        mPointsText.setText(model.getPoints() + "");
    }

}
