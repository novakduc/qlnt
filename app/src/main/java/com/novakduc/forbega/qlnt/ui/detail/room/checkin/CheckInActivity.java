package com.novakduc.forbega.qlnt.ui.detail.room.checkin;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.novakduc.baselibrary.SimpleFragmentActivity;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.ui.ConfirmationDialogFragment;

public class CheckInActivity extends SimpleFragmentActivity
        implements ConfirmationDialogFragment.ConfirmListener, CheckInActivityListener {
    @Override
    protected Fragment createFragment() {
        long roomId = getIntent().getLongExtra(CheckInFragment.ROOM_ID, -1);
        if (roomId != -1) {
            return CheckInFragment.getInstance(roomId);
        }
        Toast.makeText(this, getString(R.string.roomIdError), Toast.LENGTH_SHORT).show();
        return null;
    }

    @Override
    public void action(int result) {
        if (result == ConfirmationDialogFragment.RESULT_OK) {
            //user confirm to discard check in process
            finish();
        }
    }

    @Override
    public void discardConfirmation(int messageId) {
        ConfirmationDialogFragment.showDialog(getString(messageId), getSupportFragmentManager());
    }
}
