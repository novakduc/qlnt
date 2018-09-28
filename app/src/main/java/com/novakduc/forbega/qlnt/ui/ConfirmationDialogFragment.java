package com.novakduc.forbega.qlnt.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import com.novakduc.forbega.qlnt.R;

/**
 * Created by n.thanh on 11/17/2016.
 */

public class ConfirmationDialogFragment extends android.support.v4.app.DialogFragment {
    public static final int RESULT_OK = 1001;
    public static final int RESULT_CANCEL = 1000;
    public static final java.lang.String MESSAGE = "com.novakduc.forbega.qlnt.confirmation_message";
    public static final java.lang.String KEY = "com.novakduc.forbega.qlnt.confirmation_reuestSource";
    private ConfirmListener callback;
    private String mMessage;
    private String mConfirmPurposeKey;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mMessage = getArguments().getString(MESSAGE);
        mConfirmPurposeKey = getArguments().getString(KEY);
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
                        callback.action(RESULT_OK, mConfirmPurposeKey);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.action(RESULT_CANCEL, mConfirmPurposeKey);
                    }
                });
        return builder.create();
    }

    public interface ConfirmListener {
        void action(int result, String confirmPurposeKey);
    }

    public static void showDialog(String pMessage, String pConfirmPurposeKey, FragmentManager pFragmentManager) {
        Bundle bundle = new Bundle();
        //dialog title in bundle
        bundle.putString(ConfirmationDialogFragment.MESSAGE,
                pMessage);
        bundle.putString(KEY, pConfirmPurposeKey);
        android.support.v4.app.DialogFragment dialogFragment = new ConfirmationDialogFragment();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(pFragmentManager, "discardConfirm");
    }
}
