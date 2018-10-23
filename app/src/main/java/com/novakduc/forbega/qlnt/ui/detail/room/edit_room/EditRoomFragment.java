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
import com.novakduc.forbega.qlnt.databinding.FragmentEditRoomBinding;
import com.novakduc.forbega.qlnt.ui.detail.room.checkin.CheckInActivityListener;
import com.novakduc.forbega.qlnt.ui.detail.room.checkin.CheckInFragment;
import com.novakduc.forbega.qlnt.ui.detail.room.checkin.CheckInViewModel;
import com.novakduc.forbega.qlnt.ui.detail.room.checkin.CheckInViewModelFactory;
import com.novakduc.forbega.qlnt.utilities.ConverterUtilities;
import com.novakduc.forbega.qlnt.utilities.InjectorUtils;

public class EditRoomFragment extends Fragment {
    private static final String LOG_TAG = EditRoomFragment.class.getSimpleName();
    public static final String ROOM_ID = EditRoomFragment.class.getName() + ".roomId";
    private FragmentEditRoomBinding mBinding;
    private EditRoomActivityListener mCallBack;
    private long mRoomId;
    private EditRoomViewModel mViewModel;
    private RoomForRent mRoomForRent;

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

        return  ViewModelProviders.of(this, factory).get(EditRoomViewModel.class);
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
        activity.setSupportActionBar(toolbar);
        mCallBack = (EditRoomActivityListener) activity;

        final ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_navigate_before);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mViewModel.getRoomForRentLiveData().observe(this, new Observer<RoomForRent>() {
            @Override
            public void onChanged(@Nullable RoomForRent pRoomForRent) {
                if (pRoomForRent != null) {
                    mRoomForRent = pRoomForRent;
                    bindRoomToUI();
                }
            }
        });

        return view;
    }

    private void bindRoomToUI() {
        mBinding.txtRoomName.setText(mRoomForRent.getName());
        mBinding.appbarSection.toolbar.setTitle(
                getString(R.string.edit_room_title) + " " + mRoomForRent.getName());
        mBinding.roomCharge.setText(String.valueOf(mRoomForRent.getCharge()));
    }

    public void discardEditRoom() {
        //discard saving edited information
        getActivity().finish();
    }

    public void deleteGuest() {
        // TODO: 10/23/2018 delete guest
    }
}
