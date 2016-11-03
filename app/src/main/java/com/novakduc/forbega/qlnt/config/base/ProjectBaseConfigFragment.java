package com.novakduc.forbega.qlnt.config.base;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;

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
    private Project mProject;
    private EditText mEditTextAddress;
    private EditText mEditTextName;
    private EditText mEditTextStartDate;
    private EditText mEditTextEndDate;
    private NumberPicker mNumberPickerDuration;
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
                showDatePicker();
            }
        });
        mEditTextEndDate = (EditText) view.findViewById(R.id.editTextEndDate);
        mNumberPickerDuration =
                (NumberPicker) view.findViewById(R.id.duration);
        mNumberPickerDuration.setMinValue(0);
        mNumberPickerDuration.setMaxValue(100);
        mNumberPickerDuration.setValue(10);
        mNumberPickerDuration.setWrapSelectorWheel(true);
        mNumberPickerDuration.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mNumberPickerDuration.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                Calendar endDate = (Calendar) mStartDate.clone();
                endDate.add(Calendar.YEAR, newVal);
            }
        });

        mCancel = (Button) view.findViewById(R.id.btCancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 11/3/2016 Show confirmation dialog
                getActivity().finish();
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
        if (resultCode != AppCompatActivity.RESULT_OK) return;
        if (requestCode == START_DATE_PICKED) {
            mStartDate = (Calendar) data.getSerializableExtra(DatePickerFragment.PICKED_DATE);
            Date date = mStartDate.getTime();
            DateFormat format = SimpleDateFormat.getDateInstance();
            String s = format.format(date);
            mEditTextStartDate.setText(s);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showDatePicker() {
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.setTargetFragment(this, START_DATE_PICKED);
        dialogFragment.show(getActivity().getFragmentManager(),
                "startDatePicker");
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
        // TODO: 11/3/2016 Check base information and save to building project
        if (mName == null) {
            // TODO: 11/3/2016
            return;
        }
        if (mAddress == null) {
            // TODO: 11/3/2016
            return;
        }
        if (mStartDate == null) {
            // TODO: 11/3/2016 remind dialog
            return;
        }
        mProject.setStartDate(mStartDate);
        mProject.setDuration(mNumberPickerDuration.getValue());
        FragmentManager manager = getActivity().getFragmentManager();
        manager.beginTransaction().replace(R.id.fragmentContainer,
                ProjectFinanceConfigFragment.newInstance(mProject)).commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
