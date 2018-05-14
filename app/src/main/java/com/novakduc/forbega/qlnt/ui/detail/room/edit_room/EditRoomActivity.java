package com.novakduc.forbega.qlnt.ui.detail.room.edit_room;

import android.support.v4.app.Fragment;

import com.novakduc.baselibrary.SimpleFragmentActivity;
import com.novakduc.forbega.qlnt.ui.ConfirmationDialogFragment;

public class EditRoomActivity extends SimpleFragmentActivity implements ConfirmationDialogFragment.ConfirmListener {
    @Override
    protected Fragment createFragment() {
        long roomId = getIntent().getLongExtra(EditRoomFragment.ROOM_ID, -1);
        if (roomId != -1) {
            return EditRoomFragment.getInstance(roomId);
        }
        return null;
    }

    @Override
    public void action(int result) {

    }
}
