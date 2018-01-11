package com.novakduc.forbega.qlnt.ui.list;

import android.app.Fragment;

import com.novakduc.baselibrary.SimpleFragmentActivity;

public class ProjectListActivity extends SimpleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ProjectListFragment();
    }
}