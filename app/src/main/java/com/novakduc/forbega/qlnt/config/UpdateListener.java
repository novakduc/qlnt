package com.novakduc.forbega.qlnt.config;

import com.novakduc.forbega.qlnt.model.Loan;
import com.novakduc.forbega.qlnt.model.LoanList;

/**
 * Created by n.thanh on 4/18/2017.
 */

public interface UpdateListener {
    void discardConfirmation();

    void updateBase(String name, String address, int duration, long startDate);

    void updateFinance(long investment, LoanList<Loan> loanLoanList);
}
