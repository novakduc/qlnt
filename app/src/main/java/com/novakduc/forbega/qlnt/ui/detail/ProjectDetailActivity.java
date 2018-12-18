package com.novakduc.forbega.qlnt.ui.detail;

import com.novakduc.baselibrary.SimpleFragmentActivity;
import com.novakduc.forbega.qlnt.ui.ConfirmationDialogFragment;
import com.novakduc.forbega.qlnt.ui.detail.room.RoomListFragment;
import com.novakduc.forbega.qlnt.ui.list.ProjectListFragment;

import java.util.List;

import androidx.fragment.app.Fragment;

/**
 * Created by n.thanh on 10/12/2016.
 */

public class ProjectDetailActivity extends SimpleFragmentActivity
        implements ConfirmationDialogFragment.ConfirmListener {
    public static final String DELETE_ROOM = ProjectDetailActivity.class.getName() + "deleteRoom";

    @Override
    protected Fragment createFragment() {
        long activeProjectId = getIntent().getLongExtra(ProjectListFragment.ACTIVE_PROJECT_ID, -1);
        return ProjectDetailFragment.newInstance(activeProjectId);
    }

    @Override
    public void action(int result, String purposeKey) {
        if (result == ConfirmationDialogFragment.RESULT_OK) {
            if (purposeKey == DELETE_ROOM) {
                //Delete room after confirm
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                for (Fragment f :
                        fragments) {
                    if (f instanceof RoomListFragment) {
                        ((RoomListFragment) f).deleteRoom();  //delete room from room list
                        return;
                    }
                }
            }
        }
    }
}
