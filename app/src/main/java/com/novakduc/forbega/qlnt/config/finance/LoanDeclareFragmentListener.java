package com.novakduc.forbega.qlnt.config.finance;

/**
 * Created by Novak on 4/30/2017.
 */

public interface LoanDeclareFragmentListener {
    void discardConfirm();

    void loanUpdate(String bankName, long amount, float rate, long loanDate);
}
