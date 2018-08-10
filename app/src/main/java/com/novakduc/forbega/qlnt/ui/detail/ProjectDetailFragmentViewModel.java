package com.novakduc.forbega.qlnt.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.novakduc.forbega.qlnt.data.ProjectRepo;

public class ProjectDetailFragmentViewModel extends ViewModel {
    private ProjectRepo mProjectRepo;
    private LiveData<ProjectNameQuery> mProjectName;

    public ProjectDetailFragmentViewModel(ProjectRepo projectRepo) {
        mProjectRepo = projectRepo;
        mProjectName = mProjectRepo.getProjectName();
    }

    public LiveData<ProjectNameQuery> getProjectName() {
        return mProjectName;
    }
}
