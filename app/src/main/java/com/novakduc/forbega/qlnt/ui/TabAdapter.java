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
    private long mActivieProjectId;

    public TabAdapter(FragmentManager fm, int numberOfPage, long activeProjectId) {
        super(fm);
        this.numberOfPage = numberOfPage;
        this.mActivieProjectId = activeProjectId;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ItemListFragment.getInstance(mActivieProjectId);
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
