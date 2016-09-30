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

    public Boolean addProject(Project project) {
        new RecordProject().execute(project);
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

        @Override
        protected void onPostExecute(Object o) {
            Toast.makeText(sContext, R.string.project_recorded, Toast.LENGTH_SHORT).show();
        }
    }
}
