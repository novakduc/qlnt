package com.novakduc.forbega.qlnt.ui.list;

import com.novakduc.baselibrary.SimpleFragmentActivity;
import com.novakduc.forbega.qlnt.ui.ConfirmationDialogFragment;

import java.util.List;

import androidx.fragment.app.Fragment;

public class ProjectListActivity extends SimpleFragmentActivity
        implements ConfirmationDialogFragment.ConfirmListener {

    public static final String DELETE_PROJECT = ProjectListActivity.class.getName() +
            "deleteProject";

    @Override
    protected Fragment createFragment() {
        return new ProjectListFragment();
    }

    @Override
    public void action(int result, String purposeKey) {
        if (result == ConfirmationDialogFragment.RESULT_OK) {
            if (purposeKey == DELETE_PROJECT) {
                //Delete project after confirm
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                for (Fragment f :
                        fragments) {
                    if (f instanceof ProjectListFragment) {
                        ((ProjectListFragment) f).deleteProject();  //delete project from project list
                        return;
                    }
                }
            }
        }
    }
}