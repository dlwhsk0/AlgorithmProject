package com.cookandroid.algorithmproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            //딜레이 후 시작할 코드
            Intent intent = new Intent(getApplicationContext(), StartActivity.class);
            startActivity(intent);
        }, 3000); // 3초 정도 딜레이를 준 후 시작
    }

}