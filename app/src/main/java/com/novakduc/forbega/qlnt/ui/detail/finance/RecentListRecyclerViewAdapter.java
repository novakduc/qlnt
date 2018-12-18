package com.novakduc.forbega.qlnt.ui.detail.finance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.novakduc.forbega.qlnt.databinding.RecycleViewEarnCostItemBinding;
import com.novakduc.forbega.qlnt.utilities.ConverterUtilities;
import com.novakduc.forbega.qlnt.utilities.ItemListAdapterActionHandler;

import java.text.NumberFormat;
import java.util.List;

import javax.annotation.Nonnull;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Nguyen Quoc Thanh on 1/12/2018.
 */

public class RecentListRecyclerViewAdapter
        extends RecyclerView.Adapter<RecentListRecyclerViewAdapter.ViewHolder> {

    private List mRecentList;
    private Context mContext;
    private ItemListAdapterActionHandler mActionHandler;

    public RecentListRecyclerViewAdapter(@Nonnull Context context,
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
        String amount = "none";
        String description = "none";
        long date = 0;
        if (recentItem instanceof CostRecentItem) {
            CostRecentItem lCostRecentItem = (CostRecentItem) recentItem;
            amount = NumberFormat.getInstance().format(lCostRecentItem.getAmount());
            date = lCostRecentItem.getDate();
            //Description
            description = lCostRecentItem.getDescription();
        }

        holder.mBinding.textViewAmount.setText(amount);
        holder.mBinding.txtDate.setText(ConverterUtilities.calendarToString(date));
        holder.mBinding.txtDescription.setText(description);

        holder.mBinding.ibtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                // TODO: 8/10/2018 delete related recent item
            }
        });

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

