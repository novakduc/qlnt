package com.novakduc.forbega.qlnt.model;

import java.util.ArrayList;

/**
 * Created by n.thanh on 9/21/2016.
 */
public class LoanList extends ArrayList {
    private static Long mProjectId;

    public LoanList(Long projectId) {
        super();
        mProjectId = projectId;
    }
}
