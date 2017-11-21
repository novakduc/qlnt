package com.novakduc.forbega.qlnt.config.finance;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.config.UpdateListener;
import com.novakduc.forbega.qlnt.config.unitprice.ProjectUnitPriceConfigFragment;
import com.novakduc.forbega.qlnt.model.Loan;
import com.novakduc.forbega.qlnt.model.LoanList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by n.thanh on 10/21/2016.
 */

public class ProjectFinanceConfigFragment extends Fragment {
    public static final String TEMP_PROJECT = "com.novakduc.forbega.qlnt.tempproject";
    private static final int LOAN_DECLARE_REQUEST = 1;
    private long mAmount = -1;
    private TextInputLayout mLayoutAmount;
    private UpdateListener mCallBack;
    private LoanList<Loan> mLoanList;

    public static ProjectFinanceConfigFragment newInstance() {
        return new ProjectFinanceConfigFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoanList = new LoanList<Loan>();
        setHasOptionsMenu(true);
        getActivity().setTheme(R.style.AppTheme_NoActionBar);
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

        mCallBack = (UpdateListener) getActivity();
        mLayoutAmount = (TextInputLayout) view.findViewById(R.id.txtLayoutInvestment);
        EditText editTextAmount = (EditText) view.findViewById(R.id.investmentAmount);
        //editTextAmount.setText(String.valueOf(mAmount));
        editTextAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    mAmount = Long.valueOf(editable.toString());
                    if (mAmount < 0) {
                        throw new NumberFormatException();
                    } else {
                        mLayoutAmount.setErrorEnabled(false);
                    }
                } catch (NumberFormatException e) {
                    mLayoutAmount.setError(getString(R.string.invesment_amount_error));
                    mLayoutAmount.setErrorEnabled(true);
                    mAmount = -1;
                }
            }
        });

        ImageButton addLoan = (ImageButton) view.findViewById(R.id.btAddLoan);
        addLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProjectLoanDeclareActivity.class);
                startActivityForResult(intent, LOAN_DECLARE_REQUEST);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.next_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextAction();
            }
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.loanList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new LoansAdapter(activity, mLoanList));
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.close_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.close) {
            mCallBack.discardConfirmation(R.string.project_create_discard);
        }

        if (item.getItemId() == android.R.id.home)
            getFragmentManager().popBackStack();
        return super.onOptionsItemSelected(item);
    }

    public void nextAction() {
        if (mAmount != -1) {
            mCallBack.updateFinance(mAmount, mLoanList);
            FragmentManager manager = getActivity().getFragmentManager();
            manager.beginTransaction().replace(R.id.fragmentContainer,
                    ProjectUnitPriceConfigFragment.newInstance()).addToBackStack(null).commit();
        } else {
            mLayoutAmount.setError(getResources().getString(R.string.invesment_amount_error));
            mLayoutAmount.setErrorEnabled(true);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOAN_DECLARE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                // TODO: 4/19/2017 update loan to loanlist - something is not right
                Loan loan = data.getParcelableExtra(ProjectLoanDeclareFragment.RETURN_LOAN);
                mLoanList.add(loan);
            }
        }
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
            ImageButton mButtonEdit, mButtonDelete;

            public ViewHolder(View itemView) {
                super(itemView);

                mTextViewBankName = (TextView) itemView.findViewById(R.id.textViewBankName);
                mTextViewStartDate = (TextView) itemView.findViewById(R.id.textViewStartDate);
                mTextViewLoanAmount = (TextView) itemView.findViewById(R.id.txtViewLoanAmount);
                mTextViewInterestRate = (TextView) itemView.findViewById(R.id.interestRate);
                mButtonDelete = (ImageButton) itemView.findViewById(R.id.btDelete);
                mButtonEdit = (ImageButton) itemView.findViewById(R.id.btEdit);
                // TODO: 11/24/2016 view event and update
            }
        }
    }
}
