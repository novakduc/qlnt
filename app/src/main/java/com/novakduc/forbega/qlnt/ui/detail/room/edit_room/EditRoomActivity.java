package com.novakduc.forbega.qlnt.ui.detail.room.edit_room;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.novakduc.baselibrary.SimpleFragmentActivity;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.ui.ConfirmationDialogFragment;
import com.novakduc.forbega.qlnt.ui.detail.room.checkin.CheckInActivity;
import com.novakduc.forbega.qlnt.ui.detail.room.checkin.CheckInFragment;

import java.util.List;

public class EditRoomActivity extends SimpleFragmentActivity
        implements ConfirmationDialogFragment.ConfirmListener, EditRoomActivityListener {
    public static final String DISCARD_EDIT_ROOM_KEY = EditRoomActivity.class.getName() + "discardEditRoom";
    public static final String DELETE_GUEST_KEY = EditRoomActivity.class.getName() + "deleteGuest";
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
        discardConfirmation(R.string.announce_discard_edit_room, DISCARD_EDIT_ROOM_KEY);
    }

    @Override
    public void action(int result, String confirmPurposeKey) {
        if (result == ConfirmationDialogFragment.RESULT_OK) {
            if (confirmPurposeKey == DISCARD_EDIT_ROOM_KEY) {
                //user confirm to discard check in process
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                for (Fragment f :
                        fragments) {
                    if (f instanceof EditRoomFragment) {
                        ((EditRoomFragment) f).discardEditRoom();  //delete guest from guest list
                        return;
                    }
                }
            }
        }

        if (result == ConfirmationDialogFragment.RESULT_OK) {
            if (confirmPurposeKey == DELETE_GUEST_KEY) {
                //Delete guest after confirm
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                for (Fragment f :
                        fragments) {
                    if (f instanceof EditRoomFragment) {
                        ((EditRoomFragment) f).deleteGuest();  //delete guest from guest list
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
