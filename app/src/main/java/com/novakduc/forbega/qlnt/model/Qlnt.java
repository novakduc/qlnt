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

    private Qlnt() {
        mProjectList = new ArrayList<Project>();
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
            Toast.makeText(sContext, R.string.delete_project_confirm, Toast.LENGTH_SHORT).show();
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
