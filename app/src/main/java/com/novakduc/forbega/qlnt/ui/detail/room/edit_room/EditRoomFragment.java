package com.novakduc.forbega.qlnt.ui.detail.room.edit_room;

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
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Toast;

import com.novakduc.baselibrary.NumbericTextWatcher;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.data.database.Guest;
import com.novakduc.forbega.qlnt.data.database.RoomForRent;
import com.novakduc.forbega.qlnt.data.database.RoomService;
import com.novakduc.forbega.qlnt.data.database.RoomStatus;
import com.novakduc.forbega.qlnt.databinding.FragmentEditRoomBinding;
import com.novakduc.forbega.qlnt.ui.ConfirmationDialogFragment;
import com.novakduc.forbega.qlnt.ui.detail.room.checkin.GuestListAdapterActionHandler;
import com.novakduc.forbega.qlnt.ui.detail.room.checkin.GuestsRecyclerViewAdapter;
import com.novakduc.forbega.qlnt.ui.detail.room.checkin.add_guest.AddGuestActivity;
import com.novakduc.forbega.qlnt.ui.detail.room.checkin.add_guest.AddGuestFragment;
import com.novakduc.forbega.qlnt.utilities.ConverterUtilities;
import com.novakduc.forbega.qlnt.utilities.DatePickerFragment;
import com.novakduc.forbega.qlnt.utilities.InjectorUtils;
import com.novakduc.forbega.qlnt.utilities.SpinnerItemArrayProvider;
import com.novakduc.forbega.qlnt.workers.WorkerUtils;

import java.util.List;

public class EditRoomFragment extends Fragment implements GuestListAdapterActionHandler {
    private static final String LOG_TAG = EditRoomFragment.class.getSimpleName();
    public static final String ROOM_ID = EditRoomFragment.class.getName() + ".roomId";
    private FragmentEditRoomBinding mBinding;
    private EditRoomActivityListener mCallBack;
    private long mRoomId;
    private EditRoomViewModel mViewModel;
    private RoomForRent mRoomForRent;
    private String mRoomName;
    private long mRoomCharge;
    private GuestsRecyclerViewAdapter mGuestsRecyclerViewAdapter;
    private long mTempGuestId;
    private int mBillDate;
    private long mCheckInDate;
    private long mDepositAmount = -1;
    private long mElectricalInitialIndex = -1 , mWaterInitialIndex = - 1;
    private boolean mIsUsingTV, mIsUsingInternet;
    private boolean mIsDataValidated;
    private RoomService mElectricalService, mWaterService, mTVService, mInternetService;

    public static EditRoomFragment newInstance(long roomId) {

        Bundle args = new Bundle();
        args.putLong(ROOM_ID, roomId);
        EditRoomFragment fragment = new EditRoomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRoomId = getArguments().getLong(ROOM_ID);

        mViewModel = getViewModel();

        setHasOptionsMenu(true);
    }

