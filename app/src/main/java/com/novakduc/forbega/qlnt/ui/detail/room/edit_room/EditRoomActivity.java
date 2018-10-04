package com.novakduc.forbega.qlnt.ui.detail.room.edit_room;

import android.support.v4.app.Fragment;

import com.novakduc.baselibrary.SimpleFragmentActivity;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.ui.ConfirmationDialogFragment;
import com.novakduc.forbega.qlnt.ui.detail.room.checkin.CheckInActivity;

public class EditRoomActivity extends CheckInActivity {
    @Override
    protected Fragment createFragment() {
        long roomId = getIntent().getLongExtra(EditRoomFragment.ROOM_ID, -1);
        if (roomId != -1) {
            return EditRoomFragment.getInstance(roomId);
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        discardConfirmation(R.string.announce_discard_edit_room, DISCARD_CHECKIN_KEY);
    }
}
