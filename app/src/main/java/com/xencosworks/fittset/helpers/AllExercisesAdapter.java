package com.xencosworks.fittset.helpers;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xencosworks.fittset.DetailsFragment;
import com.xencosworks.fittset.MuscleDaysFragment;
import com.xencosworks.fittset.R;

public class AllExercisesAdapter extends FragmentPagerAdapter {
    private  Context mContext;

    public AllExercisesAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new MuscleDaysFragment();
        } else {
            return new DetailsFragment();
        }
    }
    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        /*if (position == 0) {
            return mContext.getString(R.string.fragment_muscle_days);
        } else {
            return mContext.getString(R.string.fragment_details);
        }*/
        return null;
    }



}
