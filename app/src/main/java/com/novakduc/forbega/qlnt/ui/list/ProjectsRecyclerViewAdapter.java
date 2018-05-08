package com.novakduc.forbega.qlnt.ui.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.data.database.ListViewProjectItem;
import com.novakduc.forbega.qlnt.data.database.LoanAmount;
import com.novakduc.forbega.qlnt.utilities.ConverterUtilities;
import com.novakduc.forbega.qlnt.utilities.CurrencyUnit;
import com.novakduc.forbega.qlnt.utilities.ItemListAdapterActionHandler;

import java.util.List;

/**
 * Created by Nguyen Quoc Thanh on 1/12/2018.
 */

public class ProjectsRecyclerViewAdapter
        extends RecyclerView.Adapter<ProjectsRecyclerViewAdapter.ViewHolder> {

    private List<ListViewProjectItem> mProjectList;
    private List<LoanAmount> mLoanAmounts;
    private Context mContext;
    private ItemListAdapterActionHandler mActionHandler;

    public ProjectsRecyclerViewAdapter(@NonNull Context context,
                                       ItemListAdapterActionHandler actionHandler) {
        mContext = context;
        mActionHandler = actionHandler;
    }

    public ListViewProjectItem getValueAt(int position) {
        return mProjectList.get(position);
    }

    public void swapList(List<ListViewProjectItem> newList) {
        mProjectList = newList;
        calculateTotalDept();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ListViewProjectItem project = mProjectList.get(position);
        holder.mNameTextView.setText(project.getName());
        holder.mDurationTextView.setText(String.valueOf(project.getStartYear()) + " - "
                + String.valueOf(project.getEndYear()));

        float v = (float) project.getRate();
        String s = project.getNoOfProducingRoom() + "/" + project.getNoOfRoom();

        holder.mProduceRateTextView.setText(s);
        holder.mRatingBar.setRating(v);

        holder.mIncomeTextView.setText(ConverterUtilities.currencyUnitConverterToString(
                project.getIncome(), CurrencyUnit.MIL_BASE, 3));
        holder.mIncomeProgressBar.setMax(1000);
        holder.mIncomeProgressBar.setProgress(project.getIncomeProgress());
        holder.mDeptProgressBar.setMax(1000);
        holder.mDeptProgressBar.setProgress(project.getDebtProgress());
        holder.mRevenueProgressBar.setMax(1000);
        holder.mRevenueProgressBar.setProgress(project.getRevenueProgress());

        holder.mDeptTextView.setText(ConverterUtilities.currencyUnitConverterToString(
                project.getDebt(), CurrencyUnit.MIL_BASE, 3));

        holder.mRevenueTextView.setText(ConverterUtilities.currencyUnitConverterToString(
                project.getIncome(), CurrencyUnit.MIL_BASE, 3));

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionHandler.onDeleteAction(project.getId());
            }
        });

        holder.mCopyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionHandler.onCopyAction(project.getId());
            }
        });

        holder.mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionHandler.onEditAction(project.getId());
            }
        });

        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionHandler.onItemClick(project.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mProjectList == null) {
            return 0;
        }
        return mProjectList.size();
    }

    public void updateLoanAmounts(List<LoanAmount> loanAmounts) {
        mLoanAmounts = loanAmounts;
        calculateTotalDept();
        notifyDataSetChanged();
    }

    private void calculateTotalDept() {
        if (mProjectList != null) {
            if (mLoanAmounts != null) {
                for (ListViewProjectItem project :
                        mProjectList) {
                    long total = 0;
                    for (LoanAmount loan :
                            mLoanAmounts) {
                        if (loan.getProjectId() == project.getId())
                            total += loan.getAmount();
                    }
                    project.setDebt(total);
                }
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mNameTextView;
        private final TextView mDurationTextView;
        private final TextView mProduceRateTextView;
        private final RatingBar mRatingBar;
        private final TextView mIncomeTextView;
        private final ProgressBar mIncomeProgressBar;
        private final TextView mDeptTextView;
        private final ProgressBar mDeptProgressBar;
        private final TextView mRevenueTextView;
        private final ProgressBar mRevenueProgressBar;
        private final Button mDeleteButton, mCopyButton, mEditButton;
        private final View mItemView;

        public ViewHolder(View view) {
            super(view);
            mNameTextView = view.findViewById(R.id.textViewProjectName);
            mDurationTextView = view.findViewById(R.id.textViewProjectDuration);
            mRatingBar = view.findViewById(R.id.ratingBar);
            mProduceRateTextView = view.findViewById(R.id.textViewProjectProduceRate);
            mIncomeTextView = view.findViewById(R.id.txtViewTotalIncome);
            mIncomeProgressBar = view.findViewById(R.id.progressBarIncome);
            mDeptTextView = view.findViewById(R.id.txtViewDept);
            mDeptProgressBar = view.findViewById(R.id.progressBarDept);
            mRevenueTextView = view.findViewById(R.id.txtViewRevenue);
            mRevenueProgressBar = view.findViewById(R.id.progressBarRevenue);
            mDeleteButton = view.findViewById(R.id.btDelete);
            mEditButton = view.findViewById(R.id.btEdit);
            mCopyButton = view.findViewById(R.id.btCopy);
            mItemView = view;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameTextView.getText();
        }
    }
}

