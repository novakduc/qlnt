package com.novakduc.forbega.qlnt;

import android.app.Fragment;

import com.novakduc.baselibrary.SimpleFragmentActivity;

public class ProjectConfigurationActivity extends SimpleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ProjectConfiguraitonFragment();
    }
}
