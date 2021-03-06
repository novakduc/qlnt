package com.novakduc.forbega.qlnt.utilities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Novak on 4/30/2017.
 */

public class DatePickerFragment extends android.support.v4.app.DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    //Date picker
    public static final String PICKED_DATE = "qlnt.config.base.startDate";
    public static final int START_DATE_PICKED = 0;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dateOfMonth) {
        Calendar setDate = Calendar.getInstance();
        setDate.set(Calendar.YEAR, year);
        setDate.set(Calendar.MONTH, month);
        setDate.set(Calendar.DAY_OF_MONTH, dateOfMonth);

        Intent intent = new Intent();
        intent.putExtra(PICKED_DATE, setDate.getTimeInMillis());

        getTargetFragment().onActivityResult(START_DATE_PICKED, AppCompatActivity.RESULT_OK, intent);
    }
}
