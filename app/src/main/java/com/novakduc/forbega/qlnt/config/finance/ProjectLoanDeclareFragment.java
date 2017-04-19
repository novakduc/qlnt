package com.novakduc.forbega.qlnt.config.finance;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.config.UpdateListener;
import com.novakduc.forbega.qlnt.model.Project;

/**
 * Created by n.thanh on 10/21/2016.
 */

public class ProjectLoanDeclareFragment extends Fragment {
    public static final String TEMP_PROJECT = "com.novakduc.forbega.qlnt.tempproject";
    private String mBankName;
    private long mAmount;
    private double mRate;
    private long mLoanDate;
    private TextInputLayout mLayoutBank, mLayoutAmount, mLayoutRate, mLayoutDate;
    private UpdateListener mCallBack;

    public static ProjectLoanDeclareFragment newInstance(Project tempProject) {

        return new ProjectLoanDeclareFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loan_declare, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.loanDeclareTitle);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        /* no need home icon
        final ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_navigate_before);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        */

        mCallBack = (UpdateListener) getActivity();
        mLayoutBank = (TextInputLayout) view.findViewById(R.id.txtLayoutBank);
        mLayoutDate = (TextInputLayout) view.findViewById(R.id.txtLayoutLoanDate);
        mLayoutRate = (TextInputLayout) view.findViewById(R.id.txtLayoutRate);
        mLayoutAmount = (TextInputLayout) view.findViewById(R.id.txtLayoutLoanAmount);
        EditText edtBankName = (EditText) view.findViewById(R.id.edtInputLoanBank);
        edtBankName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    mLayoutBank.setError(getString(R.string.invalidBankName));
                    mLayoutBank.setErrorEnabled(true);
                    } else {
                        mLayoutAmount.setErrorEnabled(false);
                    mBankName = editable.toString();
                    }
            }
        });

        EditText edtLoanAmount = (EditText) view.findViewById(R.id.edtLoanAmount);
        edtLoanAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // TODO: 4/19/2017 check input text
            }
        });

        EditText edtRate = (EditText) view.findViewById(R.id.edtRate);
        edtRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // TODO: 4/19/2017 check input rate
            }
        });

        EditText edtLoanDate = (EditText) view.findViewById(R.id.edtLoanDate);
        edtLoanDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 4/19/2017 pick a date
            }
        });

        Button btCancel = (Button) view.findViewById(R.id.btCancel);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 4/19/2017 cancel action
                //mCallBack.discardConfirmation();
            }
        });
        Button btConfirm = (Button) view.findViewById(R.id.btBack);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 4/19/2017 confirm action
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
            // TODO: 4/19/2017 close action
        }
        return super.onOptionsItemSelected(item);
    }
}
