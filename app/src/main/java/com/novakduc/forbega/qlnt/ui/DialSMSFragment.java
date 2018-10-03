package com.novakduc.forbega.qlnt.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.novakduc.forbega.qlnt.R;

public class DialSMSFragment extends Fragment {
    protected void sendSMS(String pPhoneNo, String pText) {
        Log.i("Send SMS", "");
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", pPhoneNo.trim());
        smsIntent.putExtra("sms_body", pText);

        try {
            startActivity(smsIntent);
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(),
                    getString(R.string.sms_fail), Toast.LENGTH_SHORT).show();
        }
    }

    protected void dial(String phoneNo) {
        String uri = "tel:" + phoneNo.trim() ;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException pE) {
            Toast.makeText(getContext(), getString(R.string.call_fail), Toast.LENGTH_SHORT).show();
        }
    }
}
