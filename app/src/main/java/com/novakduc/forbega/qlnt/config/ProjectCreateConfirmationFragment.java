package com.novakduc.forbega.qlnt.config;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.config.finance.ProjectFinanceConfigFragment;
import com.novakduc.forbega.qlnt.list.ProjectListActivity;
import com.novakduc.forbega.qlnt.model.Project;
import com.novakduc.forbega.qlnt.model.Qlnt;
import com.novakduc.forbega.qlnt.model.UnitPrice;

/**
 * Created by n.thanh on 10/21/2016.
 */

public class ProjectCreateConfirmationFragment extends Fragment {
    public static final String TEMP_PROJECT = "com.novakduc.forbega.qlnt.tempproject";

    private Project mTempProject;
    private UnitPrice mTempUnitPrice;

    public static ProjectCreateConfirmationFragment newInstance(Project tempProject) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(TEMP_PROJECT, tempProject);
        ProjectCreateConfirmationFragment fragment = new ProjectCreateConfirmationFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static ProjectCreateConfirmationFragment newInstance() {
        return new ProjectCreateConfirmationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mTempProject = (Project) getArguments().getParcelable(TEMP_PROJECT);
            mTempUnitPrice = mTempProject.getUnitPrice();
        }
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_confirmation, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.unitPrice));
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        final ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_navigate_before);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        EditText electricityEditText = view.findViewById(R.id.electricity);
        electricityEditText.setText(String.valueOf(mTempUnitPrice.getElectricity()));

        EditText waterEditText = view.findViewById(R.id.water);
        waterEditText.setText(String.valueOf(mTempUnitPrice.getWater()));

        EditText internetEditText = view.findViewById(R.id.internet);
        internetEditText.setText(String.valueOf(mTempUnitPrice.getInternet()));

        EditText securityEditText = view.findViewById(R.id.security);
        securityEditText.setText(String.valueOf(mTempUnitPrice.getSecurity()));

        EditText trashEditText = view.findViewById(R.id.trashCollention);
        trashEditText.setText(String.valueOf(mTempUnitPrice.getTrashCollection()));

        EditText tvEditText = view.findViewById(R.id.tv);
        tvEditText.setText(String.valueOf(mTempUnitPrice.getTv()));

        FloatingActionButton fab = view.findViewById(R.id.confirm_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextAction();   //to unit price config
            }
        });
        return view;
    }

    private void nextAction() {

        Qlnt.getInstance(getActivity().getApplicationContext()).addProject(mTempProject);
    }
}
