package com.example.ovv.rk1;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import ru.mail.weather.lib.News;
import ru.mail.weather.lib.NewsLoader;
import ru.mail.weather.lib.Storage;


public class NewsIntentService extends IntentService {
    public NewsIntentService() {
        super("NewsIntentService");
    }

    private static final String TAG = NewsIntentService.class.getSimpleName();
    public static final String ACTION_GET_NEWS = "com.example.ovv.rk1.action.GET_NEWS";
    public final static String EXTRA_NEWS_RESULT_RECEIVER = "extra.EXTRA_YODA_RESULT_RECEIVER";

    public final static String EXTRA_NEWS_RESULT = "extra.EXTRA_NEWS_RESULT";
    public final static int RESULT_SUCCESS = 1;
    public final static String EXTRA_NEWS_NAME_RESULT = "extra.EXTRA_NEWS_NAME_RESULT";
    public final static String EXTRA_NEWS_TEXT_RESULT = "extra.EXTRA_NEWS_TEXT_RESULT";
    public final static String EXTRA_NEWS_DATE_RESULT = "extra.EXTRA_NEWS_DATE_RESULT";



    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(TAG, "onHandleIntent()");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_NEWS.equals(action)) {
                final ResultReceiver receiver = intent.getParcelableExtra(EXTRA_NEWS_RESULT_RECEIVER);
                handleActionGetNews(receiver);
            }
        }
    }

    private Bundle loadData(String newsCategory) {
        Log.e(TAG, "loadData()");

        Storage storage = Storage.getInstance(this);
        Bundle data = new Bundle();
        try {
            NewsLoader loader = new NewsLoader();

            News news = loader.loadNews(newsCategory);

            storage.saveNews(news);

            data.putString(EXTRA_NEWS_NAME_RESULT, news.getTitle());
            data.putString(EXTRA_NEWS_TEXT_RESULT, news.getBody());
            data.putLong(EXTRA_NEWS_DATE_RESULT, news.getDate());
            return data;
        } catch (IOException ex) { // нет интернета грузим из кэша
            Log.e(TAG, ex.getMessage());
            data = getSavedData();
            return data;
        }
    }

    private Bundle getSavedData() {
        Log.e(TAG, "getSavedData()");
        Bundle data = new Bundle();
        Storage storage = Storage.getInstance(this);
        News news = storage.getLastSavedNews();
        data.putString(EXTRA_NEWS_NAME_RESULT, news.getTitle());
        data.putString(EXTRA_NEWS_TEXT_RESULT, news.getBody());
        data.putLong(EXTRA_NEWS_DATE_RESULT, news.getDate());
        return data;
    }

    private void handleActionGetNews(ResultReceiver receiver) {
        //TODO скачать данные или взять из хранилища и послать send у receiver
        Log.e(TAG, "handleActionGetNews()");
        Storage storage = Storage.getInstance(this);
        String topic = storage.loadCurrentTopic();
        Bundle data = loadData(topic);
        receiver.send(1, data);
    }
}
