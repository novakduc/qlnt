package com.novakduc.forbega.qlnt.config;

import android.app.Fragment;

import com.novakduc.baselibrary.SimpleFragmentActivity;
import com.novakduc.forbega.qlnt.config.base.ProjectBaseConfigFragment;
import com.novakduc.forbega.qlnt.model.Project;

public class ProjectConfigurationActivity extends SimpleFragmentActivity {
    private Project mTempProject;

    @Override
    protected Fragment createFragment() {
        mTempProject = (Project) getIntent()
                .getSerializableExtra(ProjectBaseConfigFragment.TEMP_PROJECT);
        return ProjectBaseConfigFragment.newInstance(mTempProject);
    }
}
