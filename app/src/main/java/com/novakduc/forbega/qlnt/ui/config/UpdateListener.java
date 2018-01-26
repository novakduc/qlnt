package com.novakduc.forbega.qlnt.ui.config;

import com.novakduc.forbega.qlnt.data.database.LoanList;
import com.novakduc.forbega.qlnt.data.database.UnitPrice;

/**
 * Created by n.thanh on 4/18/2017.
 */

public interface UpdateListener {
    void discardConfirmation(int messageId);

    void updateFinance(long investment, LoanList loanLoanList);

    void updateUnitPrice(UnitPrice unitPrice);

    void addProject();
}
