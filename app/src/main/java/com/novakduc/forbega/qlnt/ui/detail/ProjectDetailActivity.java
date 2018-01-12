package com.novakduc.forbega.qlnt.ui.detail;

import com.novakduc.baselibrary.SimpleFragmentActivity;

/**
 * Created by n.thanh on 10/12/2016.
 */

public class ProjectDetailActivity extends SimpleFragmentActivity {
    @Override
    protected android.support.v4.app.Fragment createFragment() {
        return new ProjectDetailFragment();
    }
}
