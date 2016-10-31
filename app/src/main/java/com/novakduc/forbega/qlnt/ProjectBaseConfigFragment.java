package com.novakduc.forbega.qlnt;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.novakduc.forbega.qlnt.model.Project;

/**
 * Created by n.thanh on 10/21/2016.
 */

public class ProjectBaseConfigFragment extends Fragment {
    public static final String TEMP_PROJECT = "com.novakduc.forbega.qlnt.tempproject";
    private Project mProject;

    public static ProjectBaseConfigFragment newInstance(Project tempProject) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TEMP_PROJECT, tempProject);
        ProjectBaseConfigFragment fragment = new ProjectBaseConfigFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProject = (Project) getArguments().getSerializable(TEMP_PROJECT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_base_config, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        return view;
    }
}
