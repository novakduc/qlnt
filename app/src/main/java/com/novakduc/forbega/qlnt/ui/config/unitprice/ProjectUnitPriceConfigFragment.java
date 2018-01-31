package com.novakduc.forbega.qlnt.ui.config.unitprice;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.EditText;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.data.database.UnitPrice;
import com.novakduc.forbega.qlnt.ui.config.UpdateListener;
import com.novakduc.forbega.qlnt.utilities.InjectorUtils;

/**
 * Created by n.thanh on 10/21/2016.
 */

public class ProjectUnitPriceConfigFragment extends android.support.v4.app.Fragment {
    public static final String TEMP_PROJECT_ID = "com.novakduc.forbega.qlnt.tempproject";

    private TextInputLayout mElectricityLayout, mWaterLayout, mSecurityLayout, mTrashLayout,
            mInternetLayout, mTvLayout;
    private EditText mEditTextElecticity, mEditTextWater, mEditTextSecurity, mEditTextTrash,
            mEditTextInternet, mEditTextTv;
    private UpdateListener mCallBack;
    private long mProjectId;
    private UnitPrice mUnitPrice;
    private UnitPriceConfigFragmentViewModel mViewModel;

    public static ProjectUnitPriceConfigFragment newInstance(long projectId) {
        Bundle bundle = new Bundle();
        bundle.putLong(TEMP_PROJECT_ID, projectId);
        ProjectUnitPriceConfigFragment fragment = new ProjectUnitPriceConfigFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static ProjectUnitPriceConfigFragment newInstance() {
        return new ProjectUnitPriceConfigFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            //mUnitPrice = new UnitPrice();
        }
        setHasOptionsMenu(true);
        mProjectId = getArguments().getLong(TEMP_PROJECT_ID);
        mCallBack = (UpdateListener) getActivity();

        UnitPriceConfigViewModelFactory factory =
                InjectorUtils.provideUnitPriceConfigViewModelFactory(getActivity(), mProjectId);

        mViewModel = ViewModelProviders.of(this, factory).get(UnitPriceConfigFragmentViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_unitprice_config, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.unitPrice));
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        final ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_navigate_before);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (mUnitPrice == null) {
            mUnitPrice = UnitPrice.getInstance(mProjectId);
        }

        mElectricityLayout = view.findViewById(R.id.txtLayoutElectricity);
        mEditTextElecticity = view.findViewById(R.id.electricity);

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
                        mUnitPrice.setElectricity(temp);
                        mElectricityLayout.setErrorEnabled(false);
                    }
                } catch (NumberFormatException e) {
                    mElectricityLayout.setError(getString(R.string.invalid_input_error));
                    mElectricityLayout.setErrorEnabled(true);
                    mUnitPrice.setElectricity(-1);
                }
            }
        });

        mWaterLayout = view.findViewById(R.id.txtLayoutWater);
        mEditTextWater = view.findViewById(R.id.water);

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
                        mUnitPrice.setWater(temp);
                        mWaterLayout.setErrorEnabled(false);
                    }
                } catch (NumberFormatException e) {
                    mWaterLayout.setError(getString(R.string.invalid_input_error));
                    mWaterLayout.setErrorEnabled(true);
                    mUnitPrice.setWater(-1);
                }
            }
        });

        mInternetLayout = view.findViewById(R.id.txtLayoutInternet);
        mEditTextInternet = view.findViewById(R.id.internet);

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
                        mUnitPrice.setInternet(temp);
                        mInternetLayout.setErrorEnabled(false);
                    }
                } catch (NumberFormatException e) {
                    mInternetLayout.setError(getString(R.string.invalid_input_error));
                    mInternetLayout.setErrorEnabled(true);
                    mUnitPrice.setInternet(-1);
                }
            }
        });

        mSecurityLayout = view.findViewById(R.id.txtLayoutSecurity);
        mEditTextSecurity = view.findViewById(R.id.security);

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
                        mUnitPrice.setSecurity(temp);
                        mSecurityLayout.setErrorEnabled(false);
                    }
                } catch (NumberFormatException e) {
                    mSecurityLayout.setError(getString(R.string.invalid_input_error));
                    mSecurityLayout.setErrorEnabled(true);
                    mUnitPrice.setSecurity(-1);
                }
            }
        });

        mTrashLayout = view.findViewById(R.id.txtLayoutTrashCollection);
        mEditTextTrash = view.findViewById(R.id.trashCollention);

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
                        mUnitPrice.setTrashCollection(temp);
                        mTrashLayout.setErrorEnabled(false);
                    }
                } catch (NumberFormatException e) {
                    mTrashLayout.setError(getString(R.string.invalid_input_error));
                    mTrashLayout.setErrorEnabled(true);
                    mUnitPrice.setTrashCollection(-1);
                }
            }
        });

        mTvLayout = view.findViewById(R.id.txtLayoutTv);
        mEditTextTv = view.findViewById(R.id.tv);

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
                        mUnitPrice.setTv(temp);
                        mTvLayout.setErrorEnabled(false);
                    }
                } catch (NumberFormatException e) {
                    mTvLayout.setError(getString(R.string.invalid_input_error));
                    mTvLayout.setErrorEnabled(true);
                    mUnitPrice.setTv(-1);
                }
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.next_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextAction();   //to project creation confirmation
            }
        });

        mViewModel.getUnitPriceLiveData().observe(this, new Observer<UnitPrice>() {
            @Override
            public void onChanged(@Nullable UnitPrice unitPrice) {
                if (unitPrice != null) {
                    mUnitPrice = unitPrice;
                    bindUnitPriceToUi();
                }
            }
        });
        return view;
    }

    private void nextAction() {
        boolean error = false;
        if (mUnitPrice.getElectricity() < 0) {
            mElectricityLayout.setError(getString(R.string.electricity_unitprice_error));
            mElectricityLayout.setErrorEnabled(true);
            error = true;
        }
        if (mUnitPrice.getWater() < 0) {
            mWaterLayout.setError(getString(R.string.invalid_input_error));
            mWaterLayout.setErrorEnabled(true);
            error = true;
        }
        if (mUnitPrice.getInternet() < 0) {
            mInternetLayout.setError(getString(R.string.invalid_input_error));
            mInternetLayout.setErrorEnabled(true);
            error = true;
        }

        if (mUnitPrice.getSecurity() < 0) {
            mSecurityLayout.setError(getString(R.string.invalid_input_error));
            mSecurityLayout.setErrorEnabled(true);
            error = true;
        }

        if (mUnitPrice.getTrashCollection() < 0) {
            mTrashLayout.setError(getString(R.string.invalid_input_error));
            mTrashLayout.setErrorEnabled(true);
            error = true;
        }

        if (mUnitPrice.getTv() < 0) {
            mTvLayout.setError(getString(R.string.invalid_input_error));
            mTvLayout.setErrorEnabled(true);
            error = true;
        }

        if (error) {
            return;
        }

        mViewModel.updateUnitPrice(mUnitPrice);
        mCallBack.finalCheck();
    }

    private void bindUnitPriceToUi() {
        mEditTextElecticity.setText(String.valueOf(mUnitPrice.getElectricity()));
        mEditTextWater.setText(String.valueOf(mUnitPrice.getWater()));
        mEditTextInternet.setText(String.valueOf(mUnitPrice.getInternet()));
        mEditTextSecurity.setText(String.valueOf(mUnitPrice.getSecurity()));
        mEditTextTrash.setText(String.valueOf(mUnitPrice.getTrashCollection()));
        mEditTextTv.setText(String.valueOf(mUnitPrice.getTv()));
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
}
