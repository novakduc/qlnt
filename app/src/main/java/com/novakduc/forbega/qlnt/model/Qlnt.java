package com.novakduc.forbega.qlnt.model;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.db.ProjectDbManager;

import java.util.ArrayList;

/**
 * Created by n.thanh on 9/20/2016.
 */
public class Qlnt {
    private static Qlnt ourInstance = new Qlnt();
    private static Context sContext;
    private ArrayList<Project> mProjectList;
    private long mActiveProjectId = -1;

    private Qlnt() {
        mProjectList = new ArrayList<Project>();

        // TODO: 10/4/2016 Need to be removed
        //Test test test
        for (int i = 0; i < 20; i++) {
            Project project = new Project();
            project.setName(String.valueOf(i));
            mProjectList.add(project);
        }
    }

    public static Qlnt getInstance(Context context) {
        sContext = context;
        return ourInstance;
    }

    public boolean removeProject(Long projectId) {
        for (Project project :
                mProjectList) {
            if (project.getProjectId() == projectId) {
                new RemoveProject().execute(project);   //delete project in db
                return mProjectList.remove(project);
            }
        }
        return false;   //there is no such project in list
    }

    public Boolean addProject(Project project) {
        new RecordProject().execute(project);
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

    public ArrayList<Project> getProjectList() {
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

    //Create thread to remove project
    private class RemoveProject extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            Project project = (Project) objects[0];
            // TODO: 9/30/2016 only one database can be access at the same time. care!
            ProjectDbManager db = new ProjectDbManager(sContext);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            Toast.makeText(sContext, R.string.project_deleted_confirm, Toast.LENGTH_SHORT).show();
        }
    }

    ///Create thread to record project to DB
    private class RecordProject extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            Project project = (Project) objects[0];
            // TODO: 9/30/2016 only one database can be accessed at the same time. Care!
            ProjectDbManager databaseManager = new ProjectDbManager(sContext);
            databaseManager.recordProject(project);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            Toast.makeText(sContext, R.string.project_recorded, Toast.LENGTH_SHORT).show();
        }
    }
}
