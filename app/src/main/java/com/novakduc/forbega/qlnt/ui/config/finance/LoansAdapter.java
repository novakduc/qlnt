package com.novakduc.forbega.qlnt.ui.config.finance;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.data.database.Loan;
import com.novakduc.forbega.qlnt.utilities.ConverterUtilities;
import com.novakduc.forbega.qlnt.utilities.CurrencyUnit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Nguyen Quoc Thanh on 12/21/2017.
 */

public final class LoansAdapter extends RecyclerView.Adapter<LoansAdapter.ViewHolder> {
    public static final int LOAN_CREATION = 1120;
    public static final int LOAN_EDIT_REQUEST_FROM_ADAPTER = 1121;
    private Context mContext;
    private List<Loan> mLoans;
    private LoanAdapterHandler mContainerCallBack;

    public LoansAdapter(Context context, LoanAdapterHandler containerCallBack) {
        mContext = context;
        mContainerCallBack = containerCallBack;
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
        holder.mTextViewLoanAmount.setText(ConverterUtilities.currencyUnitConverterToString(
                loan.getAmount(), CurrencyUnit.MIL_BASE, 3));
        holder.mTextViewInterestRate.setText(String.valueOf(loan.getInterestRate()));
        holder.mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContainerCallBack.deleteLoan(loan.getId());
            }
        });
        holder.mButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContainerCallBack.editLoan(loan.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mLoans == null) {
            return 0;
        }
        return mLoans.size();
    }

    public void swapList(List<Loan> newList) {
        mLoans = newList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewBankName, mTextViewStartDate, mTextViewLoanAmount, mTextViewInterestRate;
        ImageButton mButtonEdit, mButtonDelete;

        @SuppressLint("WrongViewCast")
        public ViewHolder(View itemView) {
            super(itemView);

            mTextViewBankName = itemView.findViewById(R.id.textViewBankName);
            mTextViewStartDate = itemView.findViewById(R.id.textViewStartDate);
            mTextViewLoanAmount = itemView.findViewById(R.id.txtViewLoanAmount);
            mTextViewInterestRate = itemView.findViewById(R.id.interestRate);
            mButtonDelete = itemView.findViewById(R.id.btDelete);
            mButtonEdit = itemView.findViewById(R.id.btEdit);
        }
    }
}
