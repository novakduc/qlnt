package com.novakduc.forbega.qlnt.config.base;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
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
    private static final int PROJECT_DISCARD_CONFIRMATION = 1;
    int mDuration = 10;
    private Project mProject;
    private EditText mEditTextAddress;
    private EditText mEditTextName;
    private EditText mEditTextStartDate;
    private EditText mEditTextEndDate;
    private EditText mEditTextDuration;
    private Button mCancel;
    private Button mNext;
    private Calendar mStartDate;
    private String mName, mAddress;

    public static ProjectBaseConfigFragment newInstance(Project tempProject) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TEMP_PROJECT, tempProject);
        ProjectBaseConfigFragment fragment = new ProjectBaseConfigFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProject = (Project) getArguments().getSerializable(TEMP_PROJECT);
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

        mEditTextName = (EditText) view.findViewById(R.id.name);
        mEditTextAddress = (EditText) view.findViewById(R.id.address);
        mEditTextStartDate = (EditText) view.findViewById(R.id.editTextStartDate);
        mEditTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(new DatePickerFragment(), START_DATE_PICKED);
            }
        });
        mEditTextEndDate = (EditText) view.findViewById(R.id.editTextEndDate);
        mEditTextDuration = (EditText) view.findViewById(R.id.editTextDuration);
        mEditTextDuration.setText(String.valueOf(mDuration));
        final TextInputLayout txtLayoutDuration = (TextInputLayout) view.findViewById(R.id.txtLayoutDuration);
        mEditTextDuration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    mDuration = Integer.valueOf(s.toString());
                    if (mDuration <= 0 || mDuration > 100) {
                        throw new NumberFormatException();
                    } else {
                        txtLayoutDuration.setErrorEnabled(false);
                        if (mStartDate != null) {
                            Calendar endDate = (Calendar) mStartDate.clone();
                            endDate.add(Calendar.YEAR, mDuration);
                            mEditTextEndDate.setText(calendarToString(endDate));
                        } else {
                            mEditTextEndDate.setText("");
                        }
                    }
                } catch (NumberFormatException e) {
                    txtLayoutDuration.setError(getString(R.string.durationInputError));
                    txtLayoutDuration.setErrorEnabled(true);
                }
            }
        });

        mCancel = (Button) view.findViewById(R.id.btCancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 11/3/2016 Show confirmation dialog
                showDialog(new DiscardConfirmation(), PROJECT_DISCARD_CONFIRMATION);
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
            mStartDate = (Calendar) data.getSerializableExtra(DatePickerFragment.PICKED_DATE);
            mEditTextStartDate.setText(calendarToString(mStartDate));
            if (mDuration > 0 && mDuration <= 100) {
                Calendar endDate = (Calendar) mStartDate.clone();
                endDate.add(Calendar.YEAR, mDuration);
                mEditTextEndDate.setText(calendarToString(endDate));
            } else {
                mEditTextEndDate.setText("");
            }
        }
        //RESULT FROM PROJECT DISCARD DIALOG
        if (requestCode == PROJECT_DISCARD_CONFIRMATION) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                getActivity().finish();
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
        return super.onOptionsItemSelected(item);
    }

    //Call when next action requested from toolbar or next button
    private void nextAction() {
        if (mName != null) {
            mProject.setName(mName);
        }
        if (mAddress != null) {
            mProject.setAddress(mAddress);
        }
        if (mStartDate == null) {
            mProject.setStartDate(mStartDate);
        }
        if (mDuration > 0 && mDuration <= 100) {
            mProject.setDuration(mDuration);
        }
        FragmentManager manager = getActivity().getFragmentManager();
        manager.beginTransaction().replace(R.id.fragmentContainer,
                ProjectFinanceConfigFragment.newInstance(mProject)).commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //Project cancel confirmation dialog
    public static class DiscardConfirmation extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.project_create_discard)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO: 11/7/2016 OK action
                            getTargetFragment().onActivityResult(PROJECT_DISCARD_CONFIRMATION,
                                    AppCompatActivity.RESULT_OK, new Intent());
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO: 11/7/2016 No action
                        }
                    });
            return builder.create();
        }
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
