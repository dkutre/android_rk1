package com.example.ovv.memasCreator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SecondActivity extends AppCompatActivity {
    private MyStorage storage;
    private String[] imagesNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        storage = MyStorage.getInstance(getApplicationContext());
        imagesNames = getResources().getStringArray(R.array.array_images);
        ListView lvImages = (ListView) findViewById(R.id.lv_images);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, imagesNames);

        lvImages.setAdapter(adapter);
        lvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                if (pos >= 0 && pos < imagesNames.length)
                    storage.saveImageName(imagesNames[pos]);
                else
                    storage.saveImageName("DarthVader");
                finish();
            }
        });
    }
}
