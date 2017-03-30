package com.novakduc.forbega.qlnt.config.base;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.config.finance.ProjectFinanceConfigFragment;
import com.novakduc.forbega.qlnt.model.Project;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by n.thanh on 10/21/2016.
 */

public class ProjectBaseConfigFragment extends Fragment {

    public static final String TEMP_PROJECT = "com.novakduc.forbega.qlnt.tempproject";
    private static final int START_DATE_PICKED = 0;
    int mDuration = 10;
    private Project mProject;
    private EditText mEditTextAddress;
    private EditText mEditTextName;
    private EditText mEditTextStartDate;
    private EditText mEditTextEndDate;
    private EditText mEditTextDuration;
    private Button mCancel;
    private Button mNext;
    private long mStartDate;
    private String mName, mAddress;
    private TextInputLayout mLayoutName, mLayoutAddress, mLayoutDuration;
    private DiscardListener mCallback;

    public static ProjectBaseConfigFragment newInstance(Project tempProject) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(TEMP_PROJECT, tempProject);
        ProjectBaseConfigFragment fragment = new ProjectBaseConfigFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProject = (Project) getArguments().getParcelable(TEMP_PROJECT);
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
        mCallback = (DiscardListener) getActivity();
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
                showDialog(new DatePickerFragment(), START_DATE_PICKED);
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

        mCancel = (Button) view.findViewById(R.id.btCancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.discardConfirmation();
            }
        });
        mNext = (Button) view.findViewById(R.id.btNext);
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextAction();   //check information save and go to next
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //RESULT FROM DATE PICKER
        if (requestCode == START_DATE_PICKED) {
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
        if (item.getItemId() == R.id.next) {
            nextAction();
        }

        if (item.getItemId() == android.R.id.home)
            mCallback.discardConfirmation();

        return super.onOptionsItemSelected(item);
    }

    //Call when next action requested from toolbar or next button
    private void nextAction() {
        boolean error = false;
        // TODO: 3/29/2017 Project parcel has some problem. Every time access to project lead to error
        if (mName != null) {
            mProject.setName(mName);
        } else {
            mLayoutName.setError(getString(R.string.invalidName));
            mLayoutName.setErrorEnabled(true);
            error = true;
        }
        if (mAddress != null) {
            mProject.setAddress(mAddress);
        } else {
            mLayoutAddress.setError(getString(R.string.invalidAddress));
            mLayoutAddress.setErrorEnabled(true);
            error = true;
        }
        ///update start date
        mProject.setStartDate(mStartDate);
        if (mDuration > 0 && mDuration <= 100) {
            mProject.setDuration(mDuration);
        } else {
            mEditTextDuration.setError(getString(R.string.durationInputError));
            mLayoutDuration.setErrorEnabled(true);
            error = true;
        }

        if (error) {
            return;
        }

        Toast.makeText(this.getActivity(), "Reach here", Toast.LENGTH_SHORT).show();
        //////////////////////////////////////
        FragmentManager manager = getActivity().getFragmentManager();
        manager.beginTransaction().replace(R.id.fragmentContainer,
                ProjectFinanceConfigFragment.newInstance(mProject)).addToBackStack(null).commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public interface DiscardListener {
        public void discardConfirmation();
    }

    //Date picker
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        public static final String PICKED_DATE = "qlnt.config.base.startDate";

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dateOfMonth) {
            Calendar setDate = Calendar.getInstance();
            setDate.set(Calendar.YEAR, year);
            setDate.set(Calendar.MONTH, month);
            setDate.set(Calendar.DAY_OF_MONTH, dateOfMonth);

            Intent intent = new Intent();
            intent.putExtra(PICKED_DATE, setDate);

            getTargetFragment().onActivityResult(START_DATE_PICKED, AppCompatActivity.RESULT_OK, intent);
        }
    }
}
