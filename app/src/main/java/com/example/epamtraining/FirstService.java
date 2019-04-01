package com.example.epamtraining;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class FirstService extends Service {

    private static final String TAG = "FirstService";
    public static final String EXTRA_MESSAGE = "message";
    private String messageToBroadcastReceiver = "some message for task";
    private Intent broadcastIntent =  new Intent("com.example.epamtraining.CUSTOM_ACTION");

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        broadcastIntent.putExtra(EXTRA_MESSAGE, messageToBroadcastReceiver);
        sendBroadcast(broadcastIntent);

        Log.i(TAG, "Broadcast sended");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Toast.makeText(this, "Service was stopped", Toast.LENGTH_LONG)
                .show();
    }
}
