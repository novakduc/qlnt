package com.novakduc.forbega.qlnt.data.database;

import android.content.Context;

/**
 * Created by n.thanh on 9/20/2016.
 */

public class Qlnt {
    private static Qlnt ourInstance = new Qlnt();
    private static Context sContext;
    private MyArrayList<Project> mProjectList;
    private long mActiveProjectId = -1;

    private Qlnt() {
        mProjectList = new MyArrayList<Project>();
    }

    public static Qlnt getInstance(Context context) {
        sContext = context;
        return ourInstance;
    }

    public boolean removeProject(Long projectId) {
        for (Project project :
                mProjectList) {
            if (project.getProjectId() == projectId) {
                // TODO: 1/3/2018 update database
                return mProjectList.remove(project);
            }
        }
        return false;   //there is no such project in list
    }

    public Boolean addProject(Project project) {
        // TODO: 1/3/2018 update database
        return mProjectList.add(project);
    }

    public Project getProject(Long projectId) {
        for (Project project :
                mProjectList) {
            if (project.getProjectId() == projectId) {
                return project;
            }
        }
        return null;    //no such project
    }

    public MyArrayList<Project> getProjectList() {
        return mProjectList;
    }

    public long getActiveProjectId() {
        return mActiveProjectId;
    }

    public void setActiveProjectId(long activeProjectId) {
        mActiveProjectId = activeProjectId;
    }

    public Project getActiveProject(long activeProjectId) {
        for (Project project :
                mProjectList) {
            if (project.getProjectId() == activeProjectId) {
                return project;
            }
        }
        return null;
    }
}
