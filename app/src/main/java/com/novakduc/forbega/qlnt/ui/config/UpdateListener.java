package com.novakduc.forbega.qlnt.ui.config;

import com.novakduc.forbega.qlnt.data.database.Loan;
import com.novakduc.forbega.qlnt.data.database.LoanList;
import com.novakduc.forbega.qlnt.data.database.UnitPrice;

/**
 * Created by n.thanh on 4/18/2017.
 */

public interface UpdateListener {
    void discardConfirmation(int messageId);

    void updateBase(String name, String address, int duration, long startDate);

    void updateFinance(long investment, LoanList<Loan> loanLoanList);

    void updateUnitPrice(UnitPrice unitPrice);

    void addProject();
}
