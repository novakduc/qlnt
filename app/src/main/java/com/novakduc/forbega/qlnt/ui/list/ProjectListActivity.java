package com.novakduc.forbega.qlnt.ui.list;

import com.novakduc.baselibrary.SimpleFragmentActivity;

public class ProjectListActivity extends SimpleFragmentActivity {

    @Override
    protected android.support.v4.app.Fragment createFragment() {
        return new ProjectListFragment();
    }
}