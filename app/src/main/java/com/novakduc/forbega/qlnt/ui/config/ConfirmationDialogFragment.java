package com.novakduc.forbega.qlnt.ui.config;

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
    public static final int RESULT_OK = 1001;
    public static final int RESULT_CANCEL = 1000;
    public static final java.lang.String MESSAGE = "com.novakduc.forbega.qlnt.confirmation_message";
    private ConfirmListener callback;
    private String mMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mMessage = getArguments().getString(MESSAGE);
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        callback = (ConfirmListener) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(mMessage)
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
        void discardDialog(int result);
    }
}