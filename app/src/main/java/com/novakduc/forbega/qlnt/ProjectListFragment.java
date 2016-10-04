package com.novakduc.forbega.qlnt;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;

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

        ArrayAdapter<Project> adapter = new ArrayAdapter<Project>(getActivity(),
                android.R.layout.simple_list_item_1, mProjects);
        setListAdapter(adapter);
    }
}
