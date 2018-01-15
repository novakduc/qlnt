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
import com.novakduc.forbega.qlnt.data.database.Project;
import com.novakduc.forbega.qlnt.data.database.RoomForRent;
import com.novakduc.forbega.qlnt.data.database.RoomList;

import java.util.List;

/**
 * Created by Nguyen Quoc Thanh on 1/12/2018.
 */

public class ProjectsRecyclerViewAdapter
        extends RecyclerView.Adapter<ProjectsRecyclerViewAdapter.ViewHolder> {

    private List<Project> mProjectList;
    private Context mContext;
    private ProjectListAdapterActionHandler mActionHandler;

    public ProjectsRecyclerViewAdapter(@NonNull Context context,
                                       ProjectListAdapterActionHandler actionHandler) {
        mContext = context;
        mActionHandler = actionHandler;
    }

    public Project getValueAt(int position) {
        return mProjectList.get(position);
    }

    public void swapList(List<Project> newList) {
        mProjectList = newList;
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
        final Project project = mProjectList.get(position);
        holder.mNameTextView.setText(project.getName());
        holder.mDurationTextView.setText(String.valueOf(project.getStartYear()) + " - "
                + String.valueOf(project.getEndYear()));

        float v = 0;
        String s = "0/0";
        RoomList<RoomForRent> rooms = project.getRoomForRentList();
        if (rooms != null) {
            s = String.valueOf(project.getRoomForRentList().getNoOfProducingRoom()) + "/"
                    + String.valueOf(project.getRoomForRentList().size());
            if (project.getRoomForRentList().size() != 0) {
                v = project.getRoomForRentList().getNoOfProducingRoom() / project.getRoomForRentList().size();
            }
        }
        holder.mProduceRateTextView.setText(s);
        holder.mRatingBar.setRating(v);

        long investmentAmount = project.getInvestmentAmount();
        long totalLoanAmount = 0;
        if (project.getLoanList() != null) {
            totalLoanAmount = project.getLoanList().getTotalLoanAmount();
        }
        holder.mIncomeTextView.setText(String.valueOf(project.getTotalIncome()));
        if (investmentAmount == 0) {
            holder.mIncomeProgressBar.setProgress(100);
            if (totalLoanAmount > 0) {
                holder.mDeptProgressBar.setProgress(100);
            } else {
                holder.mDeptProgressBar.setProgress(0);
            }

            holder.mRevenueProgressBar.setProgress(100);
        }
        int incomePercentage = Math.round(project.getTotalIncome() * 100 / investmentAmount);
        holder.mIncomeProgressBar.setProgress(incomePercentage);

        holder.mDeptTextView.setText(String.valueOf(totalLoanAmount));
        int deptPercentage = Math.round((totalLoanAmount * 100 / investmentAmount));
        holder.mDeptProgressBar.setProgress(deptPercentage);

        holder.mRevenueTextView.setText(String.valueOf(project.getTotalIncome()));
        long totalCostAmount = 0;
        if (project.getCostManager() != null) {
            project.getCostManager().getTotalAmount();
        }
        int revenuePercentage = Math.round((totalLoanAmount - totalCostAmount) / investmentAmount);
        holder.mRevenueProgressBar.setProgress(revenuePercentage);

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
    }

    @Override
    public int getItemCount() {
        if (mProjectList == null) {
            return 0;
        }
        return mProjectList.size();
    }

    //Handler interface to process actions applied on project
    public interface ProjectListAdapterActionHandler {
        void onDeleteAction(long projectId);

        void onCopyAction(long projectId);

        void onEditAction(long projectId);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mNameTextView;
        public final TextView mDurationTextView;
        public final TextView mProduceRateTextView;
        public final RatingBar mRatingBar;
        public final TextView mIncomeTextView;
        public final ProgressBar mIncomeProgressBar;
        public final TextView mDeptTextView;
        public final ProgressBar mDeptProgressBar;
        public final TextView mRevenueTextView;
        public final ProgressBar mRevenueProgressBar;
        public final Button mDeleteButton, mCopyButton, mEditButton;

        public String mProjectName;

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
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameTextView.getText();
        }
    }
}

