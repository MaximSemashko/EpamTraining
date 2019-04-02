package com.example.epamtraining.Callbacks;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.epamtraining.adapters.TrainingsAdapter;

public class SwipeToDeleteExerciseCallback extends ItemTouchHelper.SimpleCallback {

    private TrainingsAdapter trainingsAdapter;

    public SwipeToDeleteExerciseCallback(TrainingsAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);

        trainingsAdapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int position = viewHolder.getAdapterPosition();
        trainingsAdapter.deleteItem(position);
    }
}
