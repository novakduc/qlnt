package com.novakduc.forbega.qlnt.ui.detail.room;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.data.database.GuestForRoomItemView;
import com.novakduc.forbega.qlnt.data.database.ListViewRoomItem;
import com.novakduc.forbega.qlnt.databinding.RoomItemLayoutBinding;

import java.util.List;

/**
 * Created by Nguyen Quoc Thanh on 1/12/2018.
 */

public class RoomsRecyclerViewAdapter
        extends RecyclerView.Adapter<RoomsRecyclerViewAdapter.ViewHolder> {

    private List<ListViewRoomItem> mRoomList;
    private List<GuestForRoomItemView> mKeyContactList;
    private Context mContext;
    private ItemListAdapterActionHandler mActionHandler;

    public RoomsRecyclerViewAdapter(@NonNull Context context,
                                    ItemListAdapterActionHandler actionHandler) {
        mContext = context;
        mActionHandler = actionHandler;
    }

    public ListViewRoomItem getValueAt(int position) {
        return mRoomList.get(position);
    }

    public void swapList(List<ListViewRoomItem> newList) {
        mRoomList = newList;
        updateItems();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RoomItemLayoutBinding binding =
                RoomItemLayoutBinding.inflate(inflater, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ListViewRoomItem roomItem = mRoomList.get(position);
        holder.mBinding.textViewRoomName.setText(roomItem.getName());
        switch (roomItem.getStatus()) {
            case NORMAL:
                holder.mBinding.textViewToDo.setText(
                        mContext.getResources().getString(R.string.todo_relax));
                break;
            case WAITING_FOR_BILL:
                holder.mBinding.textViewToDo.setText(
                        mContext.getResources().getString(R.string.todo_billing));
                break;
            case WAITING_FOR_PAYMENT:
                holder.mBinding.textViewToDo.setText(
                        mContext.getResources().getString(R.string.todo_get_payment));
                break;
            case AVAILABLE:
                holder.mBinding.textViewToDo.setText(
                        mContext.getResources().getString(R.string.todo_checkIn));
        }

        // TODO: 5/6/2018
    }

    @Override
    public int getItemCount() {
        if (mRoomList == null) {
            return 0;
        }
        return mRoomList.size();
    }

    public void updateKeyContacts(List<GuestForRoomItemView> guestForRoomItemViews) {
        mKeyContactList = guestForRoomItemViews;
        updateItems();
    }

    private void updateItems() {
        if (mKeyContactList != null && mRoomList != null) {
            for (ListViewRoomItem roomItem :
                    mRoomList) {
                for (GuestForRoomItemView keyContact :
                        mKeyContactList) {
                    if (roomItem.getId() == keyContact.getRoomId()) {
                        roomItem.setKeyContact(keyContact);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    //Handler interface to process actions applied on project
    public interface ItemListAdapterActionHandler {
        void onDeleteAction(long projectId);

        void onCopyAction(long projectId);

        void onEditAction(long projectId);

        void onItemClick(long id);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RoomItemLayoutBinding mBinding;

        public ViewHolder(RoomItemLayoutBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}

