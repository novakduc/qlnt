package com.novakduc.forbega.qlnt.ui.config.finance.loan;

import android.os.Bundle;

import com.novakduc.baselibrary.SimpleFragmentActivity;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.ui.ConfirmationDialogFragment;

public class ProjectLoanDeclareActivity extends SimpleFragmentActivity
        implements ConfirmationDialogFragment.ConfirmListener, LoanDeclareFragmentListener {

    @Override
    protected android.support.v4.app.Fragment createFragment() {
        return ProjectLoanDeclareFragment.newInstance();
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
    public void discardConfirm() {
        Bundle bundle = new Bundle();
        //dialog title in bundle
        bundle.putString(ConfirmationDialogFragment.MESSAGE,
                getResources().getString(R.string.loanDiscardConfirm));
        android.support.v4.app.DialogFragment dialogFragment = new ConfirmationDialogFragment();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getSupportFragmentManager(), "discardConfirm");
    }
}
