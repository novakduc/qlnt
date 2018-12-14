package com.novakduc.forbega.qlnt.ui.config.finance.loan;

import android.os.Bundle;

import com.novakduc.baselibrary.SimpleFragmentActivity;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.ui.ConfirmationDialogFragment;

import androidx.fragment.app.Fragment;

public class LoanDeclareActivity extends SimpleFragmentActivity
        implements ConfirmationDialogFragment.ConfirmListener, LoanDeclareFragmentListener {
    public static final String DISCARD_CONFIRMATION_KEY = LoanDeclareActivity.class.getName() +
            "discard_loan";

    @Override
    protected Fragment createFragment() {
        return LoanDeclareFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void action(int result, String purposeKey) {
        if (result == ConfirmationDialogFragment.RESULT_OK) {
            if (purposeKey == DISCARD_CONFIRMATION_KEY)
                //user confirm to discard project creation.
                finish();
        }
    }

    @Override
    public void onBackPressed() {
        discardConfirmation(R.string.loanDiscardConfirm,
                LoanDeclareActivity.DISCARD_CONFIRMATION_KEY);
    }

    @Override
    public void discardConfirmation(int messageId, String confirmPurpose) {
        ConfirmationDialogFragment.showDialog(getString(messageId), confirmPurpose, getSupportFragmentManager());
    }
}
