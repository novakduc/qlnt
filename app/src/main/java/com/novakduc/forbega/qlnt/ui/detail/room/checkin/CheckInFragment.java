package com.novakduc.forbega.qlnt.ui.detail.room.checkin;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.novakduc.baselibrary.NumbericTextWatcher;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.data.database.CostType;
import com.novakduc.forbega.qlnt.data.database.Guest;
import com.novakduc.forbega.qlnt.data.database.RoomForRent;
import com.novakduc.forbega.qlnt.data.database.RoomStatus;
import com.novakduc.forbega.qlnt.databinding.FragmentCheckinBinding;
import com.novakduc.forbega.qlnt.ui.ConfirmationDialogFragment;
import com.novakduc.forbega.qlnt.ui.config.finance.LoansAdapter;
import com.novakduc.forbega.qlnt.ui.config.finance.loan.LoanDeclareActivity;
import com.novakduc.forbega.qlnt.ui.config.finance.loan.LoanDeclareFragment;
import com.novakduc.forbega.qlnt.ui.detail.room.RoomsRecyclerViewAdapter;
import com.novakduc.forbega.qlnt.ui.detail.room.checkin.add_guest.AddGuestActivity;
import com.novakduc.forbega.qlnt.ui.detail.room.checkin.add_guest.AddGuestFragment;
import com.novakduc.forbega.qlnt.utilities.ConverterUtilities;
import com.novakduc.forbega.qlnt.utilities.DatePickerFragment;
import com.novakduc.forbega.qlnt.utilities.InjectorUtils;
import com.novakduc.forbega.qlnt.utilities.ItemListAdapterActionHandler;
import com.novakduc.forbega.qlnt.utilities.SpinnerItemArrayProvider;

import java.util.ArrayList;
import java.util.List;

public class CheckInFragment extends Fragment implements GuestListAdapterActionHandler{
    public static final String ACTIVE_PROJECT_ID = "active_project_id";
    private static final String LOG_TAG = CheckInFragment.class.getSimpleName();
    public static final String ROOM_ID = CheckInFragment.class.getName() + ".roomId";
    private CheckInViewModel mViewModel;
    private RoomForRent mRoomForRent;
    private long mRoomId;
    private FragmentCheckinBinding mBinding;
    private CheckInActivityListener mCallBack;
    private long mCheckInDate;
    private long mRoomCharge = -1;
    private long mDepositAmount = -1;
    private int mBillDate;
    private long mElectricalInitialIndex, mWaterInitialIndex;
    private boolean mIsUsingTV, mIsUsingInternet, mIsValidCheckInInfo;
    private GuestsRecyclerViewAdapter mGuestsRecyclerViewAdapter;
    private long mTempGuestId;

    @Override

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRoomId = getArguments().getLong(ROOM_ID);

        mIsValidCheckInInfo = false;

        CheckInViewModelFactory factory =
                InjectorUtils.provideCheckInViewModelFactory(getActivity(), mRoomId);

