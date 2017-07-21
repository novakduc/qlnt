package com.novakduc.forbega.qlnt.config.unitprice;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.model.Project;

/**
 * Created by n.thanh on 10/21/2016.
 */

public class ProjectUnitPriceConfigFragment extends Fragment {
    public static final String TEMP_PROJECT = "com.novakduc.forbega.qlnt.tempproject";
    private Project mProject;

    public static ProjectUnitPriceConfigFragment newInstance(Project tempProject) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(TEMP_PROJECT, tempProject);
        ProjectUnitPriceConfigFragment fragment = new ProjectUnitPriceConfigFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static ProjectUnitPriceConfigFragment newInstance() {
        return new ProjectUnitPriceConfigFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mProject = (Project) getArguments().getParcelable(TEMP_PROJECT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_unitprice_config, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.unitPrice));
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        final ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_view_list);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        return view;
    }
}
