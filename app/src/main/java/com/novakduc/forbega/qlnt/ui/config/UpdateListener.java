package com.novakduc.forbega.qlnt.ui.config;

/**
 * Created by n.thanh on 4/18/2017.
 */

public interface UpdateListener {
    void discardConfirmation(int messageId);

    void addProject();

    void updateProjectId(long projectId);
}
