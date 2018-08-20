package com.novakduc.forbega.qlnt.ui.detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.novakduc.forbega.qlnt.ui.detail.finance.FinanceFragment;
import com.novakduc.forbega.qlnt.ui.detail.report.ReportFragment;
import com.novakduc.forbega.qlnt.ui.detail.room.RoomListFragment;

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
                return RoomListFragment.getInstance(mActivieProjectId);
            case 1:
                return FinanceFragment.getInstance(mActivieProjectId);
            case 2:
                return ReportFragment.getInstance(mActivieProjectId);
            default:
                return FinanceFragment.getInstance(mActivieProjectId);
        }
    }

    @Override
    public int getCount() {
        return numberOfPage;
    }
}
