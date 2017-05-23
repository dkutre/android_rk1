package com.example.ovv.memasCreator;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ServiceHelper.MemResultListener {

    private MyStorage storage;
    private ImageView iv;
    private EditText topText, botText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storage = MyStorage.getInstance(getApplicationContext());

        topText = (EditText) findViewById(R.id.topText);
        botText = (EditText) findViewById(R.id.botText);
        Button getMemes = (Button) findViewById(R.id.btn_getMem);
        Button setPicture = (Button) findViewById(R.id.set_picture);
        iv = (ImageView) findViewById(R.id.iv1);

        getMemes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMemes();
            }
        });

        setPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getMemes() {
        Memas mem = new Memas(topText.getText().toString(), botText.getText().toString(), getImageName());
        ServiceHelper.getInstance(this).getMemes(this, this, mem);
    }

    private String getImageName() {
        String imageName = storage.loadImageName();
        if (!imageName.isEmpty())
            return imageName;
        else
            return "DatrVader";
    }

    private void updateMem() {
        String mem = storage.getLastSavedMem();
        if (mem != null) {
            byte[] img = Base64.decode(mem, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            if (bitmap != null) {
                iv.setImageBitmap(bitmap);
            }
        } else {
            System.out.println("Error mem update");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMemes();
    }

    @Override
    protected void onStop() {
        ServiceHelper.getInstance(this).removeListener();
        super.onStop();
    }

    @Override
    public void onMemResult(final boolean success) {
        if (success)
            updateMem();
        else
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.info),
                    Toast.LENGTH_SHORT).show();
    }
}
