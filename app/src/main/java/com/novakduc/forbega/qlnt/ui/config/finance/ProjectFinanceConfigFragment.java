package com.novakduc.forbega.qlnt.ui.config.finance;

import android.app.Activity;
import android.app.FragmentManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.data.database.Loan;
import com.novakduc.forbega.qlnt.data.database.LoanList;
import com.novakduc.forbega.qlnt.data.database.Project;
import com.novakduc.forbega.qlnt.ui.config.UpdateListener;
import com.novakduc.forbega.qlnt.ui.config.finance.loan.LoanDeclareActivity;
import com.novakduc.forbega.qlnt.ui.config.finance.loan.LoanDeclareFragment;
import com.novakduc.forbega.qlnt.ui.config.unitprice.ProjectUnitPriceConfigFragment;
import com.novakduc.forbega.qlnt.utilities.InjectorUtils;

import java.util.List;

/**
 * Created by n.thanh on 10/21/2016.
 */

public class ProjectFinanceConfigFragment extends android.support.v4.app.Fragment
        implements LoanAdapterHandler {
    public static final String TEMP_PROJECT = "com.novakduc.forbega.qlnt.tempproject";
    private static final String LOG_TAG = ProjectFinanceConfigFragment.class.getSimpleName();
    RecyclerView mRecyclerView;
    LoansAdapter mLoansAdapter;
    TextView mTotalLoanTextView;
    private long projectId;
    private long mAmount = -1;
    private TextInputLayout mLayoutAmount;
    private UpdateListener mCallBack;
    private LoanList mLoanList;
    private ProjectFinanceConfigViewModel mViewModel;
    private Project mProject;

    public static ProjectFinanceConfigFragment newInstance(long projectId) {
        Bundle bundle = new Bundle();
        bundle.putLong(TEMP_PROJECT, projectId);
        ProjectFinanceConfigFragment fragment = new ProjectFinanceConfigFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        projectId = getArguments().getLong(TEMP_PROJECT);

        ProjectFinanceConfigViewModelFactory factory =
                InjectorUtils.provideProjectFinanceConfigViewModelFactory(getActivity(), projectId);

        mViewModel = ViewModelProviders.of(this, factory).get(ProjectFinanceConfigViewModel.class);
        mViewModel.getProjectLiveData().observe(this, new Observer<Project>() {
            @Override
            public void onChanged(@Nullable Project project) {
                if (project != null) {
                    Log.d(LOG_TAG, "Project investment amount: " + project.getInvestmentAmount());
                    mProject = project;
                }
            }
        });

        mViewModel.getLoanListLiveData().observe(this, new Observer<List<Loan>>() {
            @Override
            public void onChanged(@Nullable List<Loan> loans) {
                if (loans != null) {
                    mLoansAdapter.swapList(loans);
                }
            }
        });
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
                Intent intent = new Intent(getActivity(), LoanDeclareActivity.class);
                intent.putExtra(LoanDeclareFragment.TYPE_KEY, true);    //create new loan
                intent.putExtra(LoanDeclareFragment.PROJECT_ID, projectId);
                startActivityForResult(intent, LoansAdapter.LOAN_CREATION);
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
        mLoansAdapter = new LoansAdapter(getActivity(), this);
        mRecyclerView.setAdapter(mLoansAdapter);

        mViewModel.getLoanListLiveData().observe(this, new Observer<List<Loan>>() {
            @Override
            public void onChanged(@Nullable List<Loan> loans) {
                if (loans != null) {
                    mLoansAdapter.swapList(loans);
                }
            }
        });
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
            mProject.setInvestmentAmount(mAmount);
            mViewModel.updateProject(mProject);
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
        if (requestCode == LoansAdapter.LOAN_CREATION) {
            if (resultCode == Activity.RESULT_OK) {
                //Update loan list            }
            }
            if (requestCode == LoansAdapter.LOAN_EDIT_REQUEST_FROM_ADAPTER) {

                if (resultCode == Activity.RESULT_OK) {
                    //Update loan list
                }
            }
            mLoansAdapter.notifyDataSetChanged();
            if (mLoanList != null) {
                //mTotalLoanTextView.setText(String.valueOf(
                //mLoanList.getTotalLoanAmount(CurrencyUnit.MIL_BASE)));
            }
        }

        @Override
        public void deleteLoan ( long loanId){
            mViewModel.deleteLoan(loanId);
        }

        @Override
        public void editLoan ( long loanId){
            Intent intent = new Intent(getActivity(), LoanDeclareActivity.class);
            intent.putExtra(LoanDeclareFragment.LOAN_TO_EDIT, loanId);
            startActivityForResult(intent, LoansAdapter.LOAN_EDIT_REQUEST_FROM_ADAPTER);
        }
    }
