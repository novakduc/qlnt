package com.novakduc.forbega.qlnt.ui.detail.room.checkin.add_guest;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.google.android.material.textfield.TextInputLayout;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.data.database.Guest;
import com.novakduc.forbega.qlnt.databinding.FragmentGuestDetailBinding;
import com.novakduc.forbega.qlnt.ui.DialSMSFragment;
import com.novakduc.forbega.qlnt.utilities.InjectorUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * Created by n.thanh on 10/21/2016.
 */

public class AddGuestFragment extends DialSMSFragment {
    public static final String GUEST_ID_KEY = "com.novakduc.forbega.qlnt.tempGuest";
    public static final String RETURN_GUEST = "com.forbega.qlnt.room.checkIn.returnGuest";
    public static final String GUEST_TO_EDIT = "com.novakduc.forbega.qlnt.guest.guestId";
    public static final int NEW_GUEST_FAKE_ID = -1;
    public static final String TYPE_KEY = "com.novakduc.forbega.qlnt.new_or_edit";
    public static final String ROOM_ID = "com.novakduc.forbega.qlnt.roomId";
    private static final String LOG_TAG = AddGuestFragment.class.getSimpleName();
    private String mName, mIdPassport, mPhoneNo;
    private boolean mIsKeyContact;
    private long mGuestId;
    private Guest mGuest;
    private TextInputLayout mLayoutName, mLayoutId, mLayoutPhoneNo;
    private AddGuestActivityListener mCallBack;
    private AddGuestFragmentViewModel mViewModel;
    private Boolean isNew;
    private FragmentGuestDetailBinding mBinding;

    public static AddGuestFragment newInstance() {

        return new AddGuestFragment();
    }

    public static AddGuestFragment newInstance(long pGuestId) {
        Bundle bundle = new Bundle();
        bundle.putLong(GUEST_ID_KEY, pGuestId);
        AddGuestFragment fragment = new AddGuestFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = getActivity().getIntent();
        long roomId = intent.getLongExtra(ROOM_ID, -1);

        Log.d(LOG_TAG, "Start add guest for room id: " + roomId);

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
            mName = guest.getName();
            mBinding.edtInputGuestName.setText(mName);
            mIdPassport = guest.getGuestId();
            mBinding.edtGuestId.setText(mIdPassport);
            mPhoneNo = guest.getPhoneNumber();
            mBinding.edtPhone.setText(mPhoneNo);
            mIsKeyContact = guest.isKeyContact();
            mBinding.cbxKeyContact.setChecked(mIsKeyContact);
        } else {
            // TODO: 9/26/2018  
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_guest_detail, container, false);
        View view = mBinding.getRoot();
        androidx.appcompat.widget.Toolbar toolbar = mBinding.appbarSection.toolbar;
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

        mBinding.edtInputGuestName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence pCharSequence, int pI, int pI1, int pI2) {

            }

            @Override
            public void onTextChanged(CharSequence pCharSequence, int pI, int pI1, int pI2) {

            }

            @Override
            public void afterTextChanged(Editable pEditable) {
                if (pEditable.length() != 0) {
                    mName = pEditable.toString();
                    mBinding.txtLayoutName.setErrorEnabled(false);
                } else {
                    mName = null;
                    mBinding.txtLayoutName.setError(getString(R.string.invalid_input_error));
                    mBinding.txtLayoutName.setErrorEnabled(true);
                }
            }
        });

        mBinding.edtGuestId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence pCharSequence, int pI, int pI1, int pI2) {

            }

            @Override
            public void onTextChanged(CharSequence pCharSequence, int pI, int pI1, int pI2) {

            }

            @Override
            public void afterTextChanged(Editable pEditable) {
                if (pEditable.length() != 0) {
                    mIdPassport = pEditable.toString();
                } else {
                    mIdPassport = null;
                }
            }
        });

        mBinding.cbxKeyContact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton pCompoundButton, boolean pB) {
                mIsKeyContact = pB;
            }
        });

        mBinding.edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence pCharSequence, int pI, int pI1, int pI2) {

            }

            @Override
            public void onTextChanged(CharSequence pCharSequence, int pI, int pI1, int pI2) {

            }

            @Override
            public void afterTextChanged(Editable pEditable) {
                if (pEditable != null) {
                    mPhoneNo = pEditable.toString();
                } else {
                    mPhoneNo = null;
                }

            }
        });

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

        mBinding.imageButtonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                if (mPhoneNo != null) {
                    dial(mPhoneNo);
                }
            }
        });

        mBinding.imageButtonMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                sendSMS(mPhoneNo, getString(R.string.sms_tmp_appreciation));
            }
        });

        mBinding.btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                saveGuest();
            }
        });

        mBinding.btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                mCallBack.discardConfirmation(R.string.guestDiscardConfirm, AddGuestActivity.DISCARD_ADD_GUEST);
            }
        });

        return view;
    }

    private void saveGuest() {
        if (mName != null) {
            mGuest.setName(mName);
            mGuest.setGuestId(mIdPassport);
            mGuest.setPhoneNumber(mPhoneNo);
            mGuest.setKeyContact(mIsKeyContact);
        } else {
            mBinding.txtLayoutName.setError(getString(R.string.invalid_input_error));
            mBinding.txtLayoutName.setErrorEnabled(true);
            return;
        }
        mViewModel.updateGuest(mGuest);
        getActivity().finish();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.confirm_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.confirm) {
            saveGuest();
        }

        if (item.getItemId() == android.R.id.home) {
            mCallBack.discardConfirmation(R.string.guestDiscardConfirm, AddGuestActivity.DISCARD_ADD_GUEST);
        }
        return super.onOptionsItemSelected(item);
    }
}
