package com.novakduc.forbega.qlnt.ui.detail;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.ui.TabAdapter;

/**
 * Created by n.thanh on 10/12/2016.
 */

public class ProjectDetailFragment extends android.support.v4.app.Fragment {
    public static final String PREF_QLNT = "com.novak.forbequ.qlnt";
    private static final String ACTIVE_PROJECT_ID = "active_project_id";
    private final int numberOfPage = 3;
    private long mActiveProject = -1;
    // TODO: 10/12/2016


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_project_management, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_detail, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        //Load active project ID
        SharedPreferences preferences = getActivity().getSharedPreferences(PREF_QLNT, 0);
        long id = preferences.getLong(ACTIVE_PROJECT_ID, -1);
//        QlntRepository.getInstance(getActivity()).setActiveProjectId(id);

        //Add tabs
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        TabLayout.Tab tab1 = tabLayout.newTab();
        tab1.setIcon(R.drawable.ic_view_list);
        tabLayout.addTab(tab1);

        TabLayout.Tab tab2 = tabLayout.newTab();
        tab2.setIcon(R.drawable.ic_calculator);
        tabLayout.addTab(tab2);

        TabLayout.Tab tab3 = tabLayout.newTab();
        tab3.setIcon(R.drawable.ic_currency_usd);
        tabLayout.addTab(tab3);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = view.findViewById(R.id.pager);
        final TabAdapter tabAdapter = new TabAdapter(getFragmentManager(), numberOfPage);
        viewPager.setAdapter(tabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(onTabSelectedListener(viewPager));

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
