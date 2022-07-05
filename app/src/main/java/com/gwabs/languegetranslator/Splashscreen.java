package com.gwabs.languegetranslator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);



        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                transit_To_Home();
                finish();
            }
        }, 4000);
    }

    private void transit_To_Home(){
        Intent intent  = new Intent(Splashscreen.this,MainActivity.class);
        startActivity(intent);
    }
}