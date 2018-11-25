package ru.arlen.androidservices;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
    private static final String STARTED_ACTIVITY = "STARTED_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View btnStart = findViewById(R.id.buttonStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w(STARTED_ACTIVITY, "startService");
                startService(MyService.newIntent(MainActivity.this));
            }
        });
        View btnStop = findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w(STARTED_ACTIVITY, "StopActivity");
                stopService(MyService.newIntent(MainActivity.this));
            }
        });
        View btnStartA = findViewById(R.id.btnStartActivity);
        btnStartA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w(STARTED_ACTIVITY, "StartActivity");
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });
    }
}
