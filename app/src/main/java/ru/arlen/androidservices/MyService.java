package ru.arlen.androidservices;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private static final String MY_SERVICE = "MY_SERVICE";
    private volatile boolean interrupt;
    private IBinder mBinder = new MyBinder();

    public class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w(MY_SERVICE, "onStartCommand");

        new Thread(new Runnable() {
            @Override
            public void run() {
                int step = 0;
                while (true) {
                    Log.w(MY_SERVICE, "we'are doing something: " + step++);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // Do nothing

                    }
                    if (interrupt) {
                        Log.w(MY_SERVICE, "Interrupted");
                        break;
                    }
                }
            }
        }).start();
        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.w(MY_SERVICE, "onBind");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.w(MY_SERVICE, "onUnbind");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.w(MY_SERVICE, "onDestroy");
        interrupt = true;
    }

    public static Intent newIntent(Context context){
        return new Intent(context, MyService.class);
    }

    public int getSum(int a, int b) {
        return a + b;
    }
}
