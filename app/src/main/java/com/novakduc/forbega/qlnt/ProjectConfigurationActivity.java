package com.novakduc.forbega.qlnt;

import android.app.Fragment;

import com.novakduc.baselibrary.SimpleFragmentActivity;

/**
 * Created by n.thanh on 9/29/2016.
 */

public class ProjectConfigurationActivity extends SimpleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ProjectConfigurationFragment();
    }
}
