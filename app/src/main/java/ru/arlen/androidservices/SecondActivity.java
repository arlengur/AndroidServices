package ru.arlen.androidservices;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

import static ru.arlen.androidservices.MyService.newIntent;

public class SecondActivity extends Activity {
    private static final String SECOND_ACTIVITY = "SECOND_ACTIVITY";
    private MyService mBoundService;
    private Random random;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBoundService = ((MyService.MyBinder) service).getService();
            Log.w(SECOND_ACTIVITY, "connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBoundService = null;
            Log.w(SECOND_ACTIVITY, "disconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        random = new Random();
        final TextView textSum = findViewById(R.id.textSum);
        View btnSum = findViewById(R.id.buttonSum);
        btnSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w(SECOND_ACTIVITY, "Get Sum");
                int a = random.nextInt(10);
                int b = random.nextInt(10);
                int sum = mBoundService.getSum(a, b);
                textSum.setText(a + " + " + b + " = " + sum);
            }
        });
        View btnBack = findViewById(R.id.buttonBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w(SECOND_ACTIVITY, "Back");
                startActivity(new Intent(SecondActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindService(newIntent(this), mServiceConnection, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(mServiceConnection);
    }
}
