package com.novakduc.forbega.qlnt.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Novak on 9/25/2016.
 */

public class TabAdapter extends FragmentPagerAdapter {
    private int numberOfPage;

    public TabAdapter(FragmentManager fm, int numberOfPage) {
        super(fm);
        this.numberOfPage = numberOfPage;

    }

    @Override
    public Fragment getItem(int position) {
        /*
        if (position == 0) {
            return new ProjectListFragment();
        }
        */
        TabPosition tabPosition = TabPosition.values()[position];
        return TabFragment.getInstance(tabPosition);
    }

    @Override
    public int getCount() {
        return numberOfPage;
    }
}
