package com.novakduc.forbega.qlnt.config.finance;

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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    private EditText mEdtLoanDate;
    private LoanDeclareFragmentListener mCallBack;

    public static ProjectLoanDeclareFragment newInstance() {

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

        final ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_navigate_before);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //*/

        mCallBack = (LoanDeclareFragmentListener) getActivity();
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
                try {
                    mAmount = Long.valueOf(editable.toString());
                    if (mAmount <= 0) {
                        throw new NumberFormatException();
                    } else {
                        mLayoutAmount.setErrorEnabled(false);
                    }
                } catch (NumberFormatException e) {
                    mLayoutAmount.setError(getString(R.string.durationInputError));
                    mLayoutAmount.setErrorEnabled(true);
                }
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
                try {
                    mRate = Float.valueOf(editable.toString());
                    if (mRate <= 0 || mRate > 100) {
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

        mEdtLoanDate = (EditText) view.findViewById(R.id.edtLoanDate);
        mEdtLoanDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(new DatePickerFragment(), DatePickerFragment.START_DATE_PICKED);
            }
        });

        Button btCancel = (Button) view.findViewById(R.id.btCancel);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBack.discardConfirm();
            }
        });
        Button btConfirm = (Button) view.findViewById(R.id.btConfirm);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 4/19/2017 confirm action
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
        if (item.getItemId() == R.id.home) {
            // TODO: 4/30/2017 not working yet
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
