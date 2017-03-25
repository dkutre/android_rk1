package com.example.ovv.rk1;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by ovv on 07.03.2017.
 */

public class ServiceHelper {
    private static final String TAG = ServiceHelper.class.getSimpleName();
    private int requestCounter;

    private static final ServiceHelper instance = new ServiceHelper();
    private final Map<Integer, NewsResultReceiver> ResultReceivers = new Hashtable<>();

    private ServiceHelper() {
        requestCounter = 1;
    }

    public static ServiceHelper getInstance() {
        return instance;
    }

    public interface NewsResultListener {
        void checkResult(boolean resultCode);
    }
    //TODO функциz загрузки новостей
    int loadNews(final Context context, final  NewsResultListener listener) {
        Log.e(TAG, "loadNews()");
        final NewsResultReceiver receiver = new NewsResultReceiver(requestCounter, new Handler());
        receiver.setListener(listener);
        ResultReceivers.put(requestCounter, receiver);

        Intent intent = new Intent(context, NewsIntentService.class);
        intent.setAction(NewsIntentService.ACTION_GET_NEWS);
        intent.putExtra(NewsIntentService.EXTRA_NEWS_RESULT_RECEIVER, receiver);
        context.startService(intent);

        return requestCounter++;
    }

    void removeListener(final int id) {
        Log.e(TAG, "removeListener()");
        NewsResultReceiver receiver = ResultReceivers.remove(id);
        if (receiver != null) {
            receiver.setListener(null);
        }
    }
}
