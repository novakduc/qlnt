package com.novakduc.forbega.qlnt.ui.detail.room.add_room;

import android.support.v4.app.Fragment;

import com.novakduc.baselibrary.SimpleFragmentActivity;
import com.novakduc.forbega.qlnt.ui.ConfirmationDialogFragment;
import com.novakduc.forbega.qlnt.ui.detail.room.RoomListFragment;
import com.novakduc.forbega.qlnt.utilities.ActivityListener;

public class AddRoomActivity extends SimpleFragmentActivity
        implements ActivityListener, ConfirmationDialogFragment.ConfirmListener {
    @Override
    protected Fragment createFragment() {
        long projectId = getIntent().getLongExtra(RoomListFragment.ACTIVE_PROJECT_ID, -1);
        if (projectId != -1) {
            return AddRoomFragment.getInstance(projectId);
        }
        return null;
    }

    @Override
    public void action(int result, String purposeKey) {

    }

    @Override
    public void discardConfirmation(int messageId, String purposeKey) {

    }
}
