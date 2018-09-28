package com.novakduc.forbega.qlnt.ui.detail.room.checkin;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.novakduc.baselibrary.SimpleFragmentActivity;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.ui.ConfirmationDialogFragment;
import com.novakduc.forbega.qlnt.ui.detail.room.RoomListFragment;

import java.util.List;

public class CheckInActivity extends SimpleFragmentActivity
        implements ConfirmationDialogFragment.ConfirmListener, CheckInActivityListener {

    public static final String DISCARD_CHECKIN_KEY = CheckInActivity.class.getName() + "discardCheckinProcess";
    public static final String DELETE_GUEST_KEY = CheckInActivity.class.getName() + "deleteGuest";

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
    public void action(int result, String purposeKey) {
        if (result == ConfirmationDialogFragment.RESULT_OK) {
            if (purposeKey == DISCARD_CHECKIN_KEY)
                //user confirm to discard check in process
                finish();
        }

        if (result == ConfirmationDialogFragment.RESULT_OK) {
            if (purposeKey == DELETE_GUEST_KEY) {
                //Delete room after confirm
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                for (Fragment f :
                        fragments) {
                    if (f instanceof CheckInFragment) {
                        ((CheckInFragment) f).deleteGuest();  //delete room from room list
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void discardConfirmation(int messageId, String purposeKey) {
        ConfirmationDialogFragment.showDialog(getString(messageId), purposeKey, getSupportFragmentManager());
    }
}
