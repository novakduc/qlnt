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
import com.novakduc.forbega.qlnt.utilities.InjectorUtils;

public class EditRoomFragment extends Fragment {
    public static final String ROOM_ID = EditRoomFragment.class.getName() + ".roomId";
    private static final String LOG_TAG = EditRoomFragment.class.getSimpleName();
    private EditRoomViewModel mViewModel;
    private RoomForRent mRoomForRent;
    private long mRoomId;
    private FragmentAddRoomBinding mBinding;
    private EditRoomActivityListener mCallBack;

    public static EditRoomFragment getInstance(@NonNull long roomId) {
        Bundle bundle = new Bundle();
        bundle.putLong(ROOM_ID, roomId);
        EditRoomFragment editRoomFragment = new EditRoomFragment();
        editRoomFragment.setArguments(bundle);
        return editRoomFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRoomId = getArguments().getLong(ROOM_ID);

        EditRoomViewModelFactory factory =
                InjectorUtils.provideEditRoomViewModelFactory(getActivity(), mRoomId);

        mViewModel = ViewModelProviders.of(this, factory).get(EditRoomViewModel.class);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_add_room, container, false);
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
                } else {
                    mBinding.txtLayoutName.setErrorEnabled(false);
                    mRoomForRent.setName(editable.toString());
                }
            }
        });
        mBinding.txtRoomPrice.addTextChangedListener(new NumbericTextWatcher(mBinding.txtRoomPrice) {
            @Override
            public void executeAfterTextChanged(String value) {
                try {
                    long amount;
                    amount = Long.valueOf(value);
                    if (amount < 0) {
                        throw new NumberFormatException();
                    } else {
                        mRoomForRent.setCharge(amount);
                        mBinding.txtLayoutPrice.setErrorEnabled(false);
                    }
                } catch (NumberFormatException e) {
                    mBinding.txtLayoutPrice.setError(getString(R.string.invesment_amount_error));
                    mBinding.txtLayoutPrice.setErrorEnabled(true);
                    mRoomForRent.setCharge(-1);
                }
            }
        });

        mBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.updateRoom(mRoomForRent);
                getActivity().finish();
            }
        });

        mViewModel.getRoomForRentLiveData().observe(this, new Observer<RoomForRent>() {
            @Override
            public void onChanged(@Nullable RoomForRent roomForRent) {
                if (roomForRent != null) {
                    mRoomForRent = roomForRent;
                    mBinding.txtRoomName.setText(mRoomForRent.getName());
                    mBinding.txtRoomPrice.setText(String.valueOf(mRoomForRent.getCharge()));
                    String title = getString(R.string.edit_room_title) + " " + mRoomForRent.getName();
                    mBinding.appbarSection.toolbar.setTitle(title);
                }
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.confirm_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.confirm) {
            mViewModel.updateRoom(mRoomForRent);
            getActivity().finish();
        }

        if (item.getItemId() == android.R.id.home)
            mCallBack.discardConfirmation(R.string.announce_discard_edit_room, EditRoomActivity.DISCARD_SAVING_ROOM);
        return super.onOptionsItemSelected(item);
    }
}
