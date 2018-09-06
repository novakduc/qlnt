package com.novakduc.forbega.qlnt.ui.detail.room.checkin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.novakduc.forbega.qlnt.utilities.ItemListAdapterActionHandler;

public class CheckInFragment extends Fragment implements ItemListAdapterActionHandler {
    public static final String ACTIVE_PROJECT_ID = "active_project_id";
    private static final String LOG_TAG = CheckInFragment.class.getSimpleName();
    public static final String ROOM_ID = CheckInFragment.class.getName() + ".roomId";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDeleteAction(long id) {

    }

    @Override
    public void onCopyAction(long id) {

    }

    @Override
    public void onEditAction(long id) {

    }

    @Override
    public void onItemClick(long id) {

    }

    public static CheckInFragment getInstance(long pRoomId) {
        Bundle bundle = new Bundle();
        bundle.putLong(ROOM_ID, pRoomId);
        CheckInFragment lCheckInFragment = new CheckInFragment();
        lCheckInFragment.setArguments(bundle);
        return lCheckInFragment;
    }
}