    private EditRoomViewModel getViewModel() {
        Log.d(LOG_TAG, "Get edit room view model");
        EditRoomViewModelFactory factory =
                InjectorUtils.provideEditRoomViewModelFactory(getActivity(), mRoomId);

        return ViewModelProviders.of(this, factory).get(EditRoomViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_edit_room, container, false);
        View view = mBinding.getRoot();

        android.support.v7.widget.Toolbar toolbar = mBinding.appbarSection.toolbar;

        toolbar.setTitle(getResources().getString(R.string.project_create_confirm));
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            final ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setHomeAsUpIndicator(R.drawable.ic_navigate_before);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
        mCallBack = (EditRoomActivityListener) activity;

        mViewModel.getRoomForRentLiveData().observe(this, new Observer<RoomForRent>() {
            @Override
            public void onChanged(@Nullable RoomForRent pRoomForRent) {
                if (pRoomForRent != null) {
                    mRoomForRent = pRoomForRent;
                    bindRoomToUI();
                    Log.d(LOG_TAG, "Got roomForRent live data: " + pRoomForRent.toString());
                }
            }
        });

        mBinding.txtRoomName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    mBinding.txtLayoutName.setError(getString(R.string.invalidName));
                    mBinding.txtLayoutName.setErrorEnabled(true);
                    mRoomName = "";
                } else {
                    mBinding.txtLayoutName.setErrorEnabled(false);
                    mRoomName = editable.toString();
                    mRoomForRent.setName(mRoomName);
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
                    if (pGuests.isEmpty()) {
                        mBinding.textViewNotCheckInYet.setVisibility(View.VISIBLE);
                    } else {
                        mBinding.textViewNotCheckInYet.setVisibility(View.INVISIBLE);
                    }
                    Log.d(LOG_TAG, "Got guest live data: " + pGuests.toString());
                }
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
                mRoomForRent.setBillDate(mBillDate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> pAdapterView) {

            }
        });

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
                        mRoomForRent.setCharge(mRoomCharge);
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

        mBinding.editTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                showDialog(new DatePickerFragment(), DatePickerFragment.START_DATE_PICKED);
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

        mBinding.checkBoxInternet.requestFocus();

        mViewModel.getServicesLiveData().observe(this, new Observer<List<RoomService>>() {
            @Override
            public void onChanged(@Nullable List<RoomService> pRoomServices) {
                if (pRoomServices != null) {
                    for (RoomService roomService :
                            pRoomServices) {
                        switch (roomService.getType()) {
                            case WATER:
                                mWaterService = roomService;
                                mBinding.water.setText(String.valueOf(mWaterService.getNewIndex()));
                                break;
                            case ELECTRICITY:
                                mElectricalService = roomService;
                                mBinding.electricity.setText(String.valueOf(mElectricalService.getNewIndex()));
                                break;
                            case TV_CABLE:
                                mTVService = roomService;
                                mIsUsingTV = mTVService.isApplied();
                                mBinding.checkBoxTv.setChecked(mIsUsingTV);
                                break;
                            case INTERNET:
                                mInternetService = roomService;
                                mIsUsingInternet = mInternetService.isApplied();
                                mBinding.checkBoxInternet.setChecked(mIsUsingInternet);
                        }
                    }

                    Log.d(LOG_TAG, "Got services live data: " + pRoomServices.toString());
                }
            }
        });

        return view;
    }

    private void bindRoomToUI() {
        mRoomName = mRoomForRent.getName();
        mDepositAmount = mRoomForRent.getDepositAmount();
        mCheckInDate = mRoomForRent.getCheckInDate();
        mRoomCharge = mRoomForRent.getCharge();

        //////////////
        mBillDate = mRoomForRent.getBillDate();
        mBinding.txtRoomName.setText(mRoomName);
        mBinding.appbarSection.toolbar.setTitle(
                getString(R.string.edit_room_title) + " " + mRoomName);
        mBinding.roomCharge.setText(String.valueOf(mRoomCharge));

        boolean checkedIn = mRoomForRent.getStatus() != RoomStatus.AVAILABLE;
        mBinding.depositAmount.setEnabled(checkedIn);
        mBinding.editTextStartDate.setEnabled(checkedIn);
        mBinding.billDateTitle.setEnabled(checkedIn);
        mBinding.spinnerBillDate.setEnabled(checkedIn);
        mBinding.btAddGuest.setEnabled(checkedIn);
        mBinding.electricity.setEnabled(checkedIn);
        mBinding.water.setEnabled(checkedIn);
        mBinding.checkBoxTv.setEnabled(checkedIn);
        mBinding.checkBoxInternet.setEnabled(checkedIn);
        if (checkedIn) {
            mBinding.editTextStartDate.setText(
                    ConverterUtilities.calendarToString(mCheckInDate));
            mBinding.depositAmount.setText(String.valueOf(mDepositAmount));
            mBinding.spinnerBillDate.setId(mBillDate);
        }
    }

    private void showDialog(android.support.v4.app.DialogFragment fragment, int target) {
        fragment.setTargetFragment(this, target);
        fragment.show(getActivity().getSupportFragmentManager(),
                "dialog");
    }

    public void discardEditRoom() {
        //discard saving edited information
        getActivity().finish();
    }

    public void deleteGuest() {
        mViewModel.deleteGuest(mTempGuestId);
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

    @Override
    public void onDeleteAction(long id) {
        if (mGuestsRecyclerViewAdapter.getItemCount() > 1) {
            mTempGuestId = id;
            ConfirmationDialogFragment.showDialog(getString(R.string.delete_guest_confirmation),
                    EditRoomActivity.DELETE_GUEST_KEY, getFragmentManager());
        } else {
            Toast.makeText(getActivity(),
                    getString(R.string.should_not_delete_guest), Toast.LENGTH_SHORT).show();
        }
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

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.confirm_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.confirm) {
            confirmAction();
        }

        if (item.getItemId() == android.R.id.home)
            mCallBack.discardConfirmation(R.string.announce_discard_checkIn, EditRoomActivity.DISCARD_EDIT_ROOM_KEY);
        return super.onOptionsItemSelected(item);
    }

    private void confirmAction() {
        // TODO: 10/23/2018 confirm edit room
        boolean roomNameCondition, roomChargeCondition, depositCondition, electricalIndexCondition, waterIndexCondition,
                guestCondition;

        roomNameCondition = !mRoomName.isEmpty();
        roomChargeCondition = mRoomCharge > 0;
        depositCondition = mDepositAmount > 0;
        electricalIndexCondition = mElectricalInitialIndex >= 0;
        waterIndexCondition = mWaterInitialIndex >= 0;
        guestCondition = mGuestsRecyclerViewAdapter.getItemCount() != 0;

        mBinding.txtLayoutName.setError(getString(R.string.invalid_input_error));
        mBinding.txtLayoutRoomCharge.setError(getString(R.string.invalid_input_error));
        mBinding.txtLayoutDeposit.setError(getString(R.string.invalid_input_error));
        mBinding.txtLayoutElectricity.setError(getString(R.string.invalid_input_error));
        mBinding.txtLayoutWater.setError(getString(R.string.invalid_input_error));

        mBinding.txtLayoutName.setErrorEnabled(!roomNameCondition);
        mBinding.txtLayoutRoomCharge.setErrorEnabled(!roomChargeCondition);
        mBinding.txtLayoutDeposit.setErrorEnabled(!depositCondition);
        mBinding.txtLayoutElectricity.setErrorEnabled(!electricalIndexCondition);
        mBinding.txtLayoutWater.setErrorEnabled(!waterIndexCondition);

        mIsDataValidated = roomNameCondition && roomChargeCondition && depositCondition
                && electricalIndexCondition && waterIndexCondition && guestCondition;

        Log.d(LOG_TAG, "Condition: " + String.valueOf(mIsDataValidated)
                + "\nRoom charge condition: " + String.valueOf(roomChargeCondition)
                + "\nDeposit condition: " + String.valueOf(depositCondition)
                + "\nElectrical index condition: " + String.valueOf(electricalIndexCondition)
                + "\nWater index condition: " + String.valueOf(waterIndexCondition)
                + "\nGuest condition: " + String.valueOf(guestCondition)
                + " - " + String.valueOf(mGuestsRecyclerViewAdapter.getItemCount()));

        if (!guestCondition) {
            Toast.makeText(getContext(), getString(R.string.guestCheckInCondition), Toast.LENGTH_SHORT).show();
        }

        if (mIsDataValidated) {
            //check in activity
            mRoomForRent.setName(mRoomName);
            mRoomForRent.setCharge(mRoomCharge);
            mRoomForRent.setDepositAmount(mDepositAmount);
            mRoomForRent.setBillDate(mBillDate);
            mRoomForRent.setCheckInDate(mCheckInDate);

            mElectricalService.setNewIndex(mElectricalInitialIndex);
            mViewModel.updateService(mElectricalService);
            mWaterService.setNewIndex(mWaterInitialIndex);
            mViewModel.updateService(mWaterService);

            mInternetService.setApplied(mIsUsingInternet);
            mViewModel.updateService(mInternetService);

            mTVService.setApplied(mIsUsingTV);
            mViewModel.updateService(mTVService);

            mViewModel.updateRoom(mRoomForRent);
            mViewModel.confirmKeyContact();
            WorkerUtils.makeStatusNotification("Chào anh Đực!", getActivity());
            getActivity().finish();
        }
    }
}
