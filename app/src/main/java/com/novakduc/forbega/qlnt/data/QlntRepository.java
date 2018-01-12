package com.novakduc.forbega.qlnt.data;

import android.arch.lifecycle.LiveData;

import com.novakduc.baselibrary.AppExecutors;
import com.novakduc.forbega.qlnt.data.database.AppDao;
import com.novakduc.forbega.qlnt.data.database.Project;

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
                if (appDao == null) {
                    sInstance = new QlntRepository(appDao, executors);
                }
            }
        }
        return sInstance;
    }

    public void removeProject(Project project) {
        mAppDao.removeProject(project);
    }

    public void addProject(Project project) {
        mAppDao.insert(project);
    }

    public LiveData<Project> getProject(Long projectId) {
        return mAppDao.getProject(projectId);
    }

    public LiveData<Project[]> getProjectList() {
        return mAppDao.getAllProjects();
    }
}
