package com.novakduc.forbega.qlnt.ui.config;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.novakduc.forbega.qlnt.data.QlntRepository;

/**
 * Created by Nguyen Quoc Thanh on 1/30/2018.
 */

public class ProjectConfigActivityViewModel extends ViewModel {
    private static final String LOG_TAG = ProjectConfigActivityViewModel.class.getSimpleName();
    private QlntRepository mRepository;
    private long mProjectId;
    private boolean isConfirmed;

    public ProjectConfigActivityViewModel(QlntRepository repository) {
        mRepository = repository;
        isConfirmed = false;
    }

    public long getProjectId() {
        return mProjectId;
    }

    public void setProjectId(long projectId) {
        mProjectId = projectId;
        Log.d(LOG_TAG, "change project id: " + mProjectId);
    }

    public void addProject() {
        isConfirmed = true;
    }

    @Override
    protected void onCleared() {
        Log.d(LOG_TAG, "Project config view model cleared. Confirm state: " + isConfirmed);
        if (!isConfirmed) {
            mRepository.deleteProject(mProjectId);
            Log.d(LOG_TAG, "delete temp project");
        }
        super.onCleared();
    }

    public void deleteProject(long tempProjectId) {
        mRepository.deleteProject(tempProjectId);
    }
}
