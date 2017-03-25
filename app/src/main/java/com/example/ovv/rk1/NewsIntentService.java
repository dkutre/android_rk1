package com.example.ovv.rk1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;

import android.content.SharedPreferences;
import android.net.Uri;
import java.net.HttpURLConnection;
import java.net.URL;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import java.io.IOException;

import ru.mail.weather.lib.News;
import ru.mail.weather.lib.Storage;


public class NewsIntentService extends IntentService {
    public NewsIntentService() {
        super("NewsIntentService");
    }

    private final static String API_KEY = "5s4gTp3TqOmsh7ZiGvYJa4ZbOwFjp1bhN4ZjsnsE4WgnFenOyM";
    private final static String METHOD_URL = "https://community-bitcointy.p.mashape.com/average/";

    public static final String EXTRA_NEWS_RESULT_RECEIVER = "com.example.ovv.rk1.RECEIVER";
    private static final String TAG = NewsIntentService.class.getSimpleName();
    public static final String ACTION_GET_NEWS = "com.example.ovv.rk1.action.GET_NEWS";

    public final static String EXTRA_NEWS_RESULT = "extra.EXTRA_NEWS_RESULT";
    public final static int RESULT_SUCCESS = 1;

    public final static String STORAGE_PRICE = "PRICE";
    public final static String STORAGE_CURRENCY = "currency";
    public final static String EXTRA_PRICE = "EXTRA_PRICE";
    public final static String EXTRA_CURRENCY = "EXTRA_CURRENCY";


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

    private Bundle loadData() {
        Log.e(TAG, "loadData()");
        Bundle data = new Bundle();
        myStorage storage = myStorage.getInstance(this);
        String saved_currency = storage.loadString(STORAGE_CURRENCY);
        try {
            final String uri = Uri.parse(METHOD_URL + saved_currency)
                    .buildUpon()
                    .build()
                    .toString();

            HttpURLConnection conn = (HttpURLConnection) new URL(uri).openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("X-Mashape-Key", API_KEY);
            conn.setRequestProperty("Accept", "text/plain");
            conn.connect();
            int responseCode = conn.getResponseCode();
            InputStream  input_stream = conn.getInputStream();

            if(responseCode == HttpURLConnection.HTTP_OK) {
                String result = inputStreamToString(input_stream);
                Log.v("CatalogClient", result);
                storage.saveString(STORAGE_PRICE, result);
            }
            return data;
        } catch (IOException ex) { // нет интернета грузим из кэша
            return data;
        }
    }

    private static String inputStreamToString(final InputStream is) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while((line = bufferedReader.readLine()) != null){
            stringBuilder.append(line + "_");
        }
        return stringBuilder.toString();
    }

    private void handleActionGetNews(ResultReceiver receiver) {
        //TODO скачать данные или взять из хранилища и послать send у receiver
        Log.e(TAG, "handleActionGetNews()");
        Bundle data = loadData();
        receiver.send(1, data);
    }
}
