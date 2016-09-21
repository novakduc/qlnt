package com.novakduc.forbega.qlnt.model;

import java.util.ArrayList;

/**
 * Created by n.thanh on 9/21/2016.
 */
public class LoanList extends ArrayList {
    private static LoanList ourInstance = new LoanList();
    private static Long mProjectId;

    private LoanList() {
        super();
    }

    public static LoanList getInstance(Long projectId) {
        mProjectId = projectId;
        return ourInstance;
    }
}
