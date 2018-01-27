package com.novakduc.forbega.qlnt.ui.config.finance;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.novakduc.forbega.qlnt.data.QlntRepository;
import com.novakduc.forbega.qlnt.data.database.Loan;
import com.novakduc.forbega.qlnt.data.database.Project;

import java.util.List;

/**
 * Created by Novak on 1/27/2018.
 */

public class ProjectFinanceConfigFragmentViewModel extends ViewModel {
    private QlntRepository mRepository;
    private LiveData<Project> mProjectLiveData;
    private LiveData<List<Loan>> mLoanListLiveData;
    private long mProjectId;

    public ProjectFinanceConfigFragmentViewModel(QlntRepository repository, long projectId) {
        mRepository = repository;
        mProjectId = projectId;
        mProjectLiveData = repository.getProject(projectId);
        mLoanListLiveData = repository.getLoanList(projectId);
    }

    public LiveData<Project> getProjectLiveData() {
        return mProjectLiveData;
    }

    public LiveData<List<Loan>> getLoanListLiveData() {
        return mLoanListLiveData;
    }
}
