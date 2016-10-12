package com.novakduc.forbega.qlnt;

import android.app.Fragment;

import com.novakduc.baselibrary.SimpleFragmentActivity;

/**
 * Created by n.thanh on 10/12/2016.
 */

public class ProjectDetailActivity extends SimpleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ProjectDetailFragment();
    }
}
