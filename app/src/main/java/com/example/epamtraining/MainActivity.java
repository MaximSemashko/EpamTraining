package com.example.epamtraining;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private static final String FRAGMENT_TAG = "firstFragment";
    private static final String TAG = "MainActivity";
    private static final String BROADCAST_ACTION = "com.example.epamtraining.CUSTOM_ACTION";

    private Button startServiceButton;
    private Button stopServiceButton;

    private FirstReceiver firstReceiver;
    private Fragment firstFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstReceiver = new FirstReceiver();

        if (savedInstanceState == null) {
            firstFragment = new FirstFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, firstFragment, FRAGMENT_TAG)
                    .commit();
        } else {
            firstFragment = getSupportFragmentManager()
                    .findFragmentByTag(FRAGMENT_TAG);
        }

        startServiceButton = findViewById(R.id.start_button);
        stopServiceButton = findViewById(R.id.stop_button);

        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, getString(R.string.service_start));
                Intent intent = new Intent(MainActivity.this, FirstService.class);
                startService(intent);
            }
        });

        stopServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, getString(R.string.service_stop));
                Intent intent = new Intent(MainActivity.this, FirstService.class);
                stopService(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        
        Log.i(TAG, "onStart: registered receiver");
        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(firstReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(TAG, "onStop: unregistered receiver");
        unregisterReceiver(firstReceiver);
    }
}
