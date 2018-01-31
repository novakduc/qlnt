package com.novakduc.forbega.qlnt.ui.config;

import android.support.v4.app.Fragment;

import com.novakduc.baselibrary.SimpleFragmentActivity;

/**
 * Created by Nguyen Quoc Thanh on 1/31/2018.
 */

public class EditProjectActivity extends SimpleFragmentActivity implements UpdateListener {

    @Override
    public void discardConfirmation(int messageId) {

    }

    @Override
    public void updateProjectId(long projectId) {

    }

    @Override
    public void finalCheck() {

    }

    @Override
    public void saveProject() {

    }

    @Override
    protected Fragment createFragment() {
        long projectId = getIntent().getLongExtra(ProjectEditFragment.TEMP_PROJECT_ID, -1);
        return ProjectEditFragment.newInstance(projectId);
    }
}
