package com.example.ovv.rk1;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

/**
 * Created by ovv on 07.03.2017.
 */

public class NewsResultReceiver extends ResultReceiver {
    private static final String TAG = NewsResultReceiver.class.getSimpleName();
    private ServiceHelper.NewsResultListener listener;
    private int requestId_;

    public NewsResultReceiver(int requestId, Handler handler) {
        super(handler);
        this.requestId_ = requestId;
    }


    public void setListener(ServiceHelper.NewsResultListener listener) {
        Log.e(TAG, "setListener()");
        this.listener = listener;
    }

    @Override
    protected void onReceiveResult(final int resultCode, final Bundle resultData) {
        Log.e(TAG, "onReceiveResult()");
        if (listener != null) {
            final boolean success = (resultCode == NewsIntentService.RESULT_SUCCESS);
            if (success) {
                final String name = (resultData.getString(NewsIntentService.EXTRA_NEWS_NAME_RESULT));
                final String text = (resultData.getString(NewsIntentService.EXTRA_NEWS_TEXT_RESULT));
                final long date = (resultData.getLong(NewsIntentService.EXTRA_NEWS_DATE_RESULT));
                listener.checkResult(success, name, text, date);
            } else {
                listener.checkResult(success, resultData.getString(NewsIntentService.EXTRA_NEWS_RESULT), "ERROR", 0L);
            }
        }
        ServiceHelper.getInstance().removeListener(requestId_);
    }
}
