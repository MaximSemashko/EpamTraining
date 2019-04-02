package com.example.epamtraining.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.epamtraining.R;
import com.example.epamtraining.adapters.TrainingsAdapter;
import com.example.epamtraining.models.Exercises;

import java.util.Arrays;
import java.util.Collection;

public class TrainingsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TrainingsAdapter trainingsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainings);

        recyclerView = findViewById(R.id.exercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        trainingsAdapter = new TrainingsAdapter();
        recyclerView.setAdapter(trainingsAdapter);
        loadData();
    }

    private void loadData() {
        Collection<Exercises> exercises = getExercises();
        trainingsAdapter.setItems(exercises);


    }

    private Collection<Exercises> getExercises() {
        return Arrays.asList(
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),new Exercises("some",2),
                new Exercises("some",2),new Exercises("some",2),new Exercises("some",2),
                new Exercises("some",2),new Exercises("some",2),new Exercises("some",2),new Exercises("some",2),new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),new Exercises("some",2),
                new Exercises("some",2),new Exercises("some",2),new Exercises("some",2),
                new Exercises("some",2),new Exercises("some",2),new Exercises("some",2),new Exercises("some",2),new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),new Exercises("some",2),
                new Exercises("some",2),new Exercises("some",2),new Exercises("some",2),
                new Exercises("some",2),new Exercises("some",2),new Exercises("some",2),new Exercises("some",2),new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),new Exercises("some",2),
                new Exercises("some",2),new Exercises("some",2),new Exercises("some",2),
                new Exercises("some",2),new Exercises("some",2),new Exercises("some",2),new Exercises("some",2),new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2), new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),new Exercises("some",2),
                new Exercises("some",2),new Exercises("some",2),new Exercises("some",2),
                new Exercises("some",2),new Exercises("some",2),new Exercises("some",2),new Exercises("some",2),new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2),
                new Exercises("some",2)
        );
    }
}
