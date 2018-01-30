package com.novakduc.forbega.qlnt.ui.config;

import android.arch.lifecycle.ViewModel;

import com.novakduc.forbega.qlnt.data.QlntRepository;

/**
 * Created by Nguyen Quoc Thanh on 1/30/2018.
 */

public class ProjectConfigActivityViewModel extends ViewModel {
    private QlntRepository mRepository;
    private long mProjectId;
    private boolean isConfirmed;

    public ProjectConfigActivityViewModel(QlntRepository repository) {
        mRepository = repository;
        isConfirmed = false;
    }

    public void setProjectId(long projectId) {
        mProjectId = projectId;
    }

    public void addProject() {
        isConfirmed = true;
    }

    @Override
    protected void onCleared() {
        if (!isConfirmed) {
            mRepository.deleteProject(mProjectId);
        }
        super.onCleared();
    }
}
