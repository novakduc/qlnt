package com.novakduc.forbega.qlnt.ui.detail.room;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import com.novakduc.forbega.qlnt.data.database.GuestForRoomItemView;
import com.novakduc.forbega.qlnt.data.database.ListViewRoomItem;
import com.novakduc.forbega.qlnt.databinding.FragmentRoomListTabBinding;
import com.novakduc.forbega.qlnt.ui.ConfirmationDialogFragment;
import com.novakduc.forbega.qlnt.utilities.InjectorUtils;
import com.novakduc.forbega.qlnt.utilities.ItemListAdapterActionHandler;

import java.util.List;

/**
 * Created by n.thanh on 9/29/2016.
 */

public class RoomListFragment extends android.support.v4.app.Fragment
        implements ItemListAdapterActionHandler {
    public static final String PREF_QLNT = "com.novak.forbequ.qlnt";
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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
                mRoomsRecyclerViewAdapter.swapList(listViewRoomItems);
            }
        });

        mViewModel.getKeyContacts().observe(this, new Observer<List<GuestForRoomItemView>>() {
            @Override
            public void onChanged(@Nullable List<GuestForRoomItemView> guestForRoomItemViews) {
                mRoomsRecyclerViewAdapter.updateKeyContacts(guestForRoomItemViews);
                // TODO: 5/5/2018 utilizing RoomList class
            }
        });

        //Add room button
        mDataBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 5/5/2018 add new room
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
        Bundle bundle = new Bundle();
        //dialog title in bundle
        bundle.putString(ConfirmationDialogFragment.MESSAGE,
                getResources().getString(R.string.delete_room_confirmation));
        android.support.v4.app.DialogFragment dialogFragment = new ConfirmationDialogFragment();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getActivity().getSupportFragmentManager(), "discardConfirm");
    }

    @Override
    public void onCopyAction(long roomId) {
        mViewModel.copyRoom(roomId);
    }

    @Override
    public void onEditAction(long roomId) {
//        Intent intent = new Intent(getActivity(), EditProjectActivity.class);
//        intent.putExtra(ProjectEditFragment.TEMP_PROJECT_ID, projectId);
//        startActivity(intent);
    }

    @Override
    public void onItemClick(long id) {
//        Log.d(LOG_TAG, "Clicked on project item with id: " + id);
//        Intent intent = new Intent(getActivity(), ProjectDetailActivity.class);
//        intent.putExtra(ACTIVE_PROJECT_ID, mActiveProject);
//        startActivity(intent);
    }
}
