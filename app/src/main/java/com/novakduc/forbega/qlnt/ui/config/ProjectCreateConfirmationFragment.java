package com.novakduc.forbega.qlnt.ui.config;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.TypedArray;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.data.database.CurrencyUnit;
import com.novakduc.forbega.qlnt.data.database.Loan;
import com.novakduc.forbega.qlnt.data.database.LoanList;
import com.novakduc.forbega.qlnt.data.database.Project;
import com.novakduc.forbega.qlnt.data.database.Qlnt;
import com.novakduc.forbega.qlnt.data.database.UnitPrice;
import com.novakduc.forbega.qlnt.ui.config.finance.LoanContainerListener;
import com.novakduc.forbega.qlnt.ui.config.finance.LoansAdapter;
import com.novakduc.forbega.qlnt.ui.config.finance.ProjectLoanDeclareActivity;
import com.novakduc.forbega.qlnt.ui.config.finance.ProjectLoanDeclareFragment;
import com.novakduc.forbega.qlnt.ui.detail.DatePickerFragment;

import java.util.Calendar;

/**
 * Created by n.thanh on 10/21/2016.
 */

public class ProjectCreateConfirmationFragment extends Fragment implements LoanContainerListener {
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
    private boolean mError = false;
    private TextInputLayout mLayoutName, mLayoutAddress, mLayoutDuration;
    private TextInputLayout mElectricityLayout, mWaterLayout, mSecurityLayout, mTrashLayout,
            mInternetLayout, mTvLayout;
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
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        final ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_navigate_before);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mLayoutName = view.findViewById(R.id.txtLayoutName);
        mEditTextName = view.findViewById(R.id.name);
        mEditTextName.setText(mTempProject.getName());
        mEditTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    mLayoutName.setError(getString(R.string.invalidName));
                    mLayoutName.setErrorEnabled(true);
                    mError = true;
                } else {
                    mError = false;
                    mLayoutName.setErrorEnabled(false);
                    mTempProject.setName(editable.toString());
                }
            }
        });

        mEditTextName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) return;
                Editable editable = mEditTextName.getText();
                if (editable.length() == 0) {
                    mLayoutName.setError(getString(R.string.invalidName));
                    mLayoutName.setErrorEnabled(true);
                    mError = true;
                } else {
                    mError = false;
                    mLayoutName.setErrorEnabled(false);
                }
            }
        });

        mLayoutAddress = view.findViewById(R.id.txtLayoutAddress);
        mEditTextAddress = view.findViewById(R.id.address);
        mEditTextAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    mLayoutAddress.setError(getString(R.string.invalidAddress));
                    mLayoutAddress.setErrorEnabled(true);
                    mError = true;
                } else {
                    mError = false;
                    mLayoutAddress.setErrorEnabled(false);
                    mTempProject.setAddress(editable.toString());
                }
            }
        });
        mEditTextAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) return;
                Editable editable = mEditTextAddress.getText();
                if (editable.length() == 0) {
                    mLayoutAddress.setError(getString(R.string.invalidAddress));
                    mLayoutAddress.setErrorEnabled(true);
                    mError = true;
                } else {
                    mError = false;
                    mLayoutAddress.setErrorEnabled(false);
                }

            }
        });
        mEditTextAddress.setText(mTempProject.getAddress());

        mEditTextDuration = view.findViewById(R.id.editTextDuration);
        mEditTextDuration.setText(String.valueOf(mTempProject.getDuration()));
        mLayoutDuration = view.findViewById(R.id.txtLayoutDuration);
        mEditTextDuration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int duration = Integer.valueOf(s.toString());
                    if (duration <= 0 || duration > 100) {
                        throw new NumberFormatException();
                    } else {
                        mLayoutDuration.setErrorEnabled(false);
                        mTempProject.setDuration(duration);
                        mEditTextEndDate.setText(Project.calendarToString(mTempProject.getEndDate()));
                    }
                } catch (NumberFormatException e) {
                    mLayoutDuration.setError(getString(R.string.durationInputError));
                    mLayoutDuration.setErrorEnabled(true);
                }
            }
        });

        mEditTextStartDate = view.findViewById(R.id.editTextStartDate);
        mEditTextStartDate.setText(Project.calendarToString(mTempProject.getStartDate()));
        mEditTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(new DatePickerFragment(), DatePickerFragment.START_DATE_PICKED);
            }
        });

        mEditTextEndDate = view.findViewById(R.id.editTextEndDate);
        mEditTextEndDate.setFocusableInTouchMode(false);
        mEditTextEndDate.setText(Project.calendarToString(mTempProject.getEndDate()));

        EditText editTextInvestmentAmount = view.findViewById(R.id.investmentAmount);
        editTextInvestmentAmount.setFocusableInTouchMode(false);
        editTextInvestmentAmount.setText(String.valueOf(mTempProject.getInvestmentAmount()));

        Button buttonAddLoan = view.findViewById(R.id.btAddLoan);
        buttonAddLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProjectLoanDeclareActivity.class);
                startActivityForResult(intent, LoansAdapter.LOAN_CREATION);
            }
        });

        LoanList loanList = mTempProject.getLoanList();
        mLoansAdapter = new LoansAdapter(activity, loanList);

        mTotalLoanTextView = view.findViewById(R.id.totalLoan);
        mTotalLoanTextView.setText(String.valueOf(loanList.getTotalLoanAmount(CurrencyUnit.MIL_BASE)));

        mRecyclerView = view.findViewById(R.id.loanList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mLoansAdapter);

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

        mElectricityLayout = view.findViewById(R.id.txtLayoutElectricity);
        EditText electricityEditText = view.findViewById(R.id.electricity);
        electricityEditText.setText(String.valueOf(mTempProject.getUnitPrice().getElectricity()));
        //editTextAmount.setText(String.valueOf(mAmount));
        electricityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    long temp = Long.valueOf(editable.toString());
                    if (temp < 0) {
                        throw new NumberFormatException();
                    } else {
                        mTempProject.getUnitPrice().setElectricity(temp);
                        mElectricityLayout.setErrorEnabled(false);
                        mError = false;
                    }
                } catch (NumberFormatException e) {
                    mElectricityLayout.setError(getString(R.string.invalid_input_error));
                    mElectricityLayout.setErrorEnabled(true);
                    mError = true;
                }
            }
        });

        mWaterLayout = view.findViewById(R.id.txtLayoutWater);
        EditText waterEditText = view.findViewById(R.id.water);
        waterEditText.setText(String.valueOf(mTempProject.getUnitPrice().getWater()));
        //editTextAmount.setText(String.valueOf(mAmount));
        waterEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    long temp = Long.valueOf(editable.toString());
                    if (temp < 0) {
                        throw new NumberFormatException();
                    } else {
                        mTempProject.getUnitPrice().setWater(temp);
                        mWaterLayout.setErrorEnabled(false);
                        mError = false;
                    }
                } catch (NumberFormatException e) {
                    mWaterLayout.setError(getString(R.string.invalid_input_error));
                    mWaterLayout.setErrorEnabled(true);
                    mError = true;
                }
            }
        });

        mInternetLayout = view.findViewById(R.id.txtLayoutInternet);
        EditText internetEditText = view.findViewById(R.id.internet);
        internetEditText.setText(String.valueOf(mTempProject.getUnitPrice().getInternet()));
        //editTextAmount.setText(String.valueOf(mAmount));
        internetEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    long temp = Long.valueOf(editable.toString());
                    if (temp < 0) {
                        throw new NumberFormatException();
                    } else {
                        mTempProject.getUnitPrice().setInternet(temp);
                        mInternetLayout.setErrorEnabled(false);
                        mError = false;
                    }
                } catch (NumberFormatException e) {
                    mInternetLayout.setError(getString(R.string.invalid_input_error));
                    mInternetLayout.setErrorEnabled(true);
                    mError = true;
                }
            }
        });

        mSecurityLayout = view.findViewById(R.id.txtLayoutSecurity);
        final EditText securityEditText = view.findViewById(R.id.security);
        securityEditText.setText(String.valueOf(mTempProject.getUnitPrice().getSecurity()));
        //editTextAmount.setText(String.valueOf(mAmount));
        securityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    long temp = Long.valueOf(editable.toString());
                    if (temp < 0) {
                        throw new NumberFormatException();
                    } else {
                        mTempProject.getUnitPrice().setSecurity(temp);
                        mSecurityLayout.setErrorEnabled(false);
                        mError = false;
                    }
                } catch (NumberFormatException e) {
                    mSecurityLayout.setError(getString(R.string.invalid_input_error));
                    mSecurityLayout.setErrorEnabled(true);
                    mError = true;
                }
            }
        });

        mTrashLayout = view.findViewById(R.id.txtLayoutTrashCollection);
        EditText trashEditText = view.findViewById(R.id.trashCollention);
        trashEditText.setText(String.valueOf(mTempProject.getUnitPrice().getTrashCollection()));
        //editTextAmount.setText(String.valueOf(mAmount));
        trashEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    long temp = Long.valueOf(editable.toString());
                    if (temp < 0) {
                        throw new NumberFormatException();
                    } else {
                        mTempProject.getUnitPrice().setTrashCollection(temp);
                        mTrashLayout.setErrorEnabled(false);
                        mError = false;
                    }
                } catch (NumberFormatException e) {
                    mTrashLayout.setError(getString(R.string.invalid_input_error));
                    mTrashLayout.setErrorEnabled(true);
                    mError = true;
                }
            }
        });

        mTvLayout = view.findViewById(R.id.txtLayoutTv);
        EditText tvEditText = view.findViewById(R.id.tv);
        tvEditText.setText(String.valueOf(mTempProject.getUnitPrice().getTv()));
        //editTextAmount.setText(String.valueOf(mAmount));
        tvEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    long temp = Long.valueOf(editable.toString());
                    if (temp < 0) {
                        throw new NumberFormatException();
                    } else {
                        mTempProject.getUnitPrice().setTv(temp);
                        mTvLayout.setErrorEnabled(false);
                        mError = false;
                    }
                } catch (NumberFormatException e) {
                    mTvLayout.setError(getString(R.string.invalid_input_error));
                    mTvLayout.setErrorEnabled(true);
                    mError = true;
                }
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////////

        FloatingActionButton fab = view.findViewById(R.id.confirm_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mError)
                    nextAction();   //confirm to create project
                else
                    Toast.makeText(activity, getResources().getString(R.string.invalid_input_error),
                            Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void showDialog(DialogFragment fragment, int target) {
        fragment.setTargetFragment(this, target);
        fragment.show(getActivity().getFragmentManager(),
                "dialog");
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
        //RESULT FROM DATE PICKER
        if (requestCode == DatePickerFragment.START_DATE_PICKED) {
            if (resultCode != AppCompatActivity.RESULT_OK) return;
            Calendar tmpCalendar = (Calendar) data.getSerializableExtra(DatePickerFragment.PICKED_DATE);
            mTempProject.setStartDate(tmpCalendar.getTimeInMillis());
            mEditTextStartDate.setText(Project.calendarToString(mTempProject.getStartDate()));
            TypedArray themeArray = getActivity().getTheme().obtainStyledAttributes(
                    new int[]{android.R.attr.editTextColor});
            try {
                int index = 0;
                int defaultColourValue = 0;
                int editTextColour = themeArray.getColor(index, defaultColourValue);
                mEditTextStartDate.setTextColor(editTextColour);
            } finally {
                // Calling recycle() is important. Especially if you use alot of TypedArrays
                themeArray.recycle();
            }
            mEditTextEndDate.setText(Project.calendarToString(mTempProject.getEndDate()));
        } else {
            mEditTextEndDate.setText("");
        }

        //Receive loan data
        if (requestCode == LoansAdapter.LOAN_CREATION) {
            if (resultCode == Activity.RESULT_OK) {
                Loan loan = data.getParcelableExtra(ProjectLoanDeclareFragment.RETURN_LOAN);
                mTempProject.getLoanList().add(loan);
                loanListUpdate();
            }
        }
        if (requestCode == LoansAdapter.LOAN_EDIT_REQUEST_FROM_ADAPTER) {

            if (resultCode == Activity.RESULT_OK) {
                Loan tempLoan = data.getParcelableExtra(ProjectLoanDeclareFragment.RETURN_LOAN);
                Loan loan = mTempProject.getLoanList().getLoan(tempLoan.getId());
                if (loan != null) {
                    loan.setAmount(tempLoan.getAmount());
                    loan.setInterestRate(tempLoan.getInterestRate());
                    loan.setLoanDate(tempLoan.getLoanDate());
                    loan.setName(tempLoan.getName());
                    loanListUpdate();
                }
            }
        }
    }

    private void loanListUpdate() {
        mLoansAdapter.notifyDataSetChanged();
        if (mTempProject.getLoanList() != null) {
            mTotalLoanTextView.setText(String.valueOf(
                    mTempProject.getLoanList().getTotalLoanAmount(CurrencyUnit.MIL_BASE)));
        }
    }

    @Override
    public void loanDeleteUpdate() {
        loanListUpdate();
    }
}
