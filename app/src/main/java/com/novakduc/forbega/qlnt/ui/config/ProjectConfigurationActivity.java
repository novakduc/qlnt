package com.novakduc.forbega.qlnt.ui.config;

import android.app.FragmentManager;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.util.Log;

import com.novakduc.baselibrary.SimpleFragmentActivity;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.ui.ConfirmationDialogFragment;
import com.novakduc.forbega.qlnt.ui.config.base.ProjectBaseConfigFragment;
import com.novakduc.forbega.qlnt.utilities.InjectorUtils;

public class ProjectConfigurationActivity extends SimpleFragmentActivity
        implements UpdateListener, ConfirmationDialogFragment.ConfirmListener {
    public static final String TEMP_PROJECT_ID = "com.novakduc.forbega.qlnt.tempprojectId";
    private static final String LOG_TAG = ProjectConfigurationActivity.class.getSimpleName();
    private long mTempProjectId;
    private ProjectConfigActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        if (savedInstanceState != null) {
            mTempProjectId = savedInstanceState.getLong(TEMP_PROJECT_ID);
        }
        ProjectConfigViewModelFactory factory = InjectorUtils.provideProjectConfigViewModelFactory(this);

        mViewModel = ViewModelProviders.of(this, factory).get(ProjectConfigActivityViewModel.class);

        mViewModel.setProjectId(mTempProjectId);

        Log.d(LOG_TAG, "Project id: " + mTempProjectId);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected android.support.v4.app.Fragment createFragment() {
        return ProjectBaseConfigFragment.newInstance();
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getFragmentManager();
        int count = manager.getBackStackEntryCount();
        Log.i("count", String.valueOf(count));
        if (count == 0) {
            discardConfirmation(R.string.project_create_discard);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void discardConfirmation(int messageId) {
        Bundle bundle = new Bundle();
        //dialog title in bundle
        bundle.putString(ConfirmationDialogFragment.MESSAGE,
                getResources().getString(messageId));
        android.support.v4.app.DialogFragment dialogFragment = new ConfirmationDialogFragment();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getSupportFragmentManager(), "discardConfirm");
    }

    @Override
    public void updateProjectId(long projectId) {
        mTempProjectId = projectId;
        mViewModel.setProjectId(projectId);
        Log.d(LOG_TAG, "Project id from view model: " + mViewModel.getProjectId());
    }

    @Override
    public void finalCheck() {
        Log.d(LOG_TAG, "Project id before confirming: " + mTempProjectId);

        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragmentContainer,
                ProjectEditFragment.newInstance(mTempProjectId)).addToBackStack(null).commit();
    }

    @Override
    public void saveProject() {
        mViewModel.addProject();
    }

    @Override
    public void action(int result) {
        if (result == ConfirmationDialogFragment.RESULT_OK) {
            mViewModel.deleteProject(mTempProjectId);
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong(TEMP_PROJECT_ID, mTempProjectId);
        Log.d(LOG_TAG, "Save project ID: " + mTempProjectId);
        super.onSaveInstanceState(outState);
    }
}
