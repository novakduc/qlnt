package com.novakduc.forbega.qlnt.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.utilities.InjectorUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by n.thanh on 10/12/2016.
 */

public class ProjectDetailFragment extends Fragment {
    public static final String PREF_QLNT = "com.novak.forbequ.qlnt";
    private static final String LOG_TAG = ProjectDetailFragment.class.getSimpleName();
    private static final String ACTIVE_PROJECT_ID = "active_project_id";
    private final int numberOfPage = 3;
    private long mActiveProjectId = -1;
    private ProjectDetailFragmentViewModel mViewModel;
    // TODO: 10/12/2016


    public static ProjectDetailFragment newInstance(long activeProjectId) {
        Bundle bundle = new Bundle();
        bundle.putLong(ACTIVE_PROJECT_ID, activeProjectId);
        ProjectDetailFragment fragment = new ProjectDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mActiveProjectId = getArguments().getLong(ACTIVE_PROJECT_ID);

        ProjectDetailViewModelFactory factory =
                InjectorUtils.provideProjectDetailViewModelFactory(getActivity(), mActiveProjectId);

        mViewModel = ViewModelProviders.of(this, factory).get(ProjectDetailFragmentViewModel.class);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.setting_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.homeAsUp) {
            getActivity().finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_detail, container, false);
        final Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        final ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_view_list);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mViewModel.getProjectName().observe(this, new Observer<ProjectNameQuery>() {
            @Override
            public void onChanged(@Nullable ProjectNameQuery pProjectNameQuery) {
                toolbar.setTitle(pProjectNameQuery.getName());
            }
        });

        //Load active project ID
//        SharedPreferences preferences = getActivity().getSharedPreferences(PREF_QLNT, 0);
//        mActiveProjectId = preferences.getLong(ACTIVE_PROJECT_ID, -1);

        //Add tabs
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        TabLayout.Tab tab1 = tabLayout.newTab();
        tab1.setText(R.string.page1_title);
//        tab1.setIcon(R.drawable.ic_view_list);
        tabLayout.addTab(tab1);

        TabLayout.Tab tab2 = tabLayout.newTab();
        tab2.setText(R.string.page2_title);
//        tab2.setIcon(R.drawable.ic_calculator);
        tabLayout.addTab(tab2);

        TabLayout.Tab tab3 = tabLayout.newTab();
        tab3.setText(R.string.page3_title);
//        tab3.setIcon(R.drawable.ic_currency_usd);
        tabLayout.addTab(tab3);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = view.findViewById(R.id.pager);
        final TabAdapter tabAdapter = new TabAdapter(getFragmentManager(), numberOfPage, mActiveProjectId);
        viewPager.setAdapter(tabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(onTabSelectedListener(viewPager));

//        FloatingActionButton fab = view.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        return view;
    }

    private TabLayout.OnTabSelectedListener onTabSelectedListener(final ViewPager pager) {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }
}
