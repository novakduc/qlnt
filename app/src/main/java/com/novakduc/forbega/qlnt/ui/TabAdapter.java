package com.novakduc.forbega.qlnt.ui;

/**
 * Created by Novak on 9/25/2016.
 */

public class TabAdapter extends android.support.v13.app.FragmentPagerAdapter {
    private int numberOfPage;

    public TabAdapter(android.app.FragmentManager fm, int numberOfPage) {
        super(fm);
        this.numberOfPage = numberOfPage;

    }

    @Override
    public android.app.Fragment getItem(int position) {
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