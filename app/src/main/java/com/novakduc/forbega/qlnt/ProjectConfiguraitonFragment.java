package com.novakduc.forbega.qlnt;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.novakduc.forbega.qlnt.model.Project;

/**
 * Created by n.thanh on 10/21/2016.
 */

public class ProjectConfiguraitonFragment extends Fragment {
    private Project mProject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProject = new Project();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_configuration, container, false);
        return view;
    }
}
