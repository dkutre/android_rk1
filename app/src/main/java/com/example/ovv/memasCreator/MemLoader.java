package com.example.ovv.memasCreator;

import android.util.Base64;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;


public class MemLoader {
    private final String DOWNLOAD_MEM_URL = "https://ronreiter-meme-generator.p.mashape.com/";
    private final OkHttpClient httpClient = new OkHttpClient();

    public MemLoader() {};

    public String loadMem(String imageName, String topText, String botText) throws IOException {
        Request request = (new Builder()).url(DOWNLOAD_MEM_URL +
                "meme?bottom=" +botText +
                "&font=Impact&font_size=50&top=" + topText +
                "&meme=" + imageName)
                .addHeader("X-Mashape-Key", "5s4gTp3TqOmsh7ZiGvYJa4ZbOwFjp1bhN4ZjsnsE4WgnFenOyM")
                .build();
        Response response = this.httpClient.newCall(request).execute();

        byte[] var;
        String result;

        try {
            if (!response.isSuccessful()) {
                throw new IOException("Wrong status: " + response.code() + "; body: " + response.body().string());
            }
            var = response.body().bytes();
            result = Base64.encodeToString(var, Base64.DEFAULT);
        } finally {
            response.body().close();
        }
        return result;
    }
}