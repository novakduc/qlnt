package com.novakduc.forbega.qlnt;

import android.app.Fragment;

import com.novakduc.baselibrary.SimpleFragmentActivity;

public class ProjectListActivity extends SimpleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ProjectListFragment();
    }
}