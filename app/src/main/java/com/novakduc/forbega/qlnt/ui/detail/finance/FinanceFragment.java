package com.novakduc.forbega.qlnt.ui.detail.finance;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.databinding.FragmentFinanceTabBinding;
import com.novakduc.forbega.qlnt.utilities.InjectorUtils;
import com.novakduc.forbega.qlnt.utilities.ItemListAdapterActionHandler;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by n.thanh on 9/29/2016.
 */

public class FinanceFragment extends Fragment
        implements ItemListAdapterActionHandler {
    public static final String ACTIVE_PROJECT_ID = "active_project_id";
    private static final String LOG_TAG = FinanceFragment.class.getSimpleName();
    private RecentListRecyclerViewAdapter mRecentListRecyclerViewAdapter;
    private long mActiveProject = -1;
    private FinanceFragmentViewModel mViewModel;
    private FragmentFinanceTabBinding mBinding;

    public static FinanceFragment getInstance(@Nonnull long activeId) {
        Bundle bundle = new Bundle();
        bundle.putLong(ACTIVE_PROJECT_ID, activeId);
        FinanceFragment financeFragment = new FinanceFragment();
        financeFragment.setArguments(bundle);
        return financeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            // TODO: 4/3/2018 load active project id
        } else {
            mActiveProject = getArguments().getLong(ACTIVE_PROJECT_ID);
        }

        Log.d(LOG_TAG, String.valueOf(mActiveProject));

        FinanceViewModelFactory factory =
                InjectorUtils.provideFinanceViewModelFactory(getActivity(), mActiveProject);

        mViewModel = ViewModelProviders.of(this, factory).get(FinanceFragmentViewModel.class);
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Data binding
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_finance_tab, container, false);

        final View view = mBinding.getRoot();
        //Start from here

        final AppCompatActivity activity = (AppCompatActivity) getActivity();


        mRecentListRecyclerViewAdapter = new RecentListRecyclerViewAdapter(activity, this);
        RecyclerView recyclerView = mBinding.recentActivityRecyclerView;
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mRecentListRecyclerViewAdapter);

        mViewModel.getProjectFinanceInfo().observe(this, new Observer<ProjectFinanceTab>() {
            @Override
            public void onChanged(@Nullable ProjectFinanceTab pProjectFinanceTab) {
                if (pProjectFinanceTab != null) {
                    mBinding.txtCostAmount.setText(
                            java.text.NumberFormat.getInstance().format(pProjectFinanceTab.getCost()));
                    mBinding.txtDeptAmount.setText(
                            java.text.NumberFormat.getInstance().format(pProjectFinanceTab.getDept()));
                    mBinding.txtIncomeAmount.setText(
                            java.text.NumberFormat.getInstance().format(pProjectFinanceTab.getIncome()));
                    mBinding.txtRevenueAmount.setText(
                            java.text.NumberFormat.getInstance().format(pProjectFinanceTab.getRevenue()));
                }
            }
        });

        mViewModel.getRecentItems().observe(this, new Observer<List>() {
            @Override
            public void onChanged(@Nullable List pList) {
                if (pList != null) {
                    mRecentListRecyclerViewAdapter.swapList(pList);
                    if (pList.isEmpty()) {
                        mBinding.textViewNoRecentItem.setVisibility(View.VISIBLE);
                    } else {
                        mBinding.textViewNoRecentItem.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onDeleteAction(long roomId) {

    }

    @Override
    public void onCopyAction(long roomId) {

    }

    @Override
    public void onEditAction(long roomId) {

    }

    @Override
    public void onItemClick(long id) {

    }
}
