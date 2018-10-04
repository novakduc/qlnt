package com.novakduc.forbega.qlnt.ui.detail.room.edit_room;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.novakduc.baselibrary.NumbericTextWatcher;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.data.database.RoomForRent;
import com.novakduc.forbega.qlnt.databinding.FragmentAddRoomBinding;
import com.novakduc.forbega.qlnt.ui.detail.room.checkin.CheckInFragment;
import com.novakduc.forbega.qlnt.ui.detail.room.checkin.CheckInViewModel;
import com.novakduc.forbega.qlnt.ui.detail.room.checkin.CheckInViewModelFactory;
import com.novakduc.forbega.qlnt.utilities.ConverterUtilities;
import com.novakduc.forbega.qlnt.utilities.InjectorUtils;

public class EditRoomFragment extends CheckInFragment {
    private static final String LOG_TAG = EditRoomFragment.class.getSimpleName();

    public static EditRoomFragment newInstance(long roomId) {

        Bundle args = new Bundle();
        args.putLong(ROOM_ID, roomId);
        EditRoomFragment fragment = new EditRoomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void discardCheckIn() {
        Log.d(LOG_TAG, "Back discard edit");
        getActivity().finish();
    }

    @Override
    public CheckInViewModel getViewModel() {
        Log.d(LOG_TAG, "get Edit room view model");
        EditRoomViewModelFactory factory =
                InjectorUtils.provideEditRoomViewModelFactory(getActivity(), getRoomId());

        return  ViewModelProviders.of(this, factory).get(EditRoomViewModel.class);
    }

    @Override
    protected void bindRoomInfoToUI() {
        super.bindRoomInfoToUI();
        setBillDate(getRoomForRent().getBillDate());
        getBinding().spinnerBillDate.setId(getBillDate());
        setCheckInDate(getRoomForRent().getCheckInDate());
        getBinding().editTextStartDate.setText(ConverterUtilities.calendarToString(getCheckInDate()));
        setRoomStatus(getRoomForRent().getStatus());
        Log.d(LOG_TAG, "Binded room info to UI");
    }

    @Override
    protected void checkIn() {
        Log.d(LOG_TAG, "This is not check in fragment but Edit ROOM fragment");
    }
}
