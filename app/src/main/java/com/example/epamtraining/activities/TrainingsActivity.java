package com.example.epamtraining.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.epamtraining.Callbacks.SwipeToDeleteExerciseCallback;
import com.example.epamtraining.R;
import com.example.epamtraining.adapters.TrainingsAdapter;
import com.example.epamtraining.models.Exercises;

public class TrainingsActivity extends AppCompatActivity {

    private ImageButton addExerciseButton;
    private EditText exerciseNameText;
    private EditText exerciseSetsText;

    private RecyclerView recyclerView;
    private TrainingsAdapter trainingsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainings);

        addExerciseButton = findViewById(R.id.add_exercise_button);
        exerciseNameText = findViewById(R.id.exercise_name_text);
        exerciseSetsText = findViewById(R.id.exercise_sets_text);

        recyclerView = findViewById(R.id.exercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        trainingsAdapter = new TrainingsAdapter();
        recyclerView.setAdapter(trainingsAdapter);
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteExerciseCallback(trainingsAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = exerciseNameText.getText().toString();
                int sets = Integer.parseInt(exerciseSetsText.getText().toString());
                trainingsAdapter.setItem(new Exercises(name,sets));
                exerciseNameText.setText(null);
                exerciseSetsText.setText(null);
            }
        });
    }
}
