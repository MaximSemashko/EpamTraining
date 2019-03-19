package com.example.epamtraining.entities;


public class Student {
    private Long id;
    private String name;
    private int hwCounter;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
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
