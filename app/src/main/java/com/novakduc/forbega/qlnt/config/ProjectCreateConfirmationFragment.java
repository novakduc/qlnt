package com.novakduc.forbega.qlnt.config;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.novakduc.forbega.qlnt.config.finance.LoansAdapter;
import com.novakduc.forbega.qlnt.model.CurrencyUnit;
import com.novakduc.forbega.qlnt.model.LoanList;
import com.novakduc.forbega.qlnt.model.Project;
import com.novakduc.forbega.qlnt.model.Qlnt;
import com.novakduc.forbega.qlnt.model.UnitPrice;

/**
 * Created by n.thanh on 10/21/2016.
 */

public class ProjectCreateConfirmationFragment extends Fragment {
    public static final String TEMP_PROJECT = "com.novakduc.forbega.qlnt.tempproject";
    RecyclerView mRecyclerView;
    LoansAdapter mLoansAdapter;
    TextView mTotalLoanTextView;
    private Project mTempProject;
    private UnitPrice mTempUnitPrice;
    private EditText mEditTextAddress;
    private EditText mEditTextName;
    private EditText mEditTextStartDate;
    private EditText mEditTextEndDate;
    private EditText mEditTextDuration;
    private UpdateListener mCallBack;

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
        mTempProject = getArguments().getParcelable(TEMP_PROJECT);
            mTempUnitPrice = mTempProject.getUnitPrice();
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_confirmation, container, false);

        mCallBack = (UpdateListener) getActivity();
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
        layoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new LoansAdapter(activity,
                loanList));

        /*
        int loanCount = loanList.size();//mRecyclerView.getChildCount();
        if (loanCount > 0) {
            //View loanView = mRecyclerView.getChildAt(0);
            int itemHeight = 250; //loanView.getLayoutParams().height;
            ConstraintLayout financeLayout = (ConstraintLayout) view.findViewById(R.id.financeSection);
            ViewGroup.LayoutParams layoutParams = financeLayout.getLayoutParams();
            layoutParams.height = layoutParams.height + itemHeight * loanCount;
            financeLayout.setLayoutParams(layoutParams);
        }
        */

        EditText electricityEditText = view.findViewById(R.id.electricity);
        electricityEditText.setFocusableInTouchMode(false);
        electricityEditText.setText(String.valueOf(mTempUnitPrice.getElectricity()));

        EditText waterEditText = view.findViewById(R.id.water);
        waterEditText.setFocusableInTouchMode(false);
        waterEditText.setText(String.valueOf(mTempUnitPrice.getWater()));

        EditText internetEditText = view.findViewById(R.id.internet);
        internetEditText.setFocusableInTouchMode(false);
        internetEditText.setText(String.valueOf(mTempUnitPrice.getInternet()));

        EditText securityEditText = view.findViewById(R.id.security);
        securityEditText.setFocusableInTouchMode(false);
        securityEditText.setText(String.valueOf(mTempUnitPrice.getSecurity()));

        EditText trashEditText = view.findViewById(R.id.trashCollention);
        trashEditText.setFocusableInTouchMode(false);
        trashEditText.setText(String.valueOf(mTempUnitPrice.getTrashCollection()));

        EditText tvEditText = view.findViewById(R.id.tv);
        tvEditText.setFocusableInTouchMode(false);
        tvEditText.setText(String.valueOf(mTempUnitPrice.getTv()));

        FloatingActionButton fab = view.findViewById(R.id.confirm_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextAction();   //confirm to create project
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

    private void nextAction() {

        Qlnt.getInstance(getActivity().getApplicationContext()).addProject(mTempProject);
        getActivity().finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO: 12/24/2017 loan edit, delete
    }
}
