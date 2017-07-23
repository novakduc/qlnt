package com.novakduc.forbega.qlnt.config.unitprice;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.unitPrice));
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        final ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_navigate_before);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mElectricityLayout = (TextInputLayout) view.findViewById(R.id.txtLayoutElectricity);
        EditText electricityEditText = (EditText) view.findViewById(R.id.electricity);
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
                }
            }
        });

        mWaterLayout = (TextInputLayout) view.findViewById(R.id.txtLayoutWater);
        EditText waterEditText = (EditText) view.findViewById(R.id.water);
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
                }
            }
        });

        mInternetLayout = (TextInputLayout) view.findViewById(R.id.txtLayoutInternet);
        EditText internetEditText = (EditText) view.findViewById(R.id.internet);
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
                }
            }
        });

        mSecurityLayout = (TextInputLayout) view.findViewById(R.id.txtLayoutSecurity);
        final EditText securityEditText = (EditText) view.findViewById(R.id.security);
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
                }
            }
        });

        mTrashLayout = (TextInputLayout) view.findViewById(R.id.txtLayoutTrashCollection);
        EditText trashEditText = (EditText) view.findViewById(R.id.trashCollention);
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
                }
            }
        });

        mTvLayout = (TextInputLayout) view.findViewById(R.id.txtLayoutTv);
        EditText tvEditText = (EditText) view.findViewById(R.id.tv);
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
                }
            }
        });

        Button next = (Button) view.findViewById(R.id.btNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextAction();   //to unit price config
            }
        });
        Button cancel = (Button) view.findViewById(R.id.btCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBack.discardConfirmation();
            }
        });
        Button back = (Button) view.findViewById(R.id.btBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
                //getActivity().onBackPressed();
            }
        });
        return view;
    }

    private void nextAction() {
        // TODO: 7/21/2017 next action
    }
}
