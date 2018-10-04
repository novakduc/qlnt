package com.novakduc.forbega.qlnt.ui.detail.room.edit_room;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.novakduc.baselibrary.SimpleFragmentActivity;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.ui.ConfirmationDialogFragment;
import com.novakduc.forbega.qlnt.ui.detail.room.checkin.CheckInActivity;

public class EditRoomActivity extends CheckInActivity {
    private static final String LOG_TAG = EditRoomActivity.class.getSimpleName();

    @Override
    protected Fragment createFragment() {
        long roomId = getIntent().getLongExtra(EditRoomFragment.ROOM_ID, -1);
        if (roomId != -1) {
            Log.d(LOG_TAG, "Get EditRoomFragment");
            return EditRoomFragment.newInstance(roomId);
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        discardConfirmation(R.string.announce_discard_edit_room, DISCARD_CHECKIN_KEY);
    }
}
