package com.novakduc.forbega.qlnt.ui.detail.room;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.novakduc.forbega.qlnt.R;
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
    private RoomListAdapterActionHandler mActionHandler;
    private int mRoomStatus;

    public RoomsRecyclerViewAdapter(@NonNull Context context,
                                    RoomListAdapterActionHandler actionHandler) {
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
                mRoomStatus = -1;
                break;
            case WAITING_FOR_BILL:
                holder.mBinding.textViewToDo.setText(R.string.todo_billing);
                mRoomStatus = R.string.messege_to_ask_for_indexes;
                break;
            case WAITING_FOR_PAYMENT:
                holder.mBinding.textViewToDo.setText(R.string.todo_get_payment);
                mRoomStatus = R.string.messege_ask_for_payment;
                break;
            case AVAILABLE:
                holder.mBinding.textViewToDo.setText(R.string.todo_checkIn);
                holder.mBinding.btBill.setEnabled(false);
                holder.mBinding.btCheckInOut.setText(R.string.btCheckIn);
                holder.mBinding.btCheckInOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mActionHandler.onCheckIn(roomItem.getId());
                    }
                });
        }

        if (roomItem.getStatus() != RoomStatus.AVAILABLE) {
            holder.mBinding.btBill.setEnabled(true);
            holder.mBinding.btBill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActionHandler.onBill(roomItem.getId());
                }
            });
            holder.mBinding.btCheckInOut.setText(R.string.btCheckOut);
            holder.mBinding.btCheckInOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActionHandler.onCheckOut(roomItem.getId());
                }
            });
        }

        GuestForRoomItemView keyContact = roomItem.getKeyContact();
        if (keyContact != null) {
            holder.mBinding.textViewGuestName.setText(keyContact.getName());
            holder.mBinding.imageButtonCall.setEnabled(true);
            holder.mBinding.imageButtonCall.setImageAlpha(255);
            holder.mBinding.imageButtonCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActionHandler.onCallGuest(roomItem.getKeyContact().getPhoneNumber());
                }
            });
            holder.mBinding.imageButtonMessage.setEnabled(true);
            holder.mBinding.imageButtonMessage.setImageAlpha(255);
            holder.mBinding.imageButtonMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActionHandler.onSendMessageToGuest(roomItem.getKeyContact().getPhoneNumber(), getRoomStatus());
                }
            });
        } else {
            holder.mBinding.textViewGuestName.setText(R.string.no_guest);
            holder.mBinding.imageButtonCall.setEnabled(false);
            holder.mBinding.imageButtonCall.setImageAlpha(30);
            holder.mBinding.imageButtonMessage.setEnabled(false);
            holder.mBinding.imageButtonMessage.setImageAlpha(30);
        }
        //delete button
        holder.mBinding.ibtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActionHandler.onDeleteAction(roomItem.getId());
            }
        });

        //edit action
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                mActionHandler.onEditAction(roomItem.getId());
            }
        });
    }

    private String getRoomStatus() {
        return mRoomStatus != -1 ? mContext.getResources().getString(mRoomStatus) : "";
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RecycleViewRoomItemBinding mBinding;

        public ViewHolder(RecycleViewRoomItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}

