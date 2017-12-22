package com.novakduc.forbega.qlnt.config.finance;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.config.UpdateListener;
import com.novakduc.forbega.qlnt.config.unitprice.ProjectUnitPriceConfigFragment;
import com.novakduc.forbega.qlnt.model.CurrencyUnit;
import com.novakduc.forbega.qlnt.model.Loan;
import com.novakduc.forbega.qlnt.model.LoanList;

/**
 * Created by n.thanh on 10/21/2016.
 */

public class ProjectFinanceConfigFragment extends Fragment {
    public static final String TEMP_PROJECT = "com.novakduc.forbega.qlnt.tempproject";
    private static final int LOAN_DECLARE_REQUEST = 1;
    RecyclerView mRecyclerView;
    LoansAdapter mLoansAdapter;
    TextView mTotalLoanTextView;
    private long mAmount = -1;
    private TextInputLayout mLayoutAmount;
    private UpdateListener mCallBack;
    private LoanList<Loan> mLoanList;

    public static ProjectFinanceConfigFragment newInstance() {
        return new ProjectFinanceConfigFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoanList = new LoanList<Loan>();
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_finance_config, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.financeConfigTitle);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        final ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_navigate_before);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mCallBack = (UpdateListener) getActivity();
        mLayoutAmount = view.findViewById(R.id.txtLayoutInvestment);
        final EditText editTextAmount = view.findViewById(R.id.investmentAmount);
        //editTextAmount.setText(String.valueOf(mAmount));
        editTextAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    mAmount = Long.valueOf(editable.toString());
                    if (mAmount < 0) {
                        throw new NumberFormatException();
                    } else {
                        mLayoutAmount.setErrorEnabled(false);
                    }
                } catch (NumberFormatException e) {
                    mLayoutAmount.setError(getString(R.string.invesment_amount_error));
                    mLayoutAmount.setErrorEnabled(true);
                    mAmount = -1;
                }
            }
        });

        mTotalLoanTextView = view.findViewById(R.id.totalLoan);

        Button addLoan = view.findViewById(R.id.btAddLoan);
        addLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProjectLoanDeclareActivity.class);
                startActivityForResult(intent, LOAN_DECLARE_REQUEST);
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.next_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextAction();
            }
        });

        mRecyclerView = view.findViewById(R.id.loanList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mLoansAdapter = new LoansAdapter(activity, mLoanList);
        mRecyclerView.setAdapter(mLoansAdapter);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.close_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.close) {
            mCallBack.discardConfirmation(R.string.project_create_discard);
        }

        if (item.getItemId() == android.R.id.home)
            getFragmentManager().popBackStack();
        return super.onOptionsItemSelected(item);
    }

    public void nextAction() {
        if (mAmount != -1) {
            mCallBack.updateFinance(mAmount, mLoanList);
            FragmentManager manager = getActivity().getFragmentManager();
            manager.beginTransaction().replace(R.id.fragmentContainer,
                    ProjectUnitPriceConfigFragment.newInstance()).addToBackStack(null).commit();
        } else {
            mLayoutAmount.setError(getResources().getString(R.string.invesment_amount_error));
            mLayoutAmount.setErrorEnabled(true);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOAN_DECLARE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Loan loan = data.getParcelableExtra(ProjectLoanDeclareFragment.RETURN_LOAN);
                mLoanList.add(loan);
                mLoansAdapter.notifyDataSetChanged();
                if (mLoanList != null) {
                    mTotalLoanTextView.setText(String.valueOf(mLoanList.getTotalLoanAmount(CurrencyUnit.MIL_BASE)));
                }

                /*
                // Check if no view has focus:
                View tmpview = getActivity().getCurrentFocus();
                if (tmpview != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(tmpview.getWindowToken(), 0);
                }
                */
            }
        }
    }

}
