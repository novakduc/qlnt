package com.novakduc.forbega.qlnt.config.finance;

import android.app.Fragment;
import android.os.Bundle;

import com.novakduc.baselibrary.SimpleFragmentActivity;

public class ProjectLoanDeclareActivity extends SimpleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ProjectLoanDeclareFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
