package com.example.ovv.rk1;

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
    String[] topics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news_category_a);

        final Storage storage = Storage.getInstance(this);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.category_linear_layout);

        topics = Topics.ALL_TOPICS;
        for (int i = 0; i < topics.length; i++) {
            final Button button = new Button(this);

            ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            button.setLayoutParams(layoutParams);
            button.setText(topics[i]);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String topics = button.getText().toString();
                    storage.saveCurrentTopic(topics);
                    finish();
                }
            });

            linearLayout.addView(button);
        }
    }

}
