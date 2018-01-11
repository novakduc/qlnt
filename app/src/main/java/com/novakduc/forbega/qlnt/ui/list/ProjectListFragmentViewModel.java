package com.novakduc.forbega.qlnt.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.novakduc.forbega.qlnt.data.QlntRepository;
import com.novakduc.forbega.qlnt.data.database.Project;

/**
 * Created by Nguyen Quoc Thanh on 1/11/2018.
 */

public class ProjectListFragmentViewModel extends ViewModel {
    private LiveData<Project[]> mProjects;

    public ProjectListFragmentViewModel(QlntRepository repository) {
        mProjects = repository.getProjectList();
    }

    public LiveData<Project[]> getProjects() {
        return mProjects;
    }
}
