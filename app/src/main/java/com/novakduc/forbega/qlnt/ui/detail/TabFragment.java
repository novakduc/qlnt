package com.novakduc.forbega.qlnt.ui.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.novakduc.forbega.qlnt.R;

/**
 * Created by Novak on 9/25/2016.
 */

public class TabFragment extends android.support.v4.app.Fragment {
    private static String TAB_POSITION_KEY = "qlnt.tab.position";
    TextView mTextView;
    private TabPosition position;

    public static TabFragment getInstance(TabPosition position) {
        TabFragment tabFragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAB_POSITION_KEY, position);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = (TabPosition) getArguments().getSerializable(TAB_POSITION_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_project_management, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTextView = view.findViewById(R.id.fragment_tab_text);
        setContentView();
    }

    private void setContentView() {
        switch (position) {
            case PROJECT_MANAGEMENT:
                mTextView.setText(R.string.page1_title);
                return;
            case INFORMATION:
                mTextView.setText(R.string.page2_title);
                return;
            case REPORT:
                mTextView.setText(R.string.page3_title);
        }
    }
}
