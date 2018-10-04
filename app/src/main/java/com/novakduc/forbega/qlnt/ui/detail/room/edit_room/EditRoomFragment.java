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

    @Override
    public void discardCheckIn() {
        getActivity().finish();
    }

    @Override
    public CheckInViewModel getViewModel() {
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
    }

    @Override
    protected void checkIn() {

    }
}
