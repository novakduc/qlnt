package com.novakduc.forbega.qlnt.config.finance;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.config.ProjectCreateConfirmationFragment;
import com.novakduc.forbega.qlnt.model.CurrencyUnit;
import com.novakduc.forbega.qlnt.model.Loan;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Nguyen Quoc Thanh on 12/21/2017.
 */

public final class LoansAdapter extends RecyclerView.Adapter<LoansAdapter.ViewHolder> {
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
            final Loan loan = mLoans.get(position);
            holder.mTextViewBankName.setText(loan.getName());
            Date date = new Date();
            date.setTime(loan.getLoanDate());
            DateFormat format = SimpleDateFormat.getDateInstance();
            holder.mTextViewStartDate.setText(format.format(date));
            holder.mTextViewLoanAmount.setText(String.valueOf(loan.getAmount(CurrencyUnit.MIL_BASE)));
            holder.mTextViewInterestRate.setText(String.valueOf(loan.getInterestRate()));
            holder.mButtonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager manager = ((AppCompatActivity) mContext).getFragmentManager();
                    manager.beginTransaction().replace(R.id.fragmentContainer,
                            ProjectLoanDeclareFragment.newIntance(loan)).addToBackStack(null).commit();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mLoans.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView mTextViewBankName, mTextViewStartDate, mTextViewLoanAmount, mTextViewInterestRate;
            ImageButton mButtonEdit, mButtonDelete;

            public ViewHolder(View itemView) {
                super(itemView);

                mTextViewBankName = itemView.findViewById(R.id.textViewBankName);
                mTextViewStartDate = itemView.findViewById(R.id.textViewStartDate);
                mTextViewLoanAmount = itemView.findViewById(R.id.txtViewLoanAmount);
                mTextViewInterestRate = itemView.findViewById(R.id.interestRate);
                mButtonDelete = itemView.findViewById(R.id.btDelete);
                mButtonEdit = itemView.findViewById(R.id.btEdit);
                // TODO: 11/24/2016 view event and update
            }
        }
}
