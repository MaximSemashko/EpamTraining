package com.example.epamtraining.entities;


public class Student {
    private static int count = 0;
    private Long id;
    private String name;
    private int hwCounter;

    public Student() {
        this.id = (long) ++count;
    }

    public Student(String name, int hwCounter) {
        this.id = (long) ++count;
        this.name = name;
        this.hwCounter = hwCounter;
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
