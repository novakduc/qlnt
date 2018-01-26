package com.novakduc.forbega.qlnt.ui.config.base;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.novakduc.forbega.qlnt.data.QlntRepository;
import com.novakduc.forbega.qlnt.data.database.Project;

/**
 * Created by Nguyen Quoc Thanh on 1/26/2018.
 */

public class ProjectBaseFragmentViewModel extends ViewModel {
    private QlntRepository mRepository;
    private LiveData<Project> mProjectLiveData;
    private boolean isConfirmed;
    // TODO: 1/26/2018 clear database

    public ProjectBaseFragmentViewModel(QlntRepository repository) {
        mRepository = repository;
        mProjectLiveData = repository.createTempProject();
        isConfirmed = false;
    }

    public LiveData<Project> getTempProject() {
        return mProjectLiveData;
    }

    public void updateProject(Project project) {
        mRepository.updateProject(project);
        isConfirmed = true;
    }

    @Override
    protected void onCleared() {
        if (!isConfirmed)
            mRepository.deleteProject(mProjectLiveData.getValue().getId());
        super.onCleared();
    }
}
