package com.novakduc.forbega.qlnt.ui.detail.room;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.data.database.Project;
import com.novakduc.forbega.qlnt.ui.list.ProjectListFragmentViewModel;

import java.util.ArrayList;

/**
 * Created by n.thanh on 9/29/2016.
 */

public class RoomListFragment extends android.support.v4.app.Fragment
        implements RoomsRecyclerViewAdapter.ItemListAdapterActionHandler {
    public static final String PREF_QLNT = "com.novak.forbequ.qlnt";
    public static final String ACTIVE_PROJECT_ID = "active_project_id";
    private static final String LOG_TAG = RoomListFragment.class.getSimpleName();
    private RoomsRecyclerViewAdapter mProjectsRecyclerViewAdapter;
    private long mActiveProject = -1;
    private ProjectListFragmentViewModel mViewModel;
    private ArrayList<Project> mProjects;
    private long mTempProjectId;
    private FloatingActionButton mFloatingActionButton;

    public static RoomListFragment getInstance(@NonNull long activeId) {
        Bundle bundle = new Bundle();
        bundle.putLong(ACTIVE_PROJECT_ID, activeId);
        RoomListFragment roomListFragment = new RoomListFragment();
        roomListFragment.setArguments(bundle);
        return roomListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            // TODO: 4/3/2018 load active project id
        } else {
            mActiveProject = getArguments().getLong(ACTIVE_PROJECT_ID);
        }

        Log.d(LOG_TAG, String.valueOf(mActiveProject));
    }

    private void bindToUI(Project[] projects) {

    }

    @Override
    public void onStop() {
        super.onStop();

        //Save active project ID
//        SharedPreferences preferences = getActivity().getSharedPreferences(PREF_QLNT, MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putLong(ACTIVE_PROJECT_ID, mActiveProject);
//        editor.apply();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_room_list_tab, container, false);
        Toast.makeText(getActivity(), String.valueOf(mActiveProject), Toast.LENGTH_SHORT).show();
        //Add project button
        mFloatingActionButton = view.findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 3/19/2018 add room
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
//        mTempProjectId = projectId;
//        Bundle bundle = new Bundle();
//        //dialog title in bundle
//        bundle.putString(ConfirmationDialogFragment.MESSAGE,
//                getResources().getString(R.string.delete_project_confirmation));
//        android.support.v4.app.DialogFragment dialogFragment = new ConfirmationDialogFragment();
//        dialogFragment.setArguments(bundle);
//        dialogFragment.show(getActivity().getSupportFragmentManager(), "discardConfirm");
    }

    @Override
    public void onCopyAction(long projectId) {
        mViewModel.copyProject(projectId);
    }

    @Override
    public void onEditAction(long projectId) {
//        Intent intent = new Intent(getActivity(), EditProjectActivity.class);
//        intent.putExtra(ProjectEditFragment.TEMP_PROJECT_ID, projectId);
//        startActivity(intent);
    }

    @Override
    public void onItemClick(long id) {
//        Log.d(LOG_TAG, "Clicked on project item with id: " + id);
//        Intent intent = new Intent(getActivity(), ProjectDetailActivity.class);
//        intent.putExtra(ACTIVE_PROJECT_ID, mActiveProject);
//        startActivity(intent);
    }
}
