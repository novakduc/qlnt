package com.novakduc.forbega.qlnt.ui.config.base;

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
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.data.database.Project;
import com.novakduc.forbega.qlnt.ui.config.UpdateListener;
import com.novakduc.forbega.qlnt.ui.config.finance.ProjectFinanceConfigFragment;
import com.novakduc.forbega.qlnt.ui.detail.DatePickerFragment;
import com.novakduc.forbega.qlnt.utilities.ConverterUtilities;
import com.novakduc.forbega.qlnt.utilities.InjectorUtils;

import java.util.Calendar;

/**
 * Created by n.thanh on 10/21/2016.
 */

public class ProjectBaseConfigFragment extends android.support.v4.app.Fragment {
    public static final String TEMP_PROJECT = "com.novakduc.forbega.qlnt.tempproject";
    public static final String FRAGMENT_TAG = "FINANCE_CONFIG_TAG";
    private static final String LOG_TAG = ProjectBaseConfigFragment.class.getSimpleName();
    private EditText mEditTextAddress;
    private EditText mEditTextName;
    private EditText mEditTextStartDate;
    private EditText mEditTextEndDate;
    private EditText mEditTextDuration;
    private long mStartDate;
    private String mName, mAddress;
    private int mDuration = 10;
    private TextInputLayout mLayoutName, mLayoutAddress, mLayoutDuration;
    private UpdateListener mCallback;
    private ProjectBaseFragmentViewModel mViewModel;
    private Project mProject;

    public static ProjectBaseConfigFragment newInstance() {
        return new ProjectBaseConfigFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Window window = getActivity().getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(getResources().getColor(R.color.primaryDarkColor));

        mCallback = (UpdateListener) getActivity();

        ProjectBaseViewModelFactory factory =
                InjectorUtils.provideProjectBaseViewModelFactory(getActivity());
        mViewModel = ViewModelProviders.of(this, factory).get(ProjectBaseFragmentViewModel.class);
        mViewModel.getTempProject().observe(this, new Observer<Project>() {
            @Override
            public void onChanged(@Nullable Project project) {
                if (project != null) {
                    //update views when project changes
                    Log.d(LOG_TAG, "Project ID: " + String.valueOf(project.getId()));
                    mProject = project;
                    mCallback.updateProjectId(mProject.getId());
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_base_config, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        final ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_view_list);
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
        mEditTextStartDate = view.findViewById(R.id.editTextStartDate);
        Calendar tmp = Calendar.getInstance();
        mStartDate = tmp.getTimeInMillis();
        mEditTextStartDate.setText(ConverterUtilities.calendarToString(mStartDate));
        mEditTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(new DatePickerFragment(), DatePickerFragment.START_DATE_PICKED);
            }
        });
        mEditTextEndDate = view.findViewById(R.id.editTextEndDate);
        Calendar endDate = Calendar.getInstance();
        endDate.setTimeInMillis(mStartDate);
        endDate.add(Calendar.YEAR, mDuration);
        mEditTextEndDate.setText(ConverterUtilities.calendarToString(endDate.getTimeInMillis()));

        mEditTextDuration = view.findViewById(R.id.editTextDuration);
        mEditTextDuration.setText(String.valueOf(mDuration));
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
                    mDuration = Integer.valueOf(s.toString());
                    if (mDuration <= 0 || mDuration > 100) {
                        throw new NumberFormatException();
                    } else {
                        mLayoutDuration.setErrorEnabled(false);
                        Calendar endDate = Calendar.getInstance();
                        endDate.setTimeInMillis(mStartDate);
                        endDate.add(Calendar.YEAR, mDuration);
                        mEditTextEndDate.setText(ConverterUtilities.calendarToString(endDate.getTimeInMillis()));
                    }
                } catch (NumberFormatException e) {
                    mLayoutDuration.setError(getString(R.string.durationInputError));
                    mLayoutDuration.setErrorEnabled(true);
                }
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.next_fab);
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
            mEditTextStartDate.setText(ConverterUtilities.calendarToString(mStartDate));
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
                mEditTextEndDate.setText(ConverterUtilities.calendarToString(endDate.getTimeInMillis()));
            } else {
                mEditTextEndDate.setText("");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showDialog(android.support.v4.app.DialogFragment fragment, int target) {
        fragment.setTargetFragment(this, target);
        fragment.show(getActivity().getSupportFragmentManager(),
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

        mProject.setName(mName);
        mProject.setAddress(mAddress);
        mProject.setDuration(mDuration);
        mProject.setStartDate(mStartDate);
        mViewModel.updateProject(mProject);

        Log.d(LOG_TAG, "Start finance config for project id: " + mProject.getId());
        android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().
                replace(R.id.fragmentContainer, ProjectFinanceConfigFragment.
                        newInstance(mProject.getId()), FRAGMENT_TAG).addToBackStack(null).commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.close_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
