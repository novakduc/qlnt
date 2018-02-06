package com.novakduc.forbega.qlnt.ui.config;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import com.novakduc.forbega.qlnt.data.database.Loan;
import com.novakduc.forbega.qlnt.data.database.LoanList;
import com.novakduc.forbega.qlnt.data.database.Project;
import com.novakduc.forbega.qlnt.data.database.UnitPrice;
import com.novakduc.forbega.qlnt.ui.config.finance.LoanAdapterHandler;
import com.novakduc.forbega.qlnt.ui.config.finance.LoansAdapter;
import com.novakduc.forbega.qlnt.ui.config.finance.ProjectFinanceConfigViewModel;
import com.novakduc.forbega.qlnt.ui.config.finance.ProjectFinanceConfigViewModelFactory;
import com.novakduc.forbega.qlnt.ui.config.finance.loan.LoanDeclareActivity;
import com.novakduc.forbega.qlnt.ui.config.finance.loan.LoanDeclareFragment;
import com.novakduc.forbega.qlnt.ui.config.unitprice.UnitPriceConfigFragmentViewModel;
import com.novakduc.forbega.qlnt.ui.config.unitprice.UnitPriceConfigViewModelFactory;
import com.novakduc.forbega.qlnt.ui.detail.DatePickerFragment;
import com.novakduc.forbega.qlnt.utilities.ConverterUtilities;
import com.novakduc.forbega.qlnt.utilities.CurrencyUnit;
import com.novakduc.forbega.qlnt.utilities.InjectorUtils;

import java.util.Calendar;
import java.util.List;

/**
 * Created by n.thanh on 10/21/2016.
 */

