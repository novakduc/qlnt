package com.novakduc.forbega.qlnt.config;

import android.app.Fragment;
import android.app.FragmentManager;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.config.finance.ProjectFinanceConfigFragment;
import com.novakduc.forbega.qlnt.list.ProjectListActivity;
import com.novakduc.forbega.qlnt.model.CurrencyUnit;
import com.novakduc.forbega.qlnt.model.LoanList;
import com.novakduc.forbega.qlnt.model.Project;
import com.novakduc.forbega.qlnt.model.Qlnt;
import com.novakduc.forbega.qlnt.model.UnitPrice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by n.thanh on 10/21/2016.
 */

public class ProjectCreateConfirmationFragment extends Fragment {
    public static final String TEMP_PROJECT = "com.novakduc.forbega.qlnt.tempproject";

    private Project mTempProject;
    private UnitPrice mTempUnitPrice;
    private EditText mEditTextAddress;
    private EditText mEditTextName;
    private EditText mEditTextStartDate;
    private EditText mEditTextEndDate;
    private EditText mEditTextDuration;

    RecyclerView mRecyclerView;
    ProjectFinanceConfigFragment.LoansAdapter mLoansAdapter;
    TextView mTotalLoanTextView;

    public static ProjectCreateConfirmationFragment newInstance(Project tempProject) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(TEMP_PROJECT, tempProject);
        ProjectCreateConfirmationFragment fragment = new ProjectCreateConfirmationFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static ProjectCreateConfirmationFragment newInstance() {
        return new ProjectCreateConfirmationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            mTempProject = (Project) getArguments().getParcelable(TEMP_PROJECT);
            mTempUnitPrice = mTempProject.getUnitPrice();
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_confirmation, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.project_create_confirm));
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        final ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_navigate_before);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mEditTextName = view.findViewById(R.id.name);
        mEditTextName.setFocusableInTouchMode(false);
        mEditTextName.setText(mTempProject.getName());

        mEditTextAddress = view.findViewById(R.id.address);
        mEditTextAddress.setFocusableInTouchMode(false);
        mEditTextAddress.setText(mTempProject.getAddress());

        mEditTextDuration = view.findViewById(R.id.editTextDuration);
        mEditTextDuration.setFocusableInTouchMode(false);
        mEditTextDuration.setText(String.valueOf(mTempProject.getDuration()));

        mEditTextStartDate = view.findViewById(R.id.editTextStartDate);
        mEditTextStartDate.setFocusableInTouchMode(false);
        mEditTextStartDate.setText(Project.calendarToString(mTempProject.getStartDate()));

        mEditTextEndDate = view.findViewById(R.id.editTextEndDate);
        mEditTextEndDate.setFocusableInTouchMode(false);
        mEditTextEndDate.setText(Project.calendarToString(mTempProject.getEndDate()));

        EditText editTextInvestmentAmount = view.findViewById(R.id.investmentAmount);
        editTextInvestmentAmount.setFocusableInTouchMode(false);
        editTextInvestmentAmount.setText(String.valueOf(mTempProject.getInvestment()));

        Button buttonAddLoan = view.findViewById(R.id.btAddLoan);
        buttonAddLoan.setEnabled(false);
        buttonAddLoan.setText("");

        LoanList loanList = mTempProject.getLoanList();

        mTotalLoanTextView = view.findViewById(R.id.totalLoan);
        mTotalLoanTextView.setText(String.valueOf(loanList.getTotalLoanAmount(CurrencyUnit.MIL_BASE)));

        mRecyclerView = view.findViewById(R.id.loanList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new ProjectFinanceConfigFragment.LoansAdapter(activity,
                loanList));

        EditText electricityEditText = view.findViewById(R.id.electricity);
        electricityEditText.setFocusableInTouchMode(false);
        electricityEditText.setText(String.valueOf(mTempUnitPrice.getElectricity()));

        EditText waterEditText = view.findViewById(R.id.water);
        waterEditText.setFocusableInTouchMode(false);
        waterEditText.setText(String.valueOf(mTempUnitPrice.getWater()));

        EditText internetEditText = view.findViewById(R.id.internet);
        internetEditText.setText(String.valueOf(mTempUnitPrice.getInternet()));

        EditText securityEditText = view.findViewById(R.id.security);
        securityEditText.setText(String.valueOf(mTempUnitPrice.getSecurity()));

        EditText trashEditText = view.findViewById(R.id.trashCollention);
        trashEditText.setText(String.valueOf(mTempUnitPrice.getTrashCollection()));

        EditText tvEditText = view.findViewById(R.id.tv);
        tvEditText.setText(String.valueOf(mTempUnitPrice.getTv()));

        FloatingActionButton fab = view.findViewById(R.id.confirm_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //nextAction();   //to unit price config
            }
        });
        return view;
    }

    private void nextAction() {

        Qlnt.getInstance(getActivity().getApplicationContext()).addProject(mTempProject);
    }
}
