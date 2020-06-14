package com.example.pemetaanpuradibali;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splashscreen extends AppCompatActivity {
    private final int panjang_splash=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent tampil = new Intent(splashscreen.this,MainActivity.class);
                splashscreen.this.startActivity(tampil);
                splashscreen.this.finish();
            }
        }, panjang_splash);

    }
}
