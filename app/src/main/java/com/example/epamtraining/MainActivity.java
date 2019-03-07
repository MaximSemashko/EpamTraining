package com.example.epamtraining;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.epamtraining.FirstService.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String BROADCAST_ACTION = "com.example.epamtraining.CUSTOM_ACTION";

    private Button startServiceButton;
    private Button stopServiceButton;
    private TextView messageTextView;

    private String messageFromService;

    private FirstReceiver firstReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startServiceButton = findViewById(R.id.start_button);
        stopServiceButton = findViewById(R.id.stop_button);
        messageTextView = findViewById(R.id.message_text_view);

        firstReceiver = new FirstReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                super.onReceive(context, intent);

                Bundle bundle = intent.getExtras();
                if(bundle!=null) {
                    messageFromService = bundle.getString(EXTRA_MESSAGE);
                    messageTextView.setText(messageFromService);
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(firstReceiver, intentFilter);

        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, getString(R.string.service_start));
                Intent intent = new Intent(MainActivity.this,FirstService.class);
                startService(intent);
            }
        });

        stopServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, getString(R.string.service_stop));
                Intent intent = new Intent(MainActivity.this,FirstService.class);
                stopService(intent);
            }
        });

       getSupportFragmentManager().beginTransaction()
               .add(R.id.fragment_container, new FirstFragment())
               .commit();
    }
}
