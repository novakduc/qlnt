package com.novakduc.forbega.qlnt.ui.list;

import com.novakduc.baselibrary.SimpleFragmentActivity;
import com.novakduc.forbega.qlnt.ui.ConfirmationDialogFragment;

public class ProjectListActivity extends SimpleFragmentActivity
        implements ConfirmationDialogFragment.ConfirmListener {

    @Override
    protected android.support.v4.app.Fragment createFragment() {
        return new ProjectListFragment();
    }

    @Override
    public void discardDialog(int result) {

    }
}