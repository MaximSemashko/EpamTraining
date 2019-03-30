package com.example.epamtraining.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class TrainingsAdapter extends RecyclerView.Adapter<TrainingsAdapter.TrainingsViewHolder> {

    @NonNull
    @Override
    public TrainingsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingsViewHolder trainingsViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class TrainingsViewHolder extends RecyclerView.ViewHolder{

        public TrainingsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

