package com.dhavalanjaria.dyerest.viewholders;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dhavalanjaria.dyerest.PointsByDatePagerActivity;
import com.dhavalanjaria.dyerest.R;
import com.dhavalanjaria.dyerest.models.DayPerformed;

import java.text.SimpleDateFormat;

/**
 * Created by Dhaval Anjaria on 2/26/2018.
 */

public class DayPerformedViewHolder extends RecyclerView.ViewHolder {

    private TextView mDatePerformedText;
    private TextView mPointsText;
    private Button mViewButton;


    public DayPerformedViewHolder(View itemView) {
        super(itemView);

        mDatePerformedText = itemView.findViewById(R.id.exercise_date_text);
        mPointsText = itemView.findViewById(R.id.exercise_points_text);
        mViewButton = itemView.findViewById(R.id.view_exercise_points_button);

    }


    // Not for Firebase use
    public void bind(DayPerformed dayPerformed) {

        String formattedDate = new SimpleDateFormat("dd/MM/yyyy")
                .format(dayPerformed.getDatePerformed());

        mDatePerformedText.setText(formattedDate);
        mPointsText.setText(dayPerformed.getPoints() + "");

        mViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace getAdapterPosition, perhaps.
                Intent intent = PointsByDatePagerActivity.newIntent(itemView.getContext(),
                        getAdapterPosition());
                itemView.getContext().startActivity(intent);
            }
        });
    }
}
