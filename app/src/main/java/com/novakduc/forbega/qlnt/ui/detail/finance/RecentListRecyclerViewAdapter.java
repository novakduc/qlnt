package com.novakduc.forbega.qlnt.ui.detail.finance;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.novakduc.forbega.qlnt.data.query.finance_tab.CostRecentItem;
import com.novakduc.forbega.qlnt.databinding.RecycleViewEarnCostItemBinding;
import com.novakduc.forbega.qlnt.utilities.ItemListAdapterActionHandler;

import java.util.List;

/**
 * Created by Nguyen Quoc Thanh on 1/12/2018.
 */

public class RecentListRecyclerViewAdapter
        extends RecyclerView.Adapter<RecentListRecyclerViewAdapter.ViewHolder> {

    private List mRecentList;
    private Context mContext;
    private ItemListAdapterActionHandler mActionHandler;

    public RecentListRecyclerViewAdapter(@NonNull Context context,
                                         ItemListAdapterActionHandler actionHandler) {
        mContext = context;
        mActionHandler = actionHandler;
    }

    public Object getValueAt(int position) {
        return mRecentList.get(position);
    }

    public void swapList(List newList) {
        mRecentList = newList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecycleViewEarnCostItemBinding binding =
                RecycleViewEarnCostItemBinding.inflate(inflater, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Object recentItem = mRecentList.get(position);
        if (recentItem instanceof CostRecentItem) {
            CostRecentItem lCostRecentItem = (CostRecentItem) recentItem;

        }

    }

    @Override
    public int getItemCount() {
        if (mRecentList == null) {
            return 0;
        }
        return mRecentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RecycleViewEarnCostItemBinding mBinding;

        public ViewHolder(RecycleViewEarnCostItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}

