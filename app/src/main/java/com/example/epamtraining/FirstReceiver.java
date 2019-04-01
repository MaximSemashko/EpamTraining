package com.example.epamtraining;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import static com.example.epamtraining.FirstService.EXTRA_MESSAGE;

public class FirstReceiver extends BroadcastReceiver {

    private static final String TAG = "FirstReceiver";
    private String messageFromService;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            messageFromService = bundle.getString(EXTRA_MESSAGE);
            Toast.makeText(context, messageFromService, Toast.LENGTH_SHORT).show();

            Log.i(TAG, "Message from service: "+messageFromService);
        }
    }
}
