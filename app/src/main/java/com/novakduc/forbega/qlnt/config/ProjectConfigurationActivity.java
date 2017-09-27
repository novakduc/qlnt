package com.novakduc.forbega.qlnt.config;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

import com.novakduc.baselibrary.SimpleFragmentActivity;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.config.base.ProjectBaseConfigFragment;
import com.novakduc.forbega.qlnt.model.Loan;
import com.novakduc.forbega.qlnt.model.LoanList;
import com.novakduc.forbega.qlnt.model.Project;
import com.novakduc.forbega.qlnt.model.UnitPrice;

public class ProjectConfigurationActivity extends SimpleFragmentActivity
        implements UpdateListener, ConfirmationDialogFragment.ConfirmListener {
    private Project mTempProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTempProject = new Project();
    }

    @Override
    protected Fragment createFragment() {
        return ProjectBaseConfigFragment.newInstance();
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getFragmentManager();
        int count = manager.getBackStackEntryCount();
        Log.i("count", String.valueOf(count));
        if (count == 0) {
            discardConfirmation(R.string.project_create_discard);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void discardConfirmation(int messageId) {
        Bundle bundle = new Bundle();
        //dialog title in bundle
        bundle.putString(ConfirmationDialogFragment.MESSAGE,
                getResources().getString(messageId));
        DialogFragment dialogFragment = new ConfirmationDialogFragment();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getFragmentManager(), "discardConfirm");
    }

    @Override
    public void updateBase(String name, String address, int duration, long startDate) {
        mTempProject.setName(name);
        mTempProject.setAddress(address);
        mTempProject.setDuration(duration);
        mTempProject.setStartDate(startDate);
    }

    @Override
    public void updateFinance(long investment, LoanList<Loan> loanLoanList) {
        mTempProject.setInvestment(investment);
        for (Loan loan :
                loanLoanList) {
            mTempProject.addLoan(loan);
        }
    }

    @Override
    public void updateUnitPrice(UnitPrice unitPrice) {
        mTempProject.setUnitPrice(unitPrice);
    }

    @Override
    public void discardDialog(int result) {
        if (result == ConfirmationDialogFragment.RESULT_OK) {
            //user confirm to discard project creation.
            super.onBackPressed();
        }
    }
}
