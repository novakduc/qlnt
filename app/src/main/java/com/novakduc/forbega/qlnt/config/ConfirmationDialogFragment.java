package com.novakduc.forbega.qlnt.config;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.novakduc.forbega.qlnt.R;

/**
 * Created by n.thanh on 11/17/2016.
 */

public class ConfirmationDialogFragment extends DialogFragment {
    public static final int RESULT_OK = 1;
    public static final int RESULT_CANCEL = 0;
    private ConfirmListener callback;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        callback = (ConfirmListener) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.project_create_discard)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.discardDialog(RESULT_OK);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.discardDialog(RESULT_CANCEL);
                    }
                });
        return builder.create();
    }

    public interface ConfirmListener {
        public void discardDialog(int result);
    }
}
