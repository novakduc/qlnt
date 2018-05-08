package com.novakduc.forbega.qlnt.ui.detail.room.add_room;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.novakduc.forbega.qlnt.data.database.RoomForRent;
import com.novakduc.forbega.qlnt.utilities.InjectorUtils;

public class AddRoomFragment extends Fragment {
    public static final String ACTIVE_PROJECT_ID = "active_project_id";
    private static final String LOG_TAG = AddRoomFragment.class.getSimpleName();
    private long mProjectId;
    private AddRoomViewModel mViewModel;

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
        mProjectId = getArguments().getLong(ACTIVE_PROJECT_ID);

        AddRoomViewModelFactory factory =
                InjectorUtils.provideAddRoomViewModelFactory(getActivity(), mProjectId);

        mViewModel = ViewModelProviders.of(this, factory).get(AddRoomViewModel.class);
        mViewModel.getRoomForRentLiveData().observe(this, new Observer<RoomForRent>() {
            @Override
            public void onChanged(@Nullable RoomForRent roomForRent) {
                if (roomForRent != null) {
                    // TODO: 5/8/2018 get room id
                }
            }
        });
    }
}
