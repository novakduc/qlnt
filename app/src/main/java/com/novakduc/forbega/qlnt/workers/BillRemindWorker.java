package com.novakduc.forbega.qlnt.workers;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;

import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.utilities.Constants;

import androidx.work.Result;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class BillRemindWorker extends Worker {
    private static final String TAG = BillRemindWorker.class.getSimpleName();

    public BillRemindWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context context = getApplicationContext();
        String roomName = getInputData().getString(Constants.ROOM_NAME_KEY);
        String message = context.getString(R.string.bill_reminder) + " " + roomName;
        try {
            WorkerUtils.makeStatusNotification(message, context);
            return Result.success();
        } catch (Throwable pThrowable) {
            Log.e(TAG, "Asistant error.");
            return Result.failure();
        }
    }
}
