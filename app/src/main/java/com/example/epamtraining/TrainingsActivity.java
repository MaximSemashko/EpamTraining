package com.example.epamtraining;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.epamtraining.models.Trainings;

public class TrainingsActivity extends AppCompatActivity {
    private static final String TAG = "TrainingsActivity";

    private Button button;
    private EditText name;
    private EditText sets;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainings);

        button = findViewById(R.id.put);
        name = findViewById(R.id.name);
        sets = findViewById(R.id.sets);

        final Trainings training = new Trainings();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameText = name.getText().toString();
                Integer setsText = Integer.parseInt(sets.getText().toString());

                training.exercises.put(nameText,setsText);
                name.setText(null);
                sets.setText(null);
                Log.i(TAG, training.exercises.toString());
                Toast.makeText(TrainingsActivity.this, training.exercises.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
