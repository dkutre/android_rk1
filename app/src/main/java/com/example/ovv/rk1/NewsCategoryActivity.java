package com.example.ovv.rk1;

import android.app.IntentService;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import ru.mail.weather.lib.Storage;
import ru.mail.weather.lib.Topics;

public class NewsCategoryActivity extends AppCompatActivity {
    private static final String TAG = NewsCategoryActivity.class.getSimpleName();
    private String[] curency = {"USD", "RUB"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news_category_a);

        final myStorage storage = myStorage.getInstance(this);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.category_linear_layout);

        for (int i = 0; i < curency.length; i++) {
            final Button button = new Button(this);

            ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            button.setLayoutParams(layoutParams);
            button.setText(curency[i]);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String currency = button.getText().toString();
                    storage.saveString(NewsIntentService.STORAGE_CURRENCY, currency);
                    finish();
                }
            });

            linearLayout.addView(button);
        }
    }

}
