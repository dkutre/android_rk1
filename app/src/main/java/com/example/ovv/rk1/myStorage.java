package com.example.ovv.rk1;

import android.content.Context;
import android.content.SharedPreferences;

import ru.mail.weather.lib.Storage;

/**
 * Created by ovv on 21.03.2017.
 */

public class myStorage {
    public static final String myStorage = "myPreferences";
    private static myStorage INSTANCE;
    SharedPreferences preferences;
    public static final String EMPTY = "EMPTY";

    public static synchronized myStorage getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = new myStorage(context.getApplicationContext());
        }
        return INSTANCE;
    }

    private myStorage(Context context) {
        this.preferences = context.getSharedPreferences("News", 0);
    }

    public String loadString(String key) {
        return preferences.getString(key, EMPTY);
    }

    public void saveString(String key, String data) {
        preferences.edit().putString(key, data).apply();
    }

}
