package com.dhavalanjaria.dyerest.fragments;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.dhavalanjaria.dyerest.R;

@Deprecated
public class ExerciseGuideFragment extends Fragment {

    private static final String KEY_EXERCISE_ID = "ExerciseGuideFragment.ExerciseId";

    private ImageView mExerciseGuideImage;
    private Button mEditImageButton;


    public static ExerciseGuideFragment newInstance(String exerciseId) {
        Bundle args = new Bundle();

        ExerciseGuideFragment fragment = new ExerciseGuideFragment();
        args.putString(KEY_EXERCISE_ID, exerciseId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_exercise_guide, container, true);

        return v;
    }
}
