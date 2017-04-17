package com.novakduc.forbega.qlnt.config.finance;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.model.Loan;
import com.novakduc.forbega.qlnt.model.Project;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by n.thanh on 10/21/2016.
 */

public class ProjectFinanceConfigFragment extends Fragment {
    public static final String TEMP_PROJECT = "com.novakduc.forbega.qlnt.tempproject";
    private Project mProject;
    private TextInputLayout mLayoutAmount;
    private EditText mEditTextAmount;

    public static ProjectFinanceConfigFragment newInstance(Project tempProject) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(TEMP_PROJECT, tempProject);
        ProjectFinanceConfigFragment fragment = new ProjectFinanceConfigFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProject = getArguments().getParcelable(TEMP_PROJECT);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_finance_config, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.financeConfigTitle);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        final ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_navigate_before);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mLayoutAmount = (TextInputLayout) view.findViewById(R.id.txtLayoutInvestment);
        mEditTextAmount = (EditText) view.findViewById(R.id.investmentAmount);

        /*
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.loanList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new LoansAdapter(activity, mProject.getLoanList()));
        */
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public static class LoansAdapter extends RecyclerView.Adapter<LoansAdapter.ViewHolder> {
        private Context mContext;
        private List<Loan> mLoans;

        public LoansAdapter(Context context, List<Loan> loanList) {
            mContext = context;
            mLoans = loanList;
        }

        @Override

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View loanView = inflater.inflate(R.layout.loan_item_layout, parent, false);
            ViewHolder viewHolder = new ViewHolder(loanView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            Loan loan = mLoans.get(position);
            holder.mTextViewBankName.setText(loan.getName());
            Date date = new Date();
            date.setTime(loan.getLoanDate());
            DateFormat format = SimpleDateFormat.getDateInstance();
            holder.mTextViewStartDate.setText(format.format(date));
            holder.mTextViewLoanAmount.setText(String.valueOf(loan.getAmount()));
            holder.mTextViewInterestRate.setText(String.valueOf(loan.getInterestRate()));
        }

        @Override
        public int getItemCount() {
            return mLoans.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView mTextViewBankName, mTextViewStartDate, mTextViewLoanAmount, mTextViewInterestRate;
            Button mButtonEdit, mButtonDelete;

            public ViewHolder(View itemView) {
                super(itemView);

                mTextViewBankName = (TextView) itemView.findViewById(R.id.textViewBankName);
                mTextViewStartDate = (TextView) itemView.findViewById(R.id.textViewStartDate);
                mTextViewLoanAmount = (TextView) itemView.findViewById(R.id.txtViewLoanAmount);
                mTextViewInterestRate = (TextView) itemView.findViewById(R.id.interestRate);
                mButtonDelete = (Button) itemView.findViewById(R.id.btDelete);
                mButtonEdit = (Button) itemView.findViewById(R.id.btEdit);
                // TODO: 11/24/2016 view event and update
            }
        }
    }
}
