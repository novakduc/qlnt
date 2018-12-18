package com.novakduc.forbega.qlnt.ui.detail.report;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.databinding.FragmentReportTabBinding;
import com.novakduc.forbega.qlnt.ui.detail.finance.RecentListRecyclerViewAdapter;
import com.novakduc.forbega.qlnt.utilities.InjectorUtils;
import com.novakduc.forbega.qlnt.utilities.ItemListAdapterActionHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

/**
 * Created by n.thanh on 9/29/2016.
 */

public class ReportFragment extends Fragment
        implements ItemListAdapterActionHandler {
    public static final String ACTIVE_PROJECT_ID = "active_project_id";
    private static final String LOG_TAG = ReportFragment.class.getSimpleName();
    private RecentListRecyclerViewAdapter mRecentListRecyclerViewAdapter;
    private long mActiveProject = -1;
    private ReportFragmentViewModel mViewModel;
    private FragmentReportTabBinding mBinding;

    public static ReportFragment getInstance(@Nonnull long activeId) {
        Bundle bundle = new Bundle();
        bundle.putLong(ACTIVE_PROJECT_ID, activeId);
        ReportFragment lReportFragment = new ReportFragment();
        lReportFragment.setArguments(bundle);
        return lReportFragment;
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

        ReportViewModelFactory factory =
                InjectorUtils.provideReportViewModelFactory(getActivity(), mActiveProject);

        mViewModel = ViewModelProviders.of(this, factory).get(ReportFragmentViewModel.class);
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
                R.layout.fragment_report_tab, container, false);

        final View view = mBinding.getRoot();
        //Start from here

        final AppCompatActivity activity = (AppCompatActivity) getActivity();


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
