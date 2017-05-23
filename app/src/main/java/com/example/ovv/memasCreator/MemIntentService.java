package com.example.ovv.memasCreator;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.io.IOException;


public class MemIntentService extends IntentService {
    public final static String EXTRA_MEM_IMAGE_NAME = "extra.MEM_IMAGE_NAME";
    public final static String EXTRA_MEM_TOP_TEXT = "extra.MEM_TOP_TEXT";
    public final static String EXTRA_MEM_BOT_TEXT = "extra.MEM_BOT_TEXT";


    public final static String ACTION_NEWS_RESULT_SUCCESS = "action.ACTION_NEWS_RESULT_SUCCESS";
    public final static String ACTION_NEWS_RESULT_ERROR = "action.ACTION_NEWS_RESULT_ERROR";

    public MemIntentService() {
        super("MemIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String imageName = intent.getStringExtra(EXTRA_MEM_IMAGE_NAME);
        final String topText= intent.getStringExtra(EXTRA_MEM_TOP_TEXT);
        final String botText= intent.getStringExtra(EXTRA_MEM_BOT_TEXT);
        boolean success;

        try {
            MemLoader loader = new MemLoader();
            MyStorage storage = MyStorage.getInstance(getApplicationContext());
            String mem = loader.loadMem(imageName, topText, botText);
            storage.saveMem(mem);
            success = true;
        } catch (IOException ex) {
            success = false;
        }

        final Intent intentBroadcast = new Intent(success ? ACTION_NEWS_RESULT_SUCCESS :
                ACTION_NEWS_RESULT_ERROR);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intentBroadcast);
    }
}