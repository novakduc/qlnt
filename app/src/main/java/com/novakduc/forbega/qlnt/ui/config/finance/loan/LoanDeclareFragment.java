package com.novakduc.forbega.qlnt.ui.config.finance.loan;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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

import com.novakduc.baselibrary.NumbericTextWatcher;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.data.database.Loan;
import com.novakduc.forbega.qlnt.utilities.ConverterUtilities;
import com.novakduc.forbega.qlnt.utilities.DatePickerFragment;
import com.novakduc.forbega.qlnt.utilities.InjectorUtils;

/**
 * Created by n.thanh on 10/21/2016.
 */

public class LoanDeclareFragment extends android.support.v4.app.Fragment {
    public static final String LOAN_ID_KEY = "com.novakduc.forbega.qlnt.tempLoan";
    public static final String RETURN_LOAN = "com.forbega.qlnt.temproject.loandeclare.returnloan";
    public static final String LOAN_TO_EDIT = "com.novakduc.forbega.qlnt.loanDeclare.loanId";
    public static final int NEW_LOAN_FAKE_ID = -1;
    public static final String TYPE_KEY = "com.novakduc.forbega.qlnt.new_or_edit";
    public static final String PROJECT_ID = "com.novakduc.forbega.qlnt.projectId";
    private static final String LOG_TAG = LoanDeclareFragment.class.getSimpleName();
    private String mBankName;
    private long mAmount = -1;
    private double mRate = 0;
    private long mLoanDate;
    private long mLoanId;
    private Loan mLoan;
    private TextInputLayout mLayoutBank, mLayoutAmount, mLayoutRate, mLayoutDate;
    private EditText mEdtLoanDate;
    private LoanDeclareFragmentListener mCallBack;
    private LoanDeclareFragmentViewModel mViewModel;
    private EditText edtBankName, edtLoanAmount, edtRate;
    private Boolean isNew;

    public static LoanDeclareFragment newInstance() {

        return new LoanDeclareFragment();
    }

    public static LoanDeclareFragment newInstance(long loanId) {
        Bundle bundle = new Bundle();
        bundle.putLong(LOAN_ID_KEY, loanId);
        LoanDeclareFragment fragment = new LoanDeclareFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = getActivity().getIntent();
        long projectId = intent.getLongExtra(PROJECT_ID, -1);

        Log.d(LOG_TAG, "Start create loan for project id: " + projectId);

        isNew = intent.getBooleanExtra(TYPE_KEY, false);
        if (!isNew) {
            //Not create new loan, edit existed loan
            mLoanId = intent.getLongExtra(LOAN_TO_EDIT, -1);
            if (mLoanId == -1) {
                isNew = true;
            }
        }

        LoanDeclareViewModelFactory factory =
                InjectorUtils.provideLoanDeclareViewModelFactory(getActivity(), projectId, isNew);

        mViewModel = ViewModelProviders.of(this, factory).get(LoanDeclareFragmentViewModel.class);

        setHasOptionsMenu(true);
    }