public class ProjectEditFragment extends android.support.v4.app.Fragment
        implements LoanAdapterHandler {
    public static final String TEMP_PROJECT_ID = "com.novakduc.forbega.qlnt.tempproject";
    RecyclerView mRecyclerView;
    LoansAdapter mLoansAdapter;
    TextView mTotalLoanTextView;
    private Project mTempProject;
    private long mProjectId;
    private UnitPrice mTempUnitPrice;
    private EditText mEditTextAddress;
    private EditText mEditTextName;
    private EditText mEditTextInvestmentAmount;
    private EditText mEditTextStartDate;
    private EditText mEditTextEndDate;
    private EditText mEditTextDuration;
    private boolean mError = false;
    private TextInputLayout mLayoutName, mLayoutAddress, mLayoutDuration;
    private long mStartDate;
    private String mName, mAddress;
    private int mDuration = 10;
    private long mAmount = -1;
    private TextInputLayout mElectricityLayout, mWaterLayout, mSecurityLayout, mTrashLayout,
            mInternetLayout, mTvLayout, mLayoutAmount;
    private EditText mEditTextElecticity, mEditTextWater, mEditTextSecurity, mEditTextTrash,
            mEditTextInternet, mEditTextTv;
    private UpdateListener mCallBack;

    private ProjectFinanceConfigViewModel mFinanceConfigViewModel;
    private UnitPriceConfigFragmentViewModel mUnitPriceConfigFragmentViewModel;

    public static ProjectEditFragment newInstance(long projectId) {
        Bundle bundle = new Bundle();
        bundle.putLong(TEMP_PROJECT_ID, projectId);
        ProjectEditFragment fragment = new ProjectEditFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static ProjectEditFragment newInstance() {
        return new ProjectEditFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProjectId = getArguments().getLong(TEMP_PROJECT_ID);
        mCallBack = (UpdateListener) getActivity();

        ProjectFinanceConfigViewModelFactory financeConfigViewModelFactory =
                InjectorUtils.provideProjectFinanceConfigViewModelFactory(getActivity(), mProjectId);
        UnitPriceConfigViewModelFactory unitPriceConfigViewModelFactory =
                InjectorUtils.provideUnitPriceConfigViewModelFactory(getActivity(), mProjectId);

        mFinanceConfigViewModel = ViewModelProviders.of(this,
                financeConfigViewModelFactory).get(ProjectFinanceConfigViewModel.class);

        mUnitPriceConfigFragmentViewModel = ViewModelProviders.of(this,
                unitPriceConfigViewModelFactory).get(UnitPriceConfigFragmentViewModel.class);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_confirmation, container, false);

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
                    mName = editable.toString();
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
                    mAddress = editable.toString();
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

        mEditTextDuration = view.findViewById(R.id.editTextDuration);

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
                        mDuration = duration;
                        if (mTempProject != null) {
                            mEditTextEndDate.setText(
                                    ConverterUtilities.calendarToString(mTempProject.getEndDate()));
                        }
                    }
                } catch (NumberFormatException e) {
                    mLayoutDuration.setError(getString(R.string.durationInputError));
                    mLayoutDuration.setErrorEnabled(true);
                }
            }
        });

        mEditTextStartDate = view.findViewById(R.id.editTextStartDate);

        mEditTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(new DatePickerFragment(), DatePickerFragment.START_DATE_PICKED);
            }
        });

        mEditTextEndDate = view.findViewById(R.id.editTextEndDate);
        mEditTextEndDate.setFocusableInTouchMode(false);

        mLayoutAmount = view.findViewById(R.id.txtLayoutInvestment);
        mLayoutAmount.setErrorEnabled(false);
        mEditTextInvestmentAmount = view.findViewById(R.id.investmentAmount);
        mEditTextInvestmentAmount.setFocusableInTouchMode(false);

        mEditTextInvestmentAmount.addTextChangedListener(new TextWatcher() {
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

        Button buttonAddLoan = view.findViewById(R.id.btAddLoan);
        buttonAddLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoanDeclareActivity.class);
                startActivityForResult(intent, LoansAdapter.LOAN_CREATION);
            }
        });

        mLoansAdapter = new LoansAdapter(activity, this);

        mTotalLoanTextView = view.findViewById(R.id.totalLoan);

        mRecyclerView = view.findViewById(R.id.loanList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mLoansAdapter);

        //Unit price section
        //////////////////////////////////////////////////////////
        if (mTempUnitPrice == null) {
            mTempUnitPrice = UnitPrice.getInstance(mProjectId);
        }

        mElectricityLayout = view.findViewById(R.id.txtLayoutElectricity);
        mEditTextElecticity = view.findViewById(R.id.electricity);

        //editTextAmount.setText(String.valueOf(mAmount));
        mEditTextElecticity.addTextChangedListener(new TextWatcher() {
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
                        mTempUnitPrice.setElectricity(temp);
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
        mEditTextWater = view.findViewById(R.id.water);
        //editTextAmount.setText(String.valueOf(mAmount));
        mEditTextWater.addTextChangedListener(new TextWatcher() {
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
                        mTempUnitPrice.setWater(temp);
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
        mEditTextInternet = view.findViewById(R.id.internet);
        //editTextAmount.setText(String.valueOf(mAmount));
        mEditTextInternet.addTextChangedListener(new TextWatcher() {
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
                        mTempUnitPrice.setInternet(temp);
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
        mEditTextSecurity = view.findViewById(R.id.security);

        //editTextAmount.setText(String.valueOf(mAmount));
        mEditTextSecurity.addTextChangedListener(new TextWatcher() {
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
                        mTempUnitPrice.setSecurity(temp);
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
        mEditTextTrash = view.findViewById(R.id.trashCollention);
        //editTextAmount.setText(String.valueOf(mAmount));
        mEditTextTrash.addTextChangedListener(new TextWatcher() {
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
                        mTempUnitPrice.setTrashCollection(temp);
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
        mEditTextTv = view.findViewById(R.id.tv);

        //editTextAmount.setText(String.valueOf(mAmount));
        mEditTextTv.addTextChangedListener(new TextWatcher() {
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
                        mTempUnitPrice.setTv(temp);
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

        //View model observe
        mFinanceConfigViewModel.getProjectLiveData().observe(this, new Observer<Project>() {
            @Override
            public void onChanged(@Nullable Project project) {
                if (project != null) {
                    mTempProject = project;
                    bindProjectToUI();
                }
            }
        });

        mFinanceConfigViewModel.getLoanListLiveData().observe(this, new Observer<List<Loan>>() {
            @Override
            public void onChanged(@Nullable List<Loan> loans) {
                if (loans != null) {
                    LoanList list = LoanList.getInstance(mProjectId);
                    list.addAll(loans);
                    bindLoansToUi(list);
                }
            }
        });

        mUnitPriceConfigFragmentViewModel.getUnitPriceLiveData().observe(this, new Observer<UnitPrice>() {
            @Override
            public void onChanged(@Nullable UnitPrice unitPrice) {
                if (unitPrice != null) {
                    mTempUnitPrice = unitPrice;
                    bindUnitPriceToUi();
                }
            }
        });

        return view;
    }

    private void bindUnitPriceToUi() {
        mEditTextElecticity.setText(String.valueOf(mTempUnitPrice.getElectricity()));
        mEditTextWater.setText(String.valueOf(mTempUnitPrice.getWater()));
        mEditTextInternet.setText(String.valueOf(mTempUnitPrice.getInternet()));
        mEditTextSecurity.setText(String.valueOf(mTempUnitPrice.getSecurity()));
        mEditTextTrash.setText(String.valueOf(mTempUnitPrice.getTrashCollection()));
        mEditTextTv.setText(String.valueOf(mTempUnitPrice.getTv()));
    }

    private void bindLoansToUi(LoanList loans) {
        mTotalLoanTextView.setText(String.valueOf(ConverterUtilities.currencyUnitConverter(
                loans.getTotalLoanAmount(), CurrencyUnit.MIL_BASE, 3)));
        mLoansAdapter.swapList(loans);
    }

    private void bindProjectToUI() {
        mEditTextName.setText(mTempProject.getName());
        mEditTextAddress.setText(mTempProject.getAddress());
        mEditTextDuration.setText(String.valueOf(mTempProject.getDuration()));
        mEditTextStartDate.setText(ConverterUtilities.calendarToString(mTempProject.getStartDate()));
        mEditTextEndDate.setText(ConverterUtilities.calendarToString(mTempProject.getEndDate()));
        mEditTextInvestmentAmount.setText(String.valueOf(mTempProject.getInvestmentAmount()));
        mLayoutAmount.setErrorEnabled(false);
    }

    private void showDialog(android.support.v4.app.DialogFragment fragment, int target) {
        fragment.setTargetFragment(this, target);
        fragment.show(getActivity().getSupportFragmentManager(),
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
        mFinanceConfigViewModel.updateProject(mTempProject);
        mUnitPriceConfigFragmentViewModel.updateUnitPrice(mTempUnitPrice);
        mCallBack.saveProject();
        Toast.makeText(getActivity(), getString(R.string.annouce_project_saving), Toast.LENGTH_SHORT).show();
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
            mEditTextStartDate.setText(ConverterUtilities.calendarToString(mTempProject.getStartDate()));
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
            mEditTextEndDate.setText(ConverterUtilities.calendarToString(mTempProject.getEndDate()));
        } else {
            mEditTextEndDate.setText("");
        }

        //Receive loan data
        if (requestCode == LoansAdapter.LOAN_CREATION) {
            if (resultCode == Activity.RESULT_OK) {

            }
        }
        if (requestCode == LoansAdapter.LOAN_EDIT_REQUEST_FROM_ADAPTER) {
            if (resultCode == Activity.RESULT_OK) {

            }
        }
    }

    @Override
    public void deleteLoan(long loanId) {
        mFinanceConfigViewModel.deleteLoan(loanId);
    }

    @Override
    public void editLoan(long loanId) {
        Intent intent = new Intent(getActivity(), LoanDeclareActivity.class);
        intent.putExtra(LoanDeclareFragment.PROJECT_ID, mTempProject.getId());
        intent.putExtra(LoanDeclareFragment.LOAN_TO_EDIT, loanId);
        startActivityForResult(intent, LoansAdapter.LOAN_EDIT_REQUEST_FROM_ADAPTER);
    }
}
