package com.example.ovv.rk1;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.leakcanary.LeakCanary;

import org.w3c.dom.Text;
import ru.mail.weather.lib.News;
import ru.mail.weather.lib.Storage;


public class MainActivity extends AppCompatActivity implements ServiceHelper.NewsResultListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Button refresh;
    private Button onBackgroundUpdate;
    private Button offBackgroundUpdate;
    private Button gotoSecondActivity;
    private TextView newsName;
    private TextView newsText;
    private TextView newsDate;
    private int requestId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        refresh = (Button) findViewById(R.id.btn_refresh);
        onBackgroundUpdate  = (Button) findViewById(R.id.btn_on_background_upd);
        offBackgroundUpdate = (Button) findViewById(R.id.btn_off_background_upd);
        gotoSecondActivity  = (Button) findViewById(R.id.btn_choose_topic);

        newsName = (TextView) findViewById(R.id.tv_news_name);
        newsText = (TextView) findViewById(R.id.tv_news_text);
        newsDate = (TextView) findViewById(R.id.tv_news_date);

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
        //TODO обработка в фоне

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
       // loadNews();
    }

    @Override
    protected void onPause() {
        ServiceHelper.getInstance().removeListener(requestId); // отписываемся
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //todo проследить за утечками
    }

    @Override
    public void checkResult(boolean resultCode, String name, String text, Long date) {
        //вообще не стоит передавать какие-либо параметры кроме кода результата
        //т.к их можно взять из storage
        Log.e(TAG, "checkResult()");
        requestId = 0;
        if (resultCode) {
            newsName.setText(name);
            newsText.setText(text);
            newsDate.setText(date.toString());
        } else {
            newsText.setText("ERROR");
        }
    }
}
