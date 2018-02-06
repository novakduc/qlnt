package com.novakduc.forbega.qlnt.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.novakduc.forbega.qlnt.data.QlntRepository;
import com.novakduc.forbega.qlnt.data.database.ListViewProjectItem;
import com.novakduc.forbega.qlnt.data.database.LoanAmount;

import java.util.List;

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
//        //Test
//        //Test should be removed
//        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//
//                    // Pretend this is the network loading data
//                    Project pretendProject = new Project();
//                    pretendProject.setName("sadjlkgj");
//                    pretendProject.setAddress("sdlfjdfgdf");
//                    pretendProject.setInvestmentAmount(2342905);
//                    repository.addProject(pretendProject);
//                    Thread.sleep(4000);
//                    mProjects = repository.getProjectList();
//
//                    pretendProject = new Project();
//                    pretendProject.setName("sadjagfdsdlkgj");
//                    pretendProject.setAddress("sdleagdsffdgfjdfgdf");
//                    pretendProject.setInvestmentAmount(23905);
//                    repository.addProject(pretendProject);
//
//                    Thread.sleep(2000);
//                    mProjects = repository.getProjectList();
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
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

    public void copyProject(long projectId) {
        mRepository.copyProject(projectId);
    }
}
