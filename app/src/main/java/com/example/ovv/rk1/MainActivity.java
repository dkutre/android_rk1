package com.example.ovv.rk1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ServiceHelper.NewsResultListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Button refresh;
    private Button gotoSecondActivity;
    private TextView price;
    private int requestId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        refresh = (Button) findViewById(R.id.btn_refresh);
        gotoSecondActivity  = (Button) findViewById(R.id.btn_choose_topic);

        price = (TextView) findViewById(R.id.tv_price);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (final View v) {
                loadNews();
            }
        });

        gotoSecondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (final View w) {
                Intent intent = new Intent(MainActivity.this, NewsCategoryActivity.class);
                startActivity(intent);
            }
        });

    }

    void loadNews() {
        Log.e(TAG, "loadNews()");
        if (requestId == 0) {
            requestId = ServiceHelper.getInstance().loadNews(this, this);
            /*
            в loadNews происходит подписка на serviceHelper, происходит это каждый раз при клике на refresh или перехода в
            onResume(), поэтому отписываться надо в onPause
            */
        } else {
            Toast.makeText(this, "There is pending request", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNews();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        ServiceHelper.getInstance().removeListener(requestId); // отписываемся
        super.onDestroy();
    }

    @Override
    public void checkResult(boolean resultCode) {
        Log.e(TAG, "checkResult()");
        requestId = 0;
        myStorage storage = myStorage.getInstance(this);
        String new_price = storage.loadString(NewsIntentService.STORAGE_PRICE);
        String currency = storage.loadString(NewsIntentService.STORAGE_CURRENCY);

        if (resultCode) {
            price.setText(new_price + currency);
        } else {
            price.setText("ERROR");
        }
    }
}