        mViewModel = ViewModelProviders.of(this, factory).get(CheckInViewModel.class);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_checkin, container, false);
        View view = mBinding.getRoot();

        android.support.v7.widget.Toolbar toolbar = mBinding.appbarSection.toolbar;

        toolbar.setTitle(getResources().getString(R.string.project_create_confirm));
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        mCallBack = (CheckInActivityListener) activity;

        final ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_navigate_before);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mCheckInDate = System.currentTimeMillis();

        EditText roomChargeEditText = mBinding.roomCharge;
        roomChargeEditText.addTextChangedListener(new NumbericTextWatcher(roomChargeEditText) {
            @Override
            public void executeAfterTextChanged(String value) {
                try {
                    mRoomCharge = Long.valueOf(value);
                    if (mRoomCharge < 0) {
                        throw new NumberFormatException();
                    } else {
                        mBinding.txtLayoutRoomCharge.setErrorEnabled(false);
                    }
                } catch (NumberFormatException pE) {
                    mBinding.txtLayoutRoomCharge.setError(getString(R.string.invalid_input_error));
                    mBinding.txtLayoutRoomCharge.setErrorEnabled(true);
                    mRoomCharge = -1;
                }
                Log.d(LOG_TAG, value + ": " + String.valueOf(mRoomCharge));
            }
        });

        EditText depositAmountEditText = mBinding.depositAmount;
        depositAmountEditText.addTextChangedListener(new NumbericTextWatcher(depositAmountEditText) {
            @Override
            public void executeAfterTextChanged(String value) {
                try {
                    mDepositAmount = Long.valueOf(value);
                    if (mDepositAmount < 0) {
                        throw new NumberFormatException();
                    } else {
                        mBinding.txtLayoutDeposit.setErrorEnabled(false);
                    }
                } catch (NumberFormatException pE) {
                    mBinding.txtLayoutDeposit.setError(getString(R.string.invalid_input_error));
                    mBinding.txtLayoutDeposit.setErrorEnabled(true);
                    mDepositAmount = -1;
                }
            }
        });

        mBinding.editTextStartDate.setText(ConverterUtilities.calendarToString(mCheckInDate));
        mBinding.editTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                showDialog(new DatePickerFragment(), DatePickerFragment.START_DATE_PICKED);
            }
        });

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,
                SpinnerItemArrayProvider.calendarDateArrayAdapter(31));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.spinnerBillDate.setAdapter(arrayAdapter);
        mBinding.spinnerBillDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> pAdapterView, View pView, int pI, long pL) {
                mBillDate = pI + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> pAdapterView) {

            }
        });

        mBinding.electricity.addTextChangedListener(new NumbericTextWatcher(mBinding.electricity) {
            @Override
            public void executeAfterTextChanged(String value) {
                try {
                    mElectricalInitialIndex = Long.valueOf(value);
                    if (mElectricalInitialIndex < 0) {
                        throw new NumberFormatException();
                    } else {
                        mBinding.txtLayoutElectricity.setErrorEnabled(false);
                    }
                } catch (NumberFormatException pE) {
                    mBinding.txtLayoutElectricity.setError(getString(R.string.invalid_input_error));
                    mBinding.txtLayoutElectricity.setErrorEnabled(true);
                    mElectricalInitialIndex = -1;
                }
                Log.d(LOG_TAG, value + ": " + String.valueOf(mElectricalInitialIndex));
            }
        });

        mBinding.water.addTextChangedListener(new NumbericTextWatcher(mBinding.water) {
            @Override
            public void executeAfterTextChanged(String value) {
                try {
                    mWaterInitialIndex = Long.valueOf(value);
                    if (mWaterInitialIndex < 0) {
                        throw new NumberFormatException();
                    } else {
                        mBinding.txtLayoutWater.setErrorEnabled(false);
                    }
                } catch (NumberFormatException pE) {
                    mBinding.txtLayoutWater.setError(getString(R.string.invalid_input_error));
                    mBinding.txtLayoutWater.setErrorEnabled(true);
                    mWaterInitialIndex = -1;
                }
                Log.d(LOG_TAG, value + ": " + String.valueOf(mWaterInitialIndex));
            }
        });

        mBinding.checkBoxInternet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton pCompoundButton, boolean pB) {
                mIsUsingInternet = pB;
            }
        });

        mBinding.checkBoxTv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton pCompoundButton, boolean pB) {
                mIsUsingTV = pB;
            }
        });

        mBinding.btAddGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                Intent intent = new Intent(getActivity(), AddGuestActivity.class);
                intent.putExtra(AddGuestFragment.ROOM_ID, mRoomId);
                startActivity(intent);
            }
        });

        mViewModel.getRoomForRentLiveData().observe(this, new Observer<RoomForRent>() {
            @Override
            public void onChanged(@Nullable RoomForRent pRoomForRent) {
                if (pRoomForRent != null) {
                    mRoomForRent = pRoomForRent;
                    bindRoomInfoToUI();
                }
            }
        });

        mGuestsRecyclerViewAdapter = new GuestsRecyclerViewAdapter(activity, this);
        RecyclerView recyclerView = mBinding.guestList;
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mGuestsRecyclerViewAdapter);

        mViewModel.getGuestListLiveData().observe(this, new Observer<List<Guest>>() {
            @Override
            public void onChanged(@Nullable List<Guest> pGuests) {
                if (pGuests != null) {
                    mGuestsRecyclerViewAdapter.swapList(pGuests);
                }
            }
        });

        return view;
    }

    private void bindRoomInfoToUI() {
        String title = getString(R.string.checkIn_room_title) + " " + mRoomForRent.getName();
        mBinding.appbarSection.toolbar.setTitle(title);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //RESULT FROM DATE PICKER
        if (requestCode == DatePickerFragment.START_DATE_PICKED) {
            if (resultCode != AppCompatActivity.RESULT_OK) return;
            long pickDate = data.getLongExtra(DatePickerFragment.PICKED_DATE, -1);
            if (pickDate == -1) {
                return;
            }
            mCheckInDate = pickDate;
            mBinding.editTextStartDate.setText(ConverterUtilities.calendarToString(mCheckInDate));
            TypedArray themeArray = getActivity().getTheme().obtainStyledAttributes(
                    new int[]{android.R.attr.editTextColor});
            try {
                int index = 0;
                int defaultColourValue = 0;
                int editTextColour = themeArray.getColor(index, defaultColourValue);
                mBinding.editTextStartDate.setTextColor(editTextColour);
            } finally {
                // Calling recycle() is important. Especially if you use alot of TypedArrays
                themeArray.recycle();
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
    public void onDeleteAction(long id) {
        mTempGuestId = id;
        ConfirmationDialogFragment.showDialog(getString(R.string.delete_guest_confirmation),
                CheckInActivity.DELETE_GUEST_KEY, getFragmentManager());
    }

    @Override
    public void onCopyAction(long id) {

    }

    @Override
    public void onEditAction(long id) {
        Intent intent = new Intent(getActivity(), AddGuestActivity.class);
        intent.putExtra(AddGuestFragment.ROOM_ID, mRoomId);
        intent.putExtra(AddGuestFragment.GUEST_TO_EDIT, id);
        startActivity(intent);
    }

    @Override
    public void onItemClick(long id) {
        Intent intent = new Intent(getActivity(), AddGuestActivity.class);
        intent.putExtra(AddGuestFragment.ROOM_ID, mRoomId);
        intent.putExtra(AddGuestFragment.GUEST_TO_EDIT, id);
        startActivity(intent);
    }

    public static CheckInFragment getInstance(long pRoomId) {
        Bundle bundle = new Bundle();
        bundle.putLong(ROOM_ID, pRoomId);
        CheckInFragment lCheckInFragment = new CheckInFragment();
        lCheckInFragment.setArguments(bundle);
        return lCheckInFragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.confirm_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.confirm) {
            // TODO: 9/6/2018  
        }

        if (item.getItemId() == android.R.id.home)
            mCallBack.discardConfirmation(R.string.announce_discard_checkIn, CheckInActivity.DISCARD_CHECKIN_KEY);
        return super.onOptionsItemSelected(item);
    }

    private void checkIn() {
        boolean roomChargeCondition, depositCondition, electricalIndexCondition, waterIndexCondition,
                guestCondition;

        roomChargeCondition = mRoomCharge > 0;
        depositCondition = mDepositAmount > 0;
        electricalIndexCondition = mElectricalInitialIndex >= 0;
        waterIndexCondition = mWaterInitialIndex >= 0;
        guestCondition = mGuestsRecyclerViewAdapter.getItemCount() != 0;

        mBinding.txtLayoutRoomCharge.setError(getString(R.string.invalid_input_error));
        mBinding.txtLayoutDeposit.setError(getString(R.string.invalid_input_error));
        mBinding.txtLayoutElectricity.setError(getString(R.string.invalid_input_error));
        mBinding.txtLayoutWater.setError(getString(R.string.invalid_input_error));

        mBinding.txtLayoutRoomCharge.setErrorEnabled(!roomChargeCondition);
        mBinding.txtLayoutDeposit.setErrorEnabled(!depositCondition);
        mBinding.txtLayoutElectricity.setErrorEnabled(!electricalIndexCondition);
        mBinding.txtLayoutWater.setErrorEnabled(!waterIndexCondition);

        mIsValidCheckInInfo = roomChargeCondition;  //need to input room charge
        mIsValidCheckInInfo = mIsUsingInternet && depositCondition; //need to input deposit amount
        mIsValidCheckInInfo = mIsValidCheckInInfo && electricalIndexCondition; //need to input init electrical index
        mIsValidCheckInInfo = mIsValidCheckInInfo && waterIndexCondition;  //need to input init water index
        mIsValidCheckInInfo = mIsValidCheckInInfo && guestCondition; //at least 1 guest

        if (mIsValidCheckInInfo) {
            // TODO: 9/19/2018 checkin action
            //check in activity
            mRoomForRent.setCharge(mRoomCharge);
            mRoomForRent.setDepositAmount(mDepositAmount);
            mRoomForRent.setBillDate(mBillDate);
            mRoomForRent.setCheckInDate(mCheckInDate);
            mRoomForRent.setStatus(RoomStatus.NORMAL);

            mViewModel.addElectricityService();
            mViewModel.addWaterService();

            if (mIsUsingInternet) {
                mViewModel.addInternetService();
            }

            if (mIsUsingTV) {
                mViewModel.addTvService();
            }

            mViewModel.updateRoom(mRoomForRent);
        }
    }

    public void deleteGuest() {
        mViewModel.deleteGuest(mTempGuestId);
    }

    public void discardCheckIn() {
        //Delete all guest just created
        for (int i = 0; i < mGuestsRecyclerViewAdapter.getItemCount(); i++) {
            Guest guest = mGuestsRecyclerViewAdapter.getValueAt(i);
            if (guest.getCheckOutDate() == -1) {
                Log.d(LOG_TAG, "Delete guest: " + String.valueOf(guest.getId()));
                mViewModel.deleteGuest(guest.getId());
            }
        }

        //Delete all service just created
        // TODO: 10/2/2018 detele all services

        getActivity().finish();
    }
}
