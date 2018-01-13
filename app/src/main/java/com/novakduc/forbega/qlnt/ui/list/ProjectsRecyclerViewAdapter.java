package com.novakduc.forbega.qlnt.ui.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.data.database.CurrencyUnit;
import com.novakduc.forbega.qlnt.data.database.Project;

import java.util.List;

/**
 * Created by Nguyen Quoc Thanh on 1/12/2018.
 */

public class ProjectsRecyclerViewAdapter
        extends RecyclerView.Adapter<ProjectsRecyclerViewAdapter.ViewHolder> {

    private List<Project> mProjectList;
    private Context mContext;

    public ProjectsRecyclerViewAdapter(Context context, List<Project> items) {
        mProjectList = items;
    }

    public Project getValueAt(int position) {
        return mProjectList.get(position);
    }

    public void swapList(List<Project> newList) {
        mProjectList = newList;
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
        holder.mProduceRateTextView.setText(String.valueOf(
                project.getRoomForRentList().getNoOfProducingRoom()) + "/"
                + String.valueOf(project.getRoomForRentList().size()));
        float v = 0;
        if (project.getRoomForRentList().size() != 0) {
            v = project.getRoomForRentList().getNoOfProducingRoom() / project.getRoomForRentList().size();
        }
        holder.mRatingBar.setRating(v);

        long investmentAmount = project.getInvestmentAmount();
        holder.mIncomeTextView.setText(String.valueOf(project.getTotalIncome(CurrencyUnit.MIL_BASE)));
        if (investmentAmount == 0) {
            holder.mIncomeProgressBar.setProgress(100);
            if (project.getLoanList().getTotalLoanAmount() > 0) {
                holder.mDeptProgressBar.setProgress(100);
            } else {
                holder.mDeptProgressBar.setProgress(0);
            }

            holder.mRevenueProgressBar.setProgress(100);
        }
        int incomePercentage = Math.round(project.getTotalIncome() * 100 / investmentAmount);
        holder.mIncomeProgressBar.setProgress(incomePercentage);

        holder.mDeptTextView.setText(String.valueOf(project.getLoanList()
                .getTotalLoanAmount()));
        int deptPercentage = Math.round((project.getLoanList().getTotalLoanAmount() * 100 / investmentAmount));
        holder.mDeptProgressBar.setProgress(deptPercentage);

        holder.mRevenueTextView.setText(String.valueOf(project.getTotalIncome(CurrencyUnit.MIL_BASE)));
        int revenuePercentage = Math.round(
                (project.getLoanList().getTotalLoanAmount() - project.getCostManager().getTotalAmount())
                        / investmentAmount);
        holder.mRevenueProgressBar.setProgress(revenuePercentage);

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProjectList.remove(project);
                notifyDataSetChanged();
                // TODO: 12/27/2017 weird behaviour after deleting project
            }
        });

        holder.mCopyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Project cloneProject1 = (Project) project.clone();
                    mProjectList.add(cloneProject1);
                    notifyDataSetChanged();
                } catch (CloneNotSupportedException pE) {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.projectCopyError),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 12/27/2017 Edit action

            }
        });
    }

    @Override
    public int getItemCount() {
        return mProjectList.size();
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

