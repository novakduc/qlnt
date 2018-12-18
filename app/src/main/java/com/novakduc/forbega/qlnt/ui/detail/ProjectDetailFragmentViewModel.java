package com.novakduc.forbega.qlnt.ui.detail;

import com.novakduc.forbega.qlnt.data.ProjectRepo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

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
