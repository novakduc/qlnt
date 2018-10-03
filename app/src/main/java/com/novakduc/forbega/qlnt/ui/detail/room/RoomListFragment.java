package com.novakduc.forbega.qlnt.ui.detail.room;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.databinding.FragmentRoomListTabBinding;
import com.novakduc.forbega.qlnt.ui.ConfirmationDialogFragment;
import com.novakduc.forbega.qlnt.ui.DialSMSFragment;
import com.novakduc.forbega.qlnt.ui.detail.ProjectDetailActivity;
import com.novakduc.forbega.qlnt.ui.detail.room.add_room.AddRoomActivity;
import com.novakduc.forbega.qlnt.ui.detail.room.checkin.CheckInActivity;
import com.novakduc.forbega.qlnt.ui.detail.room.checkin.CheckInFragment;
import com.novakduc.forbega.qlnt.ui.detail.room.edit_room.EditRoomActivity;
import com.novakduc.forbega.qlnt.ui.detail.room.edit_room.EditRoomFragment;
import com.novakduc.forbega.qlnt.utilities.InjectorUtils;

import java.util.List;

/**
 * Created by n.thanh on 9/29/2016.
 */

public class RoomListFragment extends DialSMSFragment
        implements RoomListAdapterActionHandler {
    public static final String ACTIVE_PROJECT_ID = "active_project_id";
    private static final String LOG_TAG = RoomListFragment.class.getSimpleName();
    private RoomsRecyclerViewAdapter mRoomsRecyclerViewAdapter;
    private long mActiveProject = -1;
    private RoomListFragmentViewModel mViewModel;
    private long mTempRoomId;
    private FragmentRoomListTabBinding mDataBinding;

    public static RoomListFragment getInstance(@NonNull long activeId) {
        Bundle bundle = new Bundle();
        bundle.putLong(ACTIVE_PROJECT_ID, activeId);
        RoomListFragment roomListFragment = new RoomListFragment();
        roomListFragment.setArguments(bundle);
        return roomListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            // TODO: 4/3/2018 load active project id
        } else {
            mActiveProject = getArguments().getLong(ACTIVE_PROJECT_ID);
        }

        Log.d(LOG_TAG, String.valueOf(mActiveProject));

        RoomListViewModelFactory factory =
                InjectorUtils.provideRoomListViewModelFactory(getActivity(), mActiveProject);

        mViewModel = ViewModelProviders.of(this, factory).get(RoomListFragmentViewModel.class);
    }

    @Override
    public void onStop() {
        super.onStop();

        //Save active project ID
//        SharedPreferences preferences = getActivity().getSharedPreferences(PREF_QLNT, MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putLong(ACTIVE_PROJECT_ID, mActiveProject);
//        editor.apply();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Data binding
        mDataBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_room_list_tab, container, false);

        final View view = mDataBinding.getRoot();

        //Toast.makeText(getActivity(), String.valueOf(mActiveProject), Toast.LENGTH_SHORT).show();

        final AppCompatActivity activity = (AppCompatActivity) getActivity();

        mRoomsRecyclerViewAdapter = new RoomsRecyclerViewAdapter(activity, this);
        RecyclerView recyclerView = mDataBinding.rvItemList;
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mRoomsRecyclerViewAdapter);

        //Observe view model
        mViewModel.getRoomListLiveData().observe(this, new Observer<List<ListViewRoomItem>>() {
            @Override
            public void onChanged(@Nullable List<ListViewRoomItem> listViewRoomItems) {
                if (listViewRoomItems != null) {
                    mRoomsRecyclerViewAdapter.swapList(listViewRoomItems);
                    if (listViewRoomItems.isEmpty()) {
                        mDataBinding.textViewNoRoom.setVisibility(View.VISIBLE);
                    } else
                        mDataBinding.textViewNoRoom.setVisibility(View.INVISIBLE);
                }

            }
        });

        mViewModel.getKeyContacts().observe(this, new Observer<List<GuestForRoomItemView>>() {
            @Override
            public void onChanged(@Nullable List<GuestForRoomItemView> guestForRoomItemViews) {
                if (guestForRoomItemViews != null) {
                    mRoomsRecyclerViewAdapter.updateKeyContacts(guestForRoomItemViews);
                }

            }
        });

        //Add room button
        mDataBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddRoomActivity.class);
                intent.putExtra(RoomListFragment.ACTIVE_PROJECT_ID, mActiveProject);
                startActivity(intent);
            }
        });

        return view;
    }

    public void deleteRoom() {
        mViewModel.deleteRoom(mTempRoomId);
        mDataBinding.fab.show();
    }

    @Override
    public void onDeleteAction(long roomId) {
        mTempRoomId = roomId;
        ConfirmationDialogFragment.showDialog(getString(R.string.delete_room_confirmation),
                ProjectDetailActivity.DELETE_ROOM, getFragmentManager());
    }

    @Override
    public void onCopyAction(long roomId) {
        mViewModel.copyRoom(roomId);
    }

    @Override
    public void onEditAction(long roomId) {
        Intent intent = new Intent(getActivity(), EditRoomActivity.class);
        intent.putExtra(EditRoomFragment.ROOM_ID, roomId);
        startActivity(intent);
    }

    @Override
    public void onItemClick(long id) {

    }

    @Override
    public void onCheckIn(long id) {
        Intent intent = new Intent(getActivity(), CheckInActivity.class);
        intent.putExtra(CheckInFragment.ROOM_ID, id);
        startActivity(intent);
    }

    @Override
    public void onCheckOut(long id) {

    }

    @Override
    public void onBill(long id) {

    }

    @Override
    public void onCallGuest(String phoneNo) {
        dial(phoneNo);
    }

    @Override
    public void onSendMessageToGuest(String phoneNo, String messege) {
        sendSMS(phoneNo, messege);
    }
}
