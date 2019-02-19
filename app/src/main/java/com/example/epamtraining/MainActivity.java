package com.example.epamtraining;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create toast for cherry pick
        Toast.makeText(this, "Toast for cherry pick", Toast.LENGTH_SHORT).show();
    }
}
