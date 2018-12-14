package com.novakduc.forbega.qlnt.ui.config.finance;

import com.novakduc.forbega.qlnt.data.ProjectRepo;
import com.novakduc.forbega.qlnt.data.database.Loan;
import com.novakduc.forbega.qlnt.data.database.Project;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by Novak on 1/27/2018.
 */

public class ProjectFinanceConfigViewModel extends ViewModel {
    private ProjectRepo mProjectRepo;
    private LiveData<Project> mProjectLiveData;
    private LiveData<List<Loan>> mLoanListLiveData;

    public ProjectFinanceConfigViewModel(ProjectRepo projectRepo) {
        mProjectRepo = projectRepo;
        mProjectLiveData = projectRepo.getProject();
        mLoanListLiveData = projectRepo.getLoanList();
    }

    public LiveData<Project> getProjectLiveData() {
        return mProjectLiveData;
    }

    public LiveData<List<Loan>> getLoanListLiveData() {
        return mLoanListLiveData;
    }

    public void deleteLoan(long loanId) {
        mProjectRepo.deleteLoan(loanId);
    }

    public void updateProject(Project project) {
        mProjectRepo.updateProject(project);
    }
}
