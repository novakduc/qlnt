package com.novakduc.forbega.qlnt;

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
        TabPostion tabPostion = TabPostion.values()[position];
        return TabFragment.getInstance(tabPostion);
    }

    @Override
    public int getCount() {
        return numberOfPage;
    }
}
