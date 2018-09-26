package com.novakduc.forbega.qlnt.ui.detail.room.checkin.add_guest;

import android.os.Bundle;
import android.widget.Toast;

import com.novakduc.baselibrary.SimpleFragmentActivity;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.ui.ConfirmationDialogFragment;
import com.novakduc.forbega.qlnt.ui.detail.room.checkin.CheckInFragment;

public class AddGuestActivity extends SimpleFragmentActivity
        implements ConfirmationDialogFragment.ConfirmListener, AddGuestActivityListener {

    @Override
    protected android.support.v4.app.Fragment createFragment() {
        return AddGuestFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void action(int result) {
        if (result == ConfirmationDialogFragment.RESULT_OK) {
            //user confirm to discard project creation.
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        // TODO: 4/30/2017 confirm discard loan on back press. Consider State save solution!
        super.onBackPressed();
    }

    @Override
    public void discardConfirmation(int messageId) {
        ConfirmationDialogFragment.showDialog(getString(messageId), getSupportFragmentManager());
    }
}
