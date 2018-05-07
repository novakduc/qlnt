package com.novakduc.forbega.qlnt.ui.detail.room;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.data.database.GuestForRoomItemView;
import com.novakduc.forbega.qlnt.data.database.ListViewRoomItem;
import com.novakduc.forbega.qlnt.data.database.RoomStatus;
import com.novakduc.forbega.qlnt.databinding.RecycleViewRoomItemBinding;

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
        RecycleViewRoomItemBinding binding =
                RecycleViewRoomItemBinding.inflate(inflater, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ListViewRoomItem roomItem = mRoomList.get(position);
        holder.mBinding.textViewRoomName.setText(roomItem.getName());
        switch (roomItem.getStatus()) {
            case NORMAL:
                holder.mBinding.textViewToDo.setText(R.string.todo_relax);
                break;
            case WAITING_FOR_BILL:
                holder.mBinding.textViewToDo.setText(R.string.todo_billing);
                break;
            case WAITING_FOR_PAYMENT:
                holder.mBinding.textViewToDo.setText(R.string.todo_get_payment);
                break;
            case AVAILABLE:
                holder.mBinding.textViewToDo.setText(R.string.todo_checkIn);
                holder.mBinding.btBill.setEnabled(false);
                holder.mBinding.btCheckInOut.setText(R.string.btCheckIn);
                holder.mBinding.btCheckInOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO: 5/7/2018 go to check in activity
                    }
                });
        }

        if (roomItem.getStatus() != RoomStatus.AVAILABLE) {
            holder.mBinding.btBill.setEnabled(true);
            holder.mBinding.btBill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 5/7/2018 go to bill activity
                }
            });
            holder.mBinding.btCheckInOut.setText(R.string.btCheckOut);
            holder.mBinding.btCheckInOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 5/7/2018 go to check out activity
                }
            });
        }

        GuestForRoomItemView keyContact = roomItem.getKeyContact();
        if (keyContact != null) {
            holder.mBinding.textViewGuestName.setText(keyContact.getName());
            holder.mBinding.imageButtonCall.setEnabled(true);
            holder.mBinding.imageButtonCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 5/7/2018 call guest
                }
            });
            holder.mBinding.imageButtonMessage.setEnabled(true);
            holder.mBinding.imageButtonMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 5/7/2018 send message to guest
                }
            });
        } else {
            holder.mBinding.textViewGuestName.setText(R.string.no_guest);
            holder.mBinding.imageButtonCall.setEnabled(false);
            holder.mBinding.imageButtonMessage.setEnabled(false);
        }
        //delete button
        holder.mBinding.ibtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActionHandler.onDeleteAction(roomItem.getId());
            }
        });
        //edit action
        holder.mBinding.ibtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActionHandler.onEditAction(roomItem.getId());
            }
        });
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
        void onDeleteAction(long id);

        void onCopyAction(long id);

        void onEditAction(long id);

        void onItemClick(long id);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RecycleViewRoomItemBinding mBinding;

        public ViewHolder(RecycleViewRoomItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}

