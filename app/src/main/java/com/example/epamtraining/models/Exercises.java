package com.example.epamtraining.models;

import android.support.annotation.NonNull;

public class Exercises {
    private String name;
    private int sets;
    private float callories;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public float getCallories() {
        return callories;
    }

    public void countCallories() {
        if (name.equals("pushups")){
            callories+=1;
            callories*=sets;
        }
    }
    @NonNull
    @Override
    public String toString() {
        return name+","+sets+","+callories;
    }

    public Exercises(String name, int sets) {
        this.name = name;
        this.sets = sets;
        countCallories();
    }
}
