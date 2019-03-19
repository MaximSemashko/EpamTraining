package com.example.epamtraining.entities;

import java.util.UUID;

public class Student {
    private UUID id;
    private String name;
    private int hwCounter;

    public Student() {
       id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHwCounter() {
        return hwCounter;
    }

    public void setHwCounter(int hwCounter) {
        this.hwCounter = hwCounter;
    }
}
