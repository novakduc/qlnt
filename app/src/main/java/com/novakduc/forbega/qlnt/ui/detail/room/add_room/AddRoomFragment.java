package com.novakduc.forbega.qlnt.ui.detail.room.add_room;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.novakduc.baselibrary.NumbericTextWatcher;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.data.database.RoomForRent;
import com.novakduc.forbega.qlnt.databinding.FragmentAddRoomBinding;
import com.novakduc.forbega.qlnt.utilities.ActivityListener;
import com.novakduc.forbega.qlnt.utilities.InjectorUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class AddRoomFragment extends Fragment {
    public static final String ACTIVE_PROJECT_ID = "active_project_id";
    private static final String LOG_TAG = AddRoomFragment.class.getSimpleName();
    private AddRoomViewModel mViewModel;
    private RoomForRent mRoomForRent;
    private FragmentAddRoomBinding mBinding;
    private ActivityListener mCallBack;

    public static AddRoomFragment getInstance(@NonNull long projectId) {
        Bundle bundle = new Bundle();
        bundle.putLong(ACTIVE_PROJECT_ID, projectId);
        AddRoomFragment addRoomFragment = new AddRoomFragment();
        addRoomFragment.setArguments(bundle);
        return addRoomFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long projectId = getArguments().getLong(ACTIVE_PROJECT_ID);

        AddRoomViewModelFactory factory =
                InjectorUtils.provideAddRoomViewModelFactory(getActivity(), projectId);

        mViewModel = ViewModelProviders.of(this, factory).get(AddRoomViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_add_room, container, false);
        View view = mBinding.getRoot();

        Toolbar toolbar = mBinding.appbarSection.toolbar;

        toolbar.setTitle(getResources().getString(R.string.titleAddRoom));
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        mCallBack = (AddRoomActivity) activity;

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
                    Log.d(LOG_TAG, "Error value format: " + value);
                }
            }
        });

        mBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.addRoom(mRoomForRent);
                getActivity().finish();
            }
        });

        mViewModel.getRoomForRentLiveData().observe(this, new Observer<RoomForRent>() {
            @Override
            public void onChanged(@Nullable RoomForRent roomForRent) {
                if (roomForRent != null) {
                    mRoomForRent = roomForRent;
                    mViewModel.setRoomId(mRoomForRent.getId());
                }
            }
        });

        return view;
    }
}
