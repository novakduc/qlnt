package com.novakduc.forbega.qlnt.ui.detail.room.checkin.add_guest;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.data.database.Guest;
import com.novakduc.forbega.qlnt.utilities.InjectorUtils;

/**
 * Created by n.thanh on 10/21/2016.
 */

public class AddGuestFragment extends android.support.v4.app.Fragment {
    public static final String GUEST_ID_KEY = "com.novakduc.forbega.qlnt.tempGuest";
    public static final String RETURN_GUEST = "com.forbega.qlnt.room.checkIn.returnGuest";
    public static final String GUEST_TO_EDIT = "com.novakduc.forbega.qlnt.guest.guestId";
    public static final int NEW_GUEST_FAKE_ID = -1;
    public static final String TYPE_KEY = "com.novakduc.forbega.qlnt.new_or_edit";
    public static final String ROOM_ID = "com.novakduc.forbega.qlnt.projectId";
    private static final String LOG_TAG = AddGuestFragment.class.getSimpleName();
    private String mName, mIdPassport, mPhoneNo;
    private boolean mIsKeyContact;
    private long mGuestId;
    private Guest mGuest;
    private TextInputLayout mLayoutName, mLayoutId, mLayoutPhoneNo;
    private AddGuestActivityListener mCallBack;
    private AddGuestFragmentViewModel mViewModel;
    private EditText edtName, edtIdPassport, edtPhoneNo;
    private CheckBox mCbKeyContact;
    private Boolean isNew;

    public static AddGuestFragment newInstance() {

        return new AddGuestFragment();
    }

    public static AddGuestFragment newInstance(long pGuestIdId) {
        Bundle bundle = new Bundle();
        bundle.putLong(GUEST_ID_KEY, pGuestIdId);
        AddGuestFragment fragment = new AddGuestFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = getActivity().getIntent();
        long roomId = intent.getLongExtra(ROOM_ID, -1);

        Log.d(LOG_TAG, "Start create loan for project id: " + roomId);

        isNew = intent.getBooleanExtra(TYPE_KEY, false);
        if (!isNew) {
            //Not create new loan, edit existed loan
            mGuestId = intent.getLongExtra(GUEST_TO_EDIT, -1);
            if (mGuestId == -1) {
                isNew = true;
            }
        }

        AddGuestViewModelFactory factory =
                InjectorUtils.provideAddGuestViewModelFactory(getActivity(), roomId, isNew);

        mViewModel = ViewModelProviders.of(this, factory).get(AddGuestFragmentViewModel.class);

        setHasOptionsMenu(true);
    }

    private void bindGuestToView(Guest guest) {
        mGuest = guest;
        mGuestId = mGuest.getId();
        if (!isNew) {
            // TODO: 9/13/2018
        }
        // TODO: 9/13/2018  
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loan_declare, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.addGuestTitle);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        final ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_navigate_before);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //*/

        mCallBack = (AddGuestActivityListener) getActivity();

        if (isNew) {
            mViewModel.getGuestLiveData().observe(this, new Observer<Guest>() {
                @Override
                public void onChanged(@Nullable Guest pGuest) {
                    bindGuestToView(pGuest);
                }
            });
        } else {
            mViewModel.getGuestLiveData(mGuestId).observe(this, new Observer<Guest>() {
                @Override
                public void onChanged(@Nullable Guest pGuest) {
                    bindGuestToView(pGuest);
                }
            });
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mCallBack.discardConfirmation(R.string.guestDiscardConfirm);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog(android.support.v4.app.DialogFragment fragment, int target) {
        fragment.setTargetFragment(this, target);
        fragment.show(getActivity().getSupportFragmentManager(),
                "dialog");
    }
}
