package com.novakduc.forbega.qlnt.ui.config.finance;

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

import com.novakduc.baselibrary.NumbericTextWatcher;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.data.database.Loan;
import com.novakduc.forbega.qlnt.data.database.LoanList;
import com.novakduc.forbega.qlnt.data.database.Project;
import com.novakduc.forbega.qlnt.ui.config.ProjectConfigurationActivity;
import com.novakduc.forbega.qlnt.ui.config.UpdateListener;
import com.novakduc.forbega.qlnt.ui.config.finance.loan.LoanDeclareActivity;
import com.novakduc.forbega.qlnt.ui.config.finance.loan.LoanDeclareFragment;
import com.novakduc.forbega.qlnt.ui.config.unitprice.ProjectUnitPriceConfigFragment;
import com.novakduc.forbega.qlnt.utilities.ConverterUtilities;
import com.novakduc.forbega.qlnt.utilities.CurrencyUnit;
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
    //private LoanList mLoanList;
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
        Log.d(LOG_TAG, "Start fragment to config finance for project id: " + projectId);
        //mLoanList = LoanList.getInstance(projectId);

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

        editTextAmount.addTextChangedListener(new NumbericTextWatcher(editTextAmount) {
            @Override
            public void executeAfterTextChanged(String value) {
                try {
                    mAmount = Long.valueOf(value);
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

                Log.d(LOG_TAG, "Create loan for project id: " + projectId);

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

        updateLoanList();

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
            mCallBack.discardConfirmation(R.string.project_create_discard,
                    ProjectConfigurationActivity.DISCARD_CURRENT_PROJECT);
        }

        if (item.getItemId() == android.R.id.home)
            getFragmentManager().popBackStack();
        return super.onOptionsItemSelected(item);
    }

    public void nextAction() {
        if (mAmount != -1) {
            mProject.setInvestmentAmount(mAmount);
            mViewModel.updateProject(mProject);
            android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragmentContainer,
                    ProjectUnitPriceConfigFragment.newInstance(mProject.getProjectId()))
                    .addToBackStack(null).commit();
        } else {
            mLayoutAmount.setError(getResources().getString(R.string.invesment_amount_error));
            mLayoutAmount.setErrorEnabled(true);
        }
    }

    private void updateLoanList() {
        mViewModel.getLoanListLiveData().observe(this, new Observer<List<Loan>>() {
            @Override
            public void onChanged(@Nullable List<Loan> loans) {
                if (loans != null) {
                    LoanList list = LoanList.getInstance(projectId);
                    list.addAll(loans);
                    if (mProject != null) {
                        mProject.setDept(list.getTotalAmount());
                        mViewModel.updateProject(mProject);
                    }

                    mTotalLoanTextView.setText(ConverterUtilities.currencyUnitConverterToString(
                            list.getTotalAmount(), CurrencyUnit.MIL_BASE, 3));
                    mLoansAdapter.swapList(loans);
                }
            }
        });
    }

    @Override
    public void deleteLoan(long loanId) {
        mViewModel.deleteLoan(loanId);
    }

    @Override
    public void editLoan(long loanId) {
        Intent intent = new Intent(getActivity(), LoanDeclareActivity.class);
        intent.putExtra(LoanDeclareFragment.PROJECT_ID, mProject.getId());
        intent.putExtra(LoanDeclareFragment.LOAN_TO_EDIT, loanId);
        startActivityForResult(intent, LoansAdapter.LOAN_EDIT_REQUEST_FROM_ADAPTER);
    }
}
