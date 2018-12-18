package com.novakduc.forbega.qlnt.ui.list;

import com.novakduc.forbega.qlnt.data.QlntRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by Nguyen Quoc Thanh on 1/11/2018.
 */

public class ProjectListFragmentViewModel extends ViewModel {
    private LiveData<List<ListViewProjectItem>> mProjects;
    private LiveData<List<LoanAmount>> mLoanAmounts;
    private QlntRepository mRepository;


    public ProjectListFragmentViewModel(final QlntRepository repository) {
        mRepository = repository;
        mProjects = repository.getProjectList();
        mLoanAmounts = repository.getAllLoanAmount();
    }

    public LiveData<List<ListViewProjectItem>> getProjects() {
        return mProjects;
    }

    public LiveData<List<LoanAmount>> getLoanAmounts() {
        return mLoanAmounts;
    }

    public void deleteProject(long projectId) {
        mRepository.deleteProject(projectId);
    }
}
