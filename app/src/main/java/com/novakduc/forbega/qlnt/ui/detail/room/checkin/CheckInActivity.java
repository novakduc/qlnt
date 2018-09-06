package com.novakduc.forbega.qlnt.ui.detail.room.checkin;

import android.support.v4.app.Fragment;

import com.novakduc.baselibrary.SimpleFragmentActivity;
import com.novakduc.forbega.qlnt.ui.ConfirmationDialogFragment;

public class CheckInActivity extends SimpleFragmentActivity implements ConfirmationDialogFragment.ConfirmListener {
    @Override
    protected Fragment createFragment() {
        long roomId = getIntent().getLongExtra(CheckInFragment.ROOM_ID, -1);
        if (roomId != -1) {
            return CheckInFragment.getInstance(roomId);
        }
        return null;
    }

    @Override
    public void action(int result) {

    }
}
