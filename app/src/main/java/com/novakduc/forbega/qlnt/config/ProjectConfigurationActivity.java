package com.novakduc.forbega.qlnt.config;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.util.Log;

import com.novakduc.baselibrary.SimpleFragmentActivity;
import com.novakduc.forbega.qlnt.config.base.ProjectBaseConfigFragment;
import com.novakduc.forbega.qlnt.model.Project;

public class ProjectConfigurationActivity extends SimpleFragmentActivity
        implements ProjectBaseConfigFragment.DiscardListener, ConfirmationDialogFragment.ConfirmListener {
    private Project mTempProject;

    @Override
    protected Fragment createFragment() {
        mTempProject = (Project) getIntent()
                .getSerializableExtra(ProjectBaseConfigFragment.TEMP_PROJECT);
        return ProjectBaseConfigFragment.newInstance(mTempProject);
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getFragmentManager();
        int count = manager.getBackStackEntryCount();
        Log.i("count", String.valueOf(count));
        if (count == 0) {
            discardConfirmation();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void discardConfirmation() {
        DialogFragment dialogFragment = new ConfirmationDialogFragment();
        dialogFragment.show(getFragmentManager(), "discardConfirm");
    }

    @Override
    public void discardDialog(int result) {
        if (result == ConfirmationDialogFragment.RESULT_OK) {
            //user confirm to discard project creation.
            super.onBackPressed();
        }
    }
}
