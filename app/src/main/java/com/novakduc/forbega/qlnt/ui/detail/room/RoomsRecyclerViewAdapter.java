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
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ListViewRoomItem roomItem = mRoomList.get(position);
    }

    @Override
    public int getItemCount() {
        if (mRoomList == null) {
            return 0;
        }
        return mRoomList.size();
    }

    //Handler interface to process actions applied on project
    public interface ItemListAdapterActionHandler {
        void onDeleteAction(long projectId);

        void onCopyAction(long projectId);

        void onEditAction(long projectId);

        void onItemClick(long id);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
        }
    }
}