    private void bindLoanToView(Loan loan) {
        mLoan = loan;
        mLoanId = mLoan.getId();
        if (!isNew) {
            mBankName = mLoan.getName();
            edtBankName.setText(mBankName);

            mAmount = mLoan.getAmount();
            edtLoanAmount.setText(String.valueOf(mAmount));

            mRate = mLoan.getInterestRate();
            edtRate.setText(String.valueOf(mRate));
        }
        mLoanId = mLoan.getId();
        mLoanDate = mLoan.getLoanDate();
        mEdtLoanDate.setText(ConverterUtilities.calendarToString(mLoanDate));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loan_declare, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.loanDeclareTitle);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        final ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_navigate_before);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //*/

        mCallBack = (LoanDeclareFragmentListener) getActivity();
        mLayoutBank = view.findViewById(R.id.txtLayoutBank);
        mLayoutDate = view.findViewById(R.id.txtLayoutLoanDate);
        mLayoutRate = view.findViewById(R.id.txtLayoutRate);
        mLayoutAmount = view.findViewById(R.id.txtLayoutLoanAmount);
        edtBankName = view.findViewById(R.id.edtInputLoanBank);

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
                    mBankName = null;
                } else {
                    mLayoutBank.setErrorEnabled(false);
                    mBankName = editable.toString();
                }
            }
        });

        edtLoanAmount = view.findViewById(R.id.edtLoanAmount);

        edtLoanAmount.addTextChangedListener(new NumbericTextWatcher(edtLoanAmount) {
            @Override
            public void executeAfterTextChanged(String value) {
                try {
                    mAmount = Long.valueOf(value);
                    if (mAmount <= 0) {
                        throw new NumberFormatException();
                    } else {
                        mLayoutAmount.setErrorEnabled(false);
                    }
                } catch (NumberFormatException e) {
                    mLayoutAmount.setError(getString(R.string.invalidLoanAmount));
                    mLayoutAmount.setErrorEnabled(true);
                    mAmount = -1;
                }
            }
        });

        edtRate = view.findViewById(R.id.edtRate);

        edtRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    mRate = Double.valueOf(editable.toString());
                    if (mRate < 0 || mRate > 50) {
                        throw new NumberFormatException();
                    } else {
                        mLayoutRate.setErrorEnabled(false);
                    }
                } catch (NumberFormatException e) {
                    mLayoutRate.setError(getString(R.string.rateInputError));
                    mLayoutRate.setErrorEnabled(true);
                }
            }
        });

        mEdtLoanDate = view.findViewById(R.id.edtLoanDate);

        mEdtLoanDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(new DatePickerFragment(), DatePickerFragment.START_DATE_PICKED);
            }
        });

        Button btCancel = view.findViewById(R.id.btCancel);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBack.discardConfirmation(R.string.loanDiscardConfirm,
                        LoanDeclareActivity.DISCARD_CONFIRMATION_KEY);
            }
        });
        Button btConfirm = view.findViewById(R.id.btConfirm);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean error = false;
                if (mBankName == null) {
                    mLayoutBank.setError(getString(R.string.invalidName));
                    mLayoutBank.setErrorEnabled(true);
                    error = true;
                }
                if (mAmount == -1) {
                    mLayoutAmount.setError(getString(R.string.invalidLoanAmount));
                    mLayoutAmount.setErrorEnabled(true);
                    error = true;
                }

                if (mRate < 0 || mRate > 50) {
                    mLayoutRate.setError(getString(R.string.rateInputError));
                    mLayoutRate.setErrorEnabled(true);
                    error = true;
                }

                if (error) {
                    return;
                }

                //Add loan to project
                mLoan.setName(mBankName);
                mLoan.setLoanDate(mLoanDate);
                mLoan.setInterestRate(mRate);
                mLoan.setAmount(mAmount);

                Log.d(LOG_TAG, "Create loan with id: " + mLoan.getId() + " in project: " + mLoan.getProjectId());

                mViewModel.updateLoan(mLoan);

                getActivity().finish();
            }
        });

        if (isNew) {
            mViewModel.getLoanLiveData().observe(this, new Observer<Loan>() {
                @Override
                public void onChanged(@Nullable Loan loan) {
                    if (loan != null) {
                        bindLoanToView(loan);
                    }
                }
            });
        } else {
            mViewModel.getLoanLiveData(mLoanId).observe(this, new Observer<Loan>() {
                @Override
                public void onChanged(@Nullable Loan loan) {
                    if (loan != null) {
                        bindLoanToView(loan);
                    }
                }
            });
        }

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //RESULT FROM DATE PICKER
        if (requestCode == DatePickerFragment.START_DATE_PICKED) {
            if (resultCode != AppCompatActivity.RESULT_OK) return;
            long pickDate = data.getLongExtra(DatePickerFragment.PICKED_DATE, -1);
            if (pickDate == -1) {
                return;
            }
            mLoanDate = pickDate;
            mEdtLoanDate.setText(ConverterUtilities.calendarToString(mLoanDate));
            TypedArray themeArray = getActivity().getTheme().obtainStyledAttributes(
                    new int[]{android.R.attr.editTextColor});
            try {
                int index = 0;
                int defaultColourValue = 0;
                int editTextColour = themeArray.getColor(index, defaultColourValue);
                mEdtLoanDate.setTextColor(editTextColour);
            } finally {
                // Calling recycle() is important. Especially if you use alot of TypedArrays
                themeArray.recycle();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mCallBack.discardConfirmation(R.string.loanDiscardConfirm,
                    LoanDeclareActivity.DISCARD_CONFIRMATION_KEY);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog(android.support.v4.app.DialogFragment fragment, int target) {
        fragment.setTargetFragment(this, target);
        fragment.show(getActivity().getSupportFragmentManager(),
                "dialog");
    }
}
