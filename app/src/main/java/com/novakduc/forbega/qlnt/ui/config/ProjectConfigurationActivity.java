package com.novakduc.forbega.qlnt.ui.config;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

import com.novakduc.baselibrary.SimpleFragmentActivity;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.data.database.Loan;
import com.novakduc.forbega.qlnt.data.database.LoanList;
import com.novakduc.forbega.qlnt.data.database.Project;
import com.novakduc.forbega.qlnt.data.database.UnitPrice;
import com.novakduc.forbega.qlnt.ui.ConfirmationDialogFragment;
import com.novakduc.forbega.qlnt.ui.config.base.ProjectBaseConfigFragment;

public class ProjectConfigurationActivity extends SimpleFragmentActivity
        implements UpdateListener, ConfirmationDialogFragment.ConfirmListener {
    private Project mTempProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected android.support.v4.app.Fragment createFragment() {
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
        android.support.v4.app.DialogFragment dialogFragment = new ConfirmationDialogFragment();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getSupportFragmentManager(), "discardConfirm");
    }

    @Override
    public void updateFinance(long investment, LoanList loanLoanList) {
        mTempProject.setInvestmentAmount(investment);

        mTempProject.getLoanList().clear();

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
    public void addProject() {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragmentContainer,
                ProjectCreateConfirmationFragment.newInstance(mTempProject)).addToBackStack(null).commit();
    }

    @Override
    public void action(int result) {
        if (result == ConfirmationDialogFragment.RESULT_OK) {
            //user confirm to discard project creation.
            finish();
        }
    }
}
