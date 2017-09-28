package com.novakduc.forbega.qlnt.config.base;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.TypedArray;
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
import android.widget.Button;
import android.widget.EditText;

import com.novakduc.forbega.qlnt.DatePickerFragment;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.config.ProjectConfigurationActivity;
import com.novakduc.forbega.qlnt.config.UpdateListener;
import com.novakduc.forbega.qlnt.config.finance.ProjectFinanceConfigFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by n.thanh on 10/21/2016.
 */

public class ProjectBaseConfigFragment extends Fragment {

    public static final String TEMP_PROJECT = "com.novakduc.forbega.qlnt.tempproject";
    int mDuration = 10;
    private EditText mEditTextAddress;
    private EditText mEditTextName;
    private EditText mEditTextStartDate;
    private EditText mEditTextEndDate;
    private EditText mEditTextDuration;
    private long mStartDate;
    private String mName, mAddress;
    private TextInputLayout mLayoutName, mLayoutAddress, mLayoutDuration;
    private UpdateListener mCallback;

    public static ProjectBaseConfigFragment newInstance() {
        return new ProjectBaseConfigFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_base_config, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        final ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_view_list);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mCallback = (UpdateListener) getActivity();
        mLayoutName = (TextInputLayout) view.findViewById(R.id.txtLayoutName);
        mEditTextName = (EditText) view.findViewById(R.id.name);
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
                    mName = null;
                } else {
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
                } else mLayoutName.setErrorEnabled(false);
            }
        });
        mLayoutAddress = (TextInputLayout) view.findViewById(R.id.txtLayoutAddress);
        mEditTextAddress = (EditText) view.findViewById(R.id.address);
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
                    mAddress = null;
                } else {
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
                } else mLayoutAddress.setErrorEnabled(false);
            }
        });
        mEditTextStartDate = (EditText) view.findViewById(R.id.editTextStartDate);
        Calendar tmp = Calendar.getInstance();
        mStartDate = tmp.getTimeInMillis();
        mEditTextStartDate.setText(calendarToString(tmp));
        mEditTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(new DatePickerFragment(), DatePickerFragment.START_DATE_PICKED);
            }
        });
        mEditTextEndDate = (EditText) view.findViewById(R.id.editTextEndDate);
        Calendar endDate = Calendar.getInstance();
        endDate.setTimeInMillis(mStartDate);
        endDate.add(Calendar.YEAR, mDuration);
        mEditTextEndDate.setText(calendarToString(endDate));

        mEditTextDuration = (EditText) view.findViewById(R.id.editTextDuration);
        mEditTextDuration.setText(String.valueOf(mDuration));
        mLayoutDuration = (TextInputLayout) view.findViewById(R.id.txtLayoutDuration);
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
                    mDuration = Integer.valueOf(s.toString());
                    if (mDuration <= 0 || mDuration > 100) {
                        throw new NumberFormatException();
                    } else {
                        mLayoutDuration.setErrorEnabled(false);
                        Calendar endDate = Calendar.getInstance();
                        endDate.setTimeInMillis(mStartDate);
                            endDate.add(Calendar.YEAR, mDuration);
                            mEditTextEndDate.setText(calendarToString(endDate));
                    }
                } catch (NumberFormatException e) {
                    mLayoutDuration.setError(getString(R.string.durationInputError));
                    mLayoutDuration.setErrorEnabled(true);
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.next_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextAction();
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
            mStartDate = tmpCalendar.getTimeInMillis();
            mEditTextStartDate.setText(calendarToString(tmpCalendar));
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
            if (mDuration > 0 && mDuration <= 100) {
                Calendar endDate = (Calendar) tmpCalendar.clone();
                endDate.add(Calendar.YEAR, mDuration);
                mEditTextEndDate.setText(calendarToString(endDate));
            } else {
                mEditTextEndDate.setText("");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String calendarToString(Calendar calendar) {
        Date date = calendar.getTime();
        DateFormat format = SimpleDateFormat.getDateInstance();
        return format.format(date);
    }

    private void showDialog(DialogFragment fragment, int target) {
        fragment.setTargetFragment(this, target);
        fragment.show(getActivity().getFragmentManager(),
                "dialog");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.close) {
            mCallback.discardConfirmation(R.string.project_create_discard); //close action;
        }

        if (item.getItemId() == android.R.id.home)
            mCallback.discardConfirmation(R.string.project_create_discard);

        return super.onOptionsItemSelected(item);
    }

    //Call when next action requested from toolbar or next button
    private void nextAction() {
        boolean error = false;
        if (mName == null) {
            mLayoutName.setError(getString(R.string.invalidName));
            mLayoutName.setErrorEnabled(true);
            error = true;
        }
        if (mAddress == null) {
            mLayoutAddress.setError(getString(R.string.invalidAddress));
            mLayoutAddress.setErrorEnabled(true);
            error = true;
        }
        if (mDuration <= 0 || mDuration > 100) {
            mEditTextDuration.setError(getString(R.string.durationInputError));
            mLayoutDuration.setErrorEnabled(true);
            error = true;
        }

        if (error) {
            return;
        }

        mCallback.updateBase(mName, mAddress, mDuration, mStartDate);
        FragmentManager manager = getActivity().getFragmentManager();
        manager.beginTransaction().replace(R.id.fragmentContainer,
                ProjectFinanceConfigFragment.newInstance()).addToBackStack(null).commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.close_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
