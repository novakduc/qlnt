package com.novakduc.forbega.qlnt.ui.list;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.data.database.Project;
import com.novakduc.forbega.qlnt.ui.ConfirmationDialogFragment;
import com.novakduc.forbega.qlnt.ui.config.EditProjectActivity;
import com.novakduc.forbega.qlnt.ui.config.ProjectConfigurationActivity;
import com.novakduc.forbega.qlnt.ui.config.ProjectEditFragment;
import com.novakduc.forbega.qlnt.ui.detail.ProjectDetailActivity;
import com.novakduc.forbega.qlnt.utilities.InjectorUtils;
import com.novakduc.forbega.qlnt.utilities.ItemListAdapterActionHandler;

import java.util.List;
import java.util.Observer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by n.thanh on 9/29/2016.
 */

public class ProjectListFragment extends Fragment
        implements ItemListAdapterActionHandler {
    public static final String PREF_QLNT = "com.novak.forbequ.qlnt";
    public static final String ACTIVE_PROJECT_ID = "active_project_id";
    private static final String LOG_TAG = ProjectListFragment.class.getSimpleName();
    private ProjectsRecyclerViewAdapter mProjectsRecyclerViewAdapter;
    private long mActiveProjectId = -1;
    private ProjectListFragmentViewModel mViewModel;
    private long mTempProjectId;
    private FloatingActionButton mFloatingActionButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ProjectListViewModelFactory factory =
                InjectorUtils.provideProjectListViewModelFactory(getActivity());

        mViewModel = ViewModelProviders.of(this, factory).get(ProjectListFragmentViewModel.class);
        mViewModel.getProjects().observe(this, new androidx.lifecycle.Observer<List<ListViewProjectItem>>() {
            @Override
            public void onChanged(@Nullable List<ListViewProjectItem> projects) {
                if (projects != null) {
                    if (mProjectsRecyclerViewAdapter != null) {
                        mProjectsRecyclerViewAdapter.swapList(projects);
                        Log.d(LOG_TAG, "Project list changed");
                    }
                }
            }
        });

        mViewModel.getLoanAmounts().observe(this, loanAmounts -> {
            if (loanAmounts != null) {
                mProjectsRecyclerViewAdapter.updateLoanAmounts(loanAmounts);
            }
        });
    }

    private void bindToUI(Project[] projects) {

    }

    @Override
    public void onStop() {
        super.onStop();

        //Save active project ID
        SharedPreferences preferences = getActivity().getSharedPreferences(PREF_QLNT, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(ACTIVE_PROJECT_ID, mActiveProjectId);
        editor.apply();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_project_list, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        final ActionBar ab = activity.getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_view_list);
            ab.setDisplayHomeAsUpEnabled(false);
        }

        final ImageView imageView = view.findViewById(R.id.backdrop);
//        Glide.with(this)
//                .load(R.drawable.pic)
//                .apply(RequestOptions.centerCropTransform())
//                .into(imageView);

        mProjectsRecyclerViewAdapter = new ProjectsRecyclerViewAdapter(activity, this);
        RecyclerView recyclerView = view.findViewById(R.id.rv_project_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mProjectsRecyclerViewAdapter);

        //Add project button
        mFloatingActionButton = view.findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProjectConfigurationActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void deleteProject() {
        mViewModel.deleteProject(mTempProjectId);
        mFloatingActionButton.show();
    }

    @Override
    public void onDeleteAction(long projectId) {
        mTempProjectId = projectId;
        ConfirmationDialogFragment.showDialog(getString(R.string.delete_project_confirmation),
                ProjectListActivity.DELETE_PROJECT, getFragmentManager());
    }

    @Override
    public void onCopyAction(long projectId) {
        //mViewModel.copyProject(projectId);
    }

    @Override
    public void onEditAction(long projectId) {
        Intent intent = new Intent(getActivity(), EditProjectActivity.class);
        intent.putExtra(ProjectEditFragment.TEMP_PROJECT_ID, projectId);
        startActivity(intent);
    }

    @Override
    public void onItemClick(long id) {
        Log.d(LOG_TAG, "Clicked on project item with id: " + id);
        Intent intent = new Intent(getActivity(), ProjectDetailActivity.class);
        mActiveProjectId = id;
        intent.putExtra(ACTIVE_PROJECT_ID, id);
        startActivity(intent);
    }
}
