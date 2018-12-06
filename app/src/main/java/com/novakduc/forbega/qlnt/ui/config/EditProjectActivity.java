package com.novakduc.forbega.qlnt.ui.config;

import android.support.v4.app.Fragment;

import com.novakduc.baselibrary.SimpleFragmentActivity;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.ui.ConfirmationDialogFragment;

/**
 * Created by Nguyen Quoc Thanh on 1/31/2018.
 */

public class EditProjectActivity extends SimpleFragmentActivity
        implements UpdateListener, ConfirmationDialogFragment.ConfirmListener {

    public static final String DISCARD_SAVING_PROJECT = EditProjectActivity.class.getName() + "discardSaving";

    @Override
    public void discardConfirmation(int messageId, String purposeKey) {
        ConfirmationDialogFragment.showDialog(getString(messageId), purposeKey, getSupportFragmentManager());
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
    public void onBackPressed() {
        discardConfirmation(R.string.announce_project_not_save, DISCARD_SAVING_PROJECT);
    }

    @Override
    protected Fragment createFragment() {
        long projectId = getIntent().getLongExtra(ProjectEditFragment.TEMP_PROJECT_ID, -1);
        return ProjectEditFragment.newInstance(projectId);
    }

    @Override
    public void action(int result, String purposeKey) {
        if (result == ConfirmationDialogFragment.RESULT_OK) {
            if (purposeKey == DISCARD_SAVING_PROJECT)
                //user confirm to discard project creation.
                finish();
        }
    }
}
