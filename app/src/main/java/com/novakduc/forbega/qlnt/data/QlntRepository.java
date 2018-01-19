package com.novakduc.forbega.qlnt.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.novakduc.baselibrary.AppExecutors;
import com.novakduc.forbega.qlnt.data.database.AppDao;
import com.novakduc.forbega.qlnt.data.database.Project;

import java.util.List;

/**
 * Created by n.thanh on 9/20/2016.
 */
public class QlntRepository {
    private static final String LOG_TAG = QlntRepository.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static QlntRepository sInstance;
    private AppDao mAppDao;
    private AppExecutors mExecutors;

    private QlntRepository(AppDao appDao, AppExecutors executors) {
        this.mAppDao = appDao;
        this.mExecutors = executors;

    }

    public static synchronized QlntRepository getInstance(AppDao appDao, AppExecutors executors) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new QlntRepository(appDao, executors);
                }
            }
        }
        return sInstance;
    }

    public void addProject(Project project) {
        mAppDao.insert(project);
    }

    public LiveData<Project> getProject(Long projectId) {
        return mAppDao.getLiveDataProject(projectId);
    }

    public LiveData<List<Project>> getProjectList() {
        return mAppDao.getAllProjects();
    }

    public void deleteProject(Project project) {
        mAppDao.removeProject(project);
    }

    public void deleteProject(final long projectId) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Project project = mAppDao.getProject(projectId);
                mAppDao.removeProject(project);
            }
        });
    }

    public void copyProject(final long projectId) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Project project = (Project) mAppDao.getProject(projectId).clone();
                    mAppDao.insert(project);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                    Log.e(LOG_TAG, e.getLocalizedMessage());
                }
            }
        });
    }
}
