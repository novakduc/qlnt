package com.novakduc.forbega.qlnt.model;

import android.content.Context;
import android.os.AsyncTask;

import com.novakduc.forbega.qlnt.db.ProjectDbManager;

import java.util.ArrayList;

/**
 * Created by n.thanh on 9/20/2016.
 */
public class Qlnt {
    private static Qlnt ourInstance = new Qlnt();
    private static Context sContext;
    private ArrayList mProjectList;

    private Qlnt() {
        mProjectList = new ArrayList();
    }

    public static Qlnt getInstance(Context context) {
        sContext = context;
        return ourInstance;
    }

    public Boolean addProject(Project project) {
        return mProjectList.add(project);
    }

    ///Create thread to record project to DB
    private class RecordProject extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            Project project = (Project) objects[0];
            ProjectDbManager databaseManager = new ProjectDbManager(sContext);
            databaseManager.recordProject(project);
            return null;
        }
    }
}
