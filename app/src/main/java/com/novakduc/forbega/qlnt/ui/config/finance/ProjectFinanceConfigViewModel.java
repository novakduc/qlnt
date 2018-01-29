package com.novakduc.forbega.qlnt.ui.config.finance;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.novakduc.forbega.qlnt.data.ProjectRepo;
import com.novakduc.forbega.qlnt.data.database.Loan;
import com.novakduc.forbega.qlnt.data.database.Project;

import java.util.List;

/**
 * Created by Novak on 1/27/2018.
 */

public class ProjectFinanceConfigViewModel extends ViewModel {
    private ProjectRepo mProjectRepo;
    private LiveData<Project> mProjectLiveData;
    private MutableLiveData<List<Loan>> mLoanListLiveData;

    public ProjectFinanceConfigViewModel(ProjectRepo projectRepo) {
        mProjectRepo = projectRepo;
        mProjectLiveData = projectRepo.getProject();
    }

    public LiveData<Project> getProjectLiveData() {
        return mProjectLiveData;
    }

    public LiveData<List<Loan>> getLoanListLiveData() {
        mLoanListLiveData = mProjectRepo.getLoanList();
        return mLoanListLiveData;
    }

    public void deleteLoan(long loanId) {
        mProjectRepo.deleteLoan(loanId);
    }

    public void updateProject(Project project) {
        mProjectRepo.updateProject(project);
    }
}
