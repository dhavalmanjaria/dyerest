package com.dhavalanjaria.dyerest;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.dhavalanjaria.dyerest.fragments.PointsByDateFragment;
import com.dhavalanjaria.dyerest.models.ExercisePerformed;
import com.dhavalanjaria.dyerest.models.MockData;
import com.dhavalanjaria.dyerest.viewholders.ExercisePerformedViewHolder;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class PointsByDatePagerActivity extends BaseActivity {

    public static final String TAG = "PointsByDatePagerActivity";
    public static final String EXTRA_PAGER_ID = "PointsByDatePagerActivity.PagerId";

    private ViewPager mViewPager;
    private RecyclerView mExercisePerformedRecycler;

    public static Intent newIntent(Context context, int pagerId) {
        Intent intent = new Intent(context, PointsByDatePagerActivity.class);
        intent.putExtra(EXTRA_PAGER_ID, pagerId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_by_date_pager);

        mViewPager = findViewById(R.id.points_by_date_pager);

        mViewPager.setAdapter(new GetScreenshotPagerAdapter(getSupportFragmentManager()));

        // See ViewPager.setCurrentItem()
    }

    private class GetScreenshotPagerAdapter extends FragmentStatePagerAdapter {

        private List<PointsByDateFragment> mFragmentList = new ArrayList<>();
        // First get the date. Then get the list of exercises for that date and show them here.
        // For now, this uses getExercisesPerformed


        public GetScreenshotPagerAdapter(FragmentManager fm) {
            super(fm);

            for (ExercisePerformed ep: MockData.getExercisesPerformed()) {
                mFragmentList.add(PointsByDateFragment.newInstance());
            }
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

    @Override
    public Query getQuery() {
        return null;
    }

    private class GetScreenshotAdapter extends RecyclerView.Adapter<ExercisePerformedViewHolder> {

        private List<ExercisePerformed> mModel;

        public GetScreenshotAdapter() {
            mModel = MockData.getExercisesPerformed();
        }

        @Override
        public ExercisePerformedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = getLayoutInflater().inflate(R.layout.exercise_performed_item, parent, false);

            return new ExercisePerformedViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ExercisePerformedViewHolder holder, int position) {
            holder.bind(mModel.get(position));
        }

        @Override
        public int getItemCount() {
            return mModel.size();
        }
    }
}
