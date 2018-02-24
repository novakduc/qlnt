package com.novakduc.forbega.qlnt.ui.detail;

import com.novakduc.baselibrary.SimpleFragmentActivity;
import com.novakduc.forbega.qlnt.ui.list.ProjectListFragment;

/**
 * Created by n.thanh on 10/12/2016.
 */

public class ProjectDetailActivity extends SimpleFragmentActivity {
    @Override
    protected android.support.v4.app.Fragment createFragment() {
        long activeProjectId = getIntent().getLongExtra(ProjectListFragment.ACTIVE_PROJECT_ID, -1);
        return ProjectDetailFragment.newInstance(activeProjectId);
    }
}
