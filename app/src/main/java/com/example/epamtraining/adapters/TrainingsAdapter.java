package com.example.epamtraining.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.epamtraining.R;
import com.example.epamtraining.models.Exercises;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TrainingsAdapter extends RecyclerView.Adapter<TrainingsAdapter.TrainingsViewHolder> {

    private final List<Exercises> exercisesList = new ArrayList<>();

    @NonNull
    @Override
    public TrainingsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.exercise_item, viewGroup, false);
        return new TrainingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingsViewHolder viewHolder, int i) {
        viewHolder.bind(exercisesList.get(i));
    }

    @Override
    public int getItemCount() {
        return exercisesList.size();
    }

    public void setItems(Collection<Exercises> exercises) {
        exercisesList.addAll(exercises);
        notifyDataSetChanged();
    }

    public void setItem(Exercises exercise) {
        exercisesList.add(exercise);
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        exercisesList.remove(position);
        notifyItemRemoved(position);
    }

    class TrainingsViewHolder extends RecyclerView.ViewHolder {
        private TextView exerciseNameTextView;
        private TextView exerciseSetsTextView;

        public TrainingsViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseNameTextView = itemView.findViewById(R.id.name);
            exerciseSetsTextView = itemView.findViewById(R.id.sets);
        }

        public void bind(Exercises exercises) {
            exerciseNameTextView.setText(exercises.getName());
            exerciseSetsTextView.setText(String.valueOf(exercises.getSets()));
        }
    }
}
