package com.novakduc.forbega.qlnt.config.finance;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;

import com.novakduc.baselibrary.SimpleFragmentActivity;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.config.ConfirmationDialogFragment;

public class ProjectLoanDeclareActivity extends SimpleFragmentActivity
        implements ConfirmationDialogFragment.ConfirmListener, LoanDeclareFragmentListener {

    @Override
    protected Fragment createFragment() {
        return ProjectLoanDeclareFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void discardDialog(int result) {
        if (result == ConfirmationDialogFragment.RESULT_OK) {
            //user confirm to discard project creation.
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        // TODO: 4/30/2017 confirm discard loan on back press
        super.onBackPressed();
    }

    @Override
    public void discardConfirm() {
        Bundle bundle = new Bundle();
        //dialog title in bundle
        bundle.putString(ConfirmationDialogFragment.MESSAGE,
                getResources().getString(R.string.loanDiscardConfirm));
        DialogFragment dialogFragment = new ConfirmationDialogFragment();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getFragmentManager(), "discardConfirm");
    }

    @Override
    public void loanUpdate(String bankName, long amount, float rate, long loanDate) {

    }
}
