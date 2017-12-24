package com.novakduc.forbega.qlnt.config.finance;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.novakduc.forbega.qlnt.DatePickerFragment;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.model.Loan;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by n.thanh on 10/21/2016.
 */

public class ProjectLoanDeclareFragment extends Fragment {
    public static final String TEMP_LOAN = "com.novakduc.forbega.qlnt.tempLoan";
    public static final String RETURN_LOAN = "com.forbega.qlnt.temproject.loandeclare.returnloan";
    public static final String LOAN_TO_EDIT = "com.novakduc.forbega.qlnt.loanDeclare.loanId";
    private String mBankName;
    private long mAmount = -1;
    private double mRate = 0;
    private long mLoanDate;
    private Loan mLoan;
    private TextInputLayout mLayoutBank, mLayoutAmount, mLayoutRate, mLayoutDate;
    private EditText mEdtLoanDate;
    private LoanDeclareFragmentListener mCallBack;

    public static ProjectLoanDeclareFragment newInstance() {

        return new ProjectLoanDeclareFragment();
    }

    public static ProjectLoanDeclareFragment newInstance(Loan loan) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(TEMP_LOAN, loan);
        ProjectLoanDeclareFragment fragment = new ProjectLoanDeclareFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        mLoan = intent.getParcelableExtra(LOAN_TO_EDIT);
        setHasOptionsMenu(true);
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
        EditText edtBankName = view.findViewById(R.id.edtInputLoanBank);
        if (mLoan != null) {
            mBankName = mLoan.getName();
            edtBankName.setText(mBankName);
        }
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

        EditText edtLoanAmount = view.findViewById(R.id.edtLoanAmount);
        if (mLoan != null) {
            mAmount = mLoan.getAmount();
            edtLoanAmount.setText(String.valueOf(mAmount));
        }
        edtLoanAmount.addTextChangedListener(new TextWatcher() {
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
                    if (mAmount <= 0) {
                        throw new NumberFormatException();
                    } else {
                        mLayoutAmount.setErrorEnabled(false);
                    }
                } catch (NumberFormatException e) {
                    mLayoutAmount.setError(getString(R.string.invalidLoanAmount));
                    mLayoutAmount.setErrorEnabled(true);
                }
            }
        });

        EditText edtRate = view.findViewById(R.id.edtRate);
        if (mLoan != null) {
            mRate = mLoan.getInterestRate();
            edtRate.setText(String.valueOf(mRate));
        }
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
        Calendar tmp = Calendar.getInstance();
        if (mLoan == null) {
            mLoanDate = tmp.getTimeInMillis();
        } else {
            mLoanDate = mLoan.getLoanDate();
            tmp.setTimeInMillis(mLoanDate);
        }
        mEdtLoanDate.setText(calendarToString(tmp));

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
                mCallBack.discardConfirm();
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

                if (error) {
                    return;
                }

                //return valid loan
                Intent returnIntent = new Intent();
                if (mLoan == null) {
                    mLoan = new Loan(mBankName, mAmount, mLoanDate, mRate);
                } else {
                    mLoan.setName(mBankName);
                    mLoan.setLoanDate(mLoanDate);
                    mLoan.setInterestRate(mRate);
                    mLoan.setAmount(mAmount);
                }
                returnIntent.putExtra(RETURN_LOAN, mLoan);
                getActivity().setResult(Activity.RESULT_OK, returnIntent);
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //RESULT FROM DATE PICKER
        if (requestCode == DatePickerFragment.START_DATE_PICKED) {
            if (resultCode != AppCompatActivity.RESULT_OK) return;
            Calendar tmpCalendar = (Calendar) data.getSerializableExtra(DatePickerFragment.PICKED_DATE);
            mLoanDate = tmpCalendar.getTimeInMillis();
            mEdtLoanDate.setText(calendarToString(tmpCalendar));
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

    private String calendarToString(Calendar calendar) {
        Date date = calendar.getTime();
        DateFormat format = SimpleDateFormat.getDateInstance();
        return format.format(date);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.close_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mCallBack.discardConfirm();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog(DialogFragment fragment, int target) {
        fragment.setTargetFragment(this, target);
        fragment.show(getActivity().getFragmentManager(),
                "dialog");
    }
}
