package com.novakduc.forbega.qlnt.ui.config;

import com.novakduc.forbega.qlnt.utilities.ActivityListener;

/**
 * Created by n.thanh on 4/18/2017.
 */

public interface UpdateListener extends ActivityListener {

    void updateProjectId(long projectId);

    void finalCheck();

    void saveProject();
}
