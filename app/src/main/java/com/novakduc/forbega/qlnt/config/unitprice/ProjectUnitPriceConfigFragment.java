package com.novakduc.forbega.qlnt.config.unitprice;

import android.app.Fragment;
import android.app.FragmentManager;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.config.ProjectCreateConfirmationFragment;
import com.novakduc.forbega.qlnt.config.UpdateListener;
import com.novakduc.forbega.qlnt.model.Project;
import com.novakduc.forbega.qlnt.model.UnitPrice;

/**
 * Created by n.thanh on 10/21/2016.
 */

public class ProjectUnitPriceConfigFragment extends Fragment {
    public static final String TEMP_PROJECT = "com.novakduc.forbega.qlnt.tempproject";

    private TextInputLayout mElectricityLayout, mWaterLayout, mSecurityLayout, mTrashLayout,
            mInternetLayout, mTvLayout;
    private UpdateListener mCallBack;
    private UnitPrice mUnitPrice;

    public static ProjectUnitPriceConfigFragment newInstance(Project tempProject) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(TEMP_PROJECT, tempProject);
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
        mUnitPrice = new UnitPrice();
        //mProject = (Project) getArguments().getParcelable(TEMP_PROJECT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_unitprice_config, container, false);

        mCallBack = (UpdateListener) getActivity();
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.unitPrice));
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        final ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_navigate_before);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mElectricityLayout = view.findViewById(R.id.txtLayoutElectricity);
        EditText electricityEditText = view.findViewById(R.id.electricity);
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
        EditText waterEditText = view.findViewById(R.id.water);
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
        EditText internetEditText = view.findViewById(R.id.internet);
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
        final EditText securityEditText = view.findViewById(R.id.security);
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
        EditText trashEditText = view.findViewById(R.id.trashCollention);
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
        EditText tvEditText = view.findViewById(R.id.tv);
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
                nextAction();   //to unit price config
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

        mCallBack.updateUnitPrice(mUnitPrice);
        FragmentManager manager = getActivity().getFragmentManager();
        manager.beginTransaction().replace(R.id.fragmentContainer,
                ProjectCreateConfirmationFragment.newInstance()).addToBackStack(null).commit();
    }
}
