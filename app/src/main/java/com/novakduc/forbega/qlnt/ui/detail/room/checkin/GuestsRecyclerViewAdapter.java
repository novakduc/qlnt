package com.novakduc.forbega.qlnt.ui.detail.room.checkin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.data.database.Guest;
import com.novakduc.forbega.qlnt.databinding.RecycleViewGuestItemBinding;
import com.novakduc.forbega.qlnt.ui.detail.room.GuestForRoomItemView;

import java.util.List;

/**
 * Created by Nguyen Quoc Thanh on 1/12/2018.
 */

public class GuestsRecyclerViewAdapter
        extends RecyclerView.Adapter<GuestsRecyclerViewAdapter.ViewHolder> {

    private List<Guest> mGuestList;
    private List<GuestForRoomItemView> mKeyContactList;
    private Context mContext;
    private GuestListAdapterActionHandler mActionHandler;

    public GuestsRecyclerViewAdapter(@NonNull Context context,
                                     GuestListAdapterActionHandler actionHandler) {
        mContext = context;
        mActionHandler = actionHandler;
    }

    public Guest getValueAt(int position) {
        return mGuestList.get(position);
    }

    public void swapList(List<Guest> newList) {
        mGuestList = newList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecycleViewGuestItemBinding binding =
                RecycleViewGuestItemBinding.inflate(inflater, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Guest guest = mGuestList.get(position);
        holder.mBinding.textViewGuestName.setText(guest.getName());

        if (guest.isKeyContact()) {
            holder.mBinding.textViewGuestName.setTextColor(
                    mContext.getResources().getColor(R.color.redColor));
        } else {
            holder.mBinding.textViewGuestName.setTextColor(
                    mContext.getResources().getColor(android.R.color.black));
        }

        holder.mBinding.ibtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                mActionHandler.onDeleteAction(guest.getId());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                mActionHandler.onEditAction(guest.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mGuestList == null) {
            return 0;
        }
        return mGuestList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RecycleViewGuestItemBinding mBinding;

        public ViewHolder(RecycleViewGuestItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}

