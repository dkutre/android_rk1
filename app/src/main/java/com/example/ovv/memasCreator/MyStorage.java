package com.example.ovv.memasCreator;

import android.content.Context;
import android.content.SharedPreferences;


public class MyStorage {

    private static MyStorage INSTANCE;
    private SharedPreferences preferences;
    private String MEM = "Mem";
    private String IMAGE_NAME = "image_name";

    public static synchronized MyStorage getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new MyStorage(context.getApplicationContext());
        }
        return INSTANCE;
    }

    private MyStorage(Context context) {
        this.preferences = context.getSharedPreferences(MEM, 0);
    }

    public void saveMem(String mem) {
        if (mem == null) {
            this.preferences.edit().remove(MEM).apply();
        } else {
            this.preferences.edit().putString(MEM, mem).apply();
        }
    }

    public String getLastSavedMem() {
        String result = this.preferences.getString(MEM, "");
        return result.isEmpty() ? null : result;
    }

    public String loadImageName() {
        return this.preferences.getString(IMAGE_NAME, "");
    }

    public void saveImageName(String imageName) {
        this.preferences.edit().putString(IMAGE_NAME, imageName).apply();
    }

}
