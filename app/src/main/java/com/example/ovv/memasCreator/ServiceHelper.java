package com.example.ovv.memasCreator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

class ServiceHelper {
    private MemResultListener memListener;
    private static ServiceHelper instance;

    private ServiceHelper() {
    }

    synchronized static ServiceHelper getInstance(final Context context) {
        if (instance == null) {
            instance = new ServiceHelper();
            instance.initBroadcastReceiver(context);
        }
        return instance;
    }

    private void initBroadcastReceiver(Context context) {
        final IntentFilter filter = new IntentFilter();
        filter.addAction(MemIntentService.ACTION_NEWS_RESULT_SUCCESS);
        filter.addAction(MemIntentService.ACTION_NEWS_RESULT_ERROR);

        LocalBroadcastManager.getInstance(context).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                if (memListener != null) {
                    final boolean success = intent.getAction().equals(MemIntentService.ACTION_NEWS_RESULT_SUCCESS);
                    memListener.onMemResult(success);
                }
            }
        }, filter);
    }

    void getMemes(final Context context, final MemResultListener listener, final Memas mem) {
        memListener = listener;

        Intent intent = new Intent(context, MemIntentService.class);
        intent.putExtra(MemIntentService.EXTRA_MEM_IMAGE_NAME, mem.getImageName());
        intent.putExtra(MemIntentService.EXTRA_MEM_TOP_TEXT,mem.getTopText());
        intent.putExtra(MemIntentService.EXTRA_MEM_BOT_TEXT,mem.getBotText());
        context.startService(intent);
    }

    void removeListener() {
        memListener = null;
    }

    interface MemResultListener {
        void onMemResult(final boolean success);
    }
}