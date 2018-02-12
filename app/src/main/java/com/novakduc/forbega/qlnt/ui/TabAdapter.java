package com.novakduc.forbega.qlnt.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.novakduc.forbega.qlnt.ui.detail.item.ItemListFragment;

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
        switch (position) {
            case 0:
                return new ItemListFragment();
            case 1:
                //return new ProjectDetailFragment();
        }
        TabPosition tabPosition = TabPosition.values()[position];
        return TabFragment.getInstance(tabPosition);
    }

    @Override
    public int getCount() {
        return numberOfPage;
    }
}
