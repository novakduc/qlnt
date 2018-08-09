package com.novakduc.forbega.qlnt.ui.detail.finance;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.novakduc.forbega.qlnt.ui.detail.room.RoomsRecyclerViewAdapter;
import com.novakduc.forbega.qlnt.utilities.InjectorUtils;
import com.novakduc.forbega.qlnt.utilities.ItemListAdapterActionHandler;

/**
 * Created by n.thanh on 9/29/2016.
 */

public class FinanceFragment extends android.support.v4.app.Fragment
        implements ItemListAdapterActionHandler {
    public static final String ACTIVE_PROJECT_ID = "active_project_id";
    private static final String LOG_TAG = FinanceFragment.class.getSimpleName();
    private RoomsRecyclerViewAdapter mRoomsRecyclerViewAdapter;
    private long mActiveProject = -1;
    private FinanceFragmentViewModel mViewModel;
//    private FragmentFinanceTabBinding mBinding;

    public static FinanceFragment getInstance(@NonNull long activeId) {
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
//        mBinding = DataBindingUtil.inflate(inflater,
//                R.layout.fragment_finance_tab, container, false);

        final View view = null; //mBinding.getRoot();

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
