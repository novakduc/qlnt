package com.novakduc.forbega.qlnt.ui.config;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.novakduc.baselibrary.SimpleFragmentActivity;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.ui.ConfirmationDialogFragment;

/**
 * Created by Nguyen Quoc Thanh on 1/31/2018.
 */

public class EditProjectActivity extends SimpleFragmentActivity
        implements UpdateListener, ConfirmationDialogFragment.ConfirmListener {

    @Override
    public void discardConfirmation(int messageId) {
        Bundle bundle = new Bundle();
        //dialog title in bundle
        bundle.putString(ConfirmationDialogFragment.MESSAGE,
                getResources().getString(R.string.announce_project_not_save));
        android.support.v4.app.DialogFragment dialogFragment = new ConfirmationDialogFragment();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getSupportFragmentManager(), "discardConfirm");
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
        discardConfirmation(R.string.announce_project_not_save);
    }

    @Override
    protected Fragment createFragment() {
        long projectId = getIntent().getLongExtra(ProjectEditFragment.TEMP_PROJECT_ID, -1);
        return ProjectEditFragment.newInstance(projectId);
    }

    @Override
    public void action(int result) {
        if (result == ConfirmationDialogFragment.RESULT_OK) {
            //user confirm to discard project creation.
            finish();
        }
    }
}
