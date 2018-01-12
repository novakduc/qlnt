package com.novakduc.forbega.qlnt.ui.list;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.data.database.CurrencyUnit;
import com.novakduc.forbega.qlnt.data.database.Project;
import com.novakduc.forbega.qlnt.ui.config.ProjectConfigurationActivity;
import com.novakduc.forbega.qlnt.ultilities.InjectorUtils;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by n.thanh on 9/29/2016.
 */

public class ProjectListFragment extends android.support.v4.app.Fragment {
    // TODO: 9/29/2016
    public static final String PREF_QLNT = "com.novak.forbequ.qlnt";
    private static final String ACTIVE_PROJECT_ID = "active_project_id";
    private static final int PROJECT_CONFIG_RESULT = 0;
    ProjectsRecyclerViewAdapter mProjectsRecyclerViewAdapter;
    private long mActiveProject = -1;
    private ProjectListFragmentViewModel mViewModel;
    private ArrayList<Project> mProjects;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ProjectListViewModelFactory factory =
                InjectorUtils.provideProjectListViewModelFactory(getActivity());

        mViewModel = ViewModelProviders.of(this, factory).get(ProjectListFragmentViewModel.class);

        //Load active project ID
        SharedPreferences preferences = getActivity().getSharedPreferences(PREF_QLNT, 0);
        Long id = preferences.getLong(ACTIVE_PROJECT_ID, -1);
    }

    @Override
    public void onStop() {
        super.onStop();

        //Save active project ID
        SharedPreferences preferences = getActivity().getSharedPreferences(PREF_QLNT, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(ACTIVE_PROJECT_ID, mActiveProject);
        editor.apply();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_list, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        final ActionBar ab = activity.getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_view_list);
            ab.setDisplayHomeAsUpEnabled(false);
        }

        final ImageView imageView = view.findViewById(R.id.backdrop);
        Glide.with(this)
                .load(R.drawable.pic)
                .apply(RequestOptions.centerCropTransform())
                .into(imageView);

        mProjectsRecyclerViewAdapter = new ProjectsRecyclerViewAdapter(activity, mProjects);
        RecyclerView recyclerView = view.findViewById(R.id.rv_project_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mProjectsRecyclerViewAdapter);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProjectConfigurationActivity.class);
                startActivityForResult(intent, PROJECT_CONFIG_RESULT);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO: 4/19/2017 Update result from project configuration activity
        mProjectsRecyclerViewAdapter.notifyDataSetChanged();
    }

    public static class ProjectsRecyclerViewAdapter
            extends RecyclerView.Adapter<ProjectsRecyclerViewAdapter.ViewHolder> {

        private ArrayList<Project> mProjectList;
        private Context mContext;

        public ProjectsRecyclerViewAdapter(Context context, ArrayList<Project> items) {
            mProjectList = items;
        }

        public Project getValueAt(int position) {
            return mProjectList.get(position);
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
}
