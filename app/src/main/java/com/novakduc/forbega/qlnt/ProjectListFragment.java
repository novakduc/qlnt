package com.novakduc.forbega.qlnt;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.novakduc.forbega.qlnt.model.Project;
import com.novakduc.forbega.qlnt.model.Qlnt;

import java.util.ArrayList;

/**
 * Created by n.thanh on 9/29/2016.
 */

public class ProjectListFragment extends ListFragment {
    // TODO: 9/29/2016
    private ArrayList<Project> mProjects;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProjects = Qlnt.getInstance(getActivity()).getProjectList();

        ProjectListAdapter adapter = new ProjectListAdapter(mProjects);
        setListAdapter(adapter);
    }

    private class ProjectListAdapter extends ArrayAdapter<Project> {
        public ProjectListAdapter(ArrayList<Project> projects) {
            super(getActivity(), 0, projects);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.project_item_layout, null);
            }

            Project project = getItem(position);
            TextView projectNameTextView
                    = (TextView) convertView.findViewById(R.id.textViewProjectName);
            projectNameTextView.setText(project.getName());

            return convertView;
        }
    }
}
