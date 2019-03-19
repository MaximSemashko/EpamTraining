package com.example.epamtraining.backend;

import android.os.Handler;
import android.os.Looper;

import com.example.epamtraining.entities.Student;
import com.example.epamtraining.util.ICallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class StudentsWebService implements IWebService<Student> {

    private List<Student> students = new ArrayList<>();
    private Random random = new Random();
    private Handler handler = new Handler(Looper.getMainLooper());

    {
        for (int i = 0; i < 1000; i++) {
            Student student = new Student();
            student.setHwCounter(1 + random.nextInt(5));
            student.setName(String.valueOf(i));
            students.add(student);
        }
    }

    @Override
    public void getEntities(ICallback<List<Student>> callback) {
    }

    @Override
    public void getEntities(final int startRange,
                            final int endRange,
                            final ICallback<List<Student>> callback) {
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                callback.onResult(students.subList(startRange, endRange));
            }
        }, 1000);
    }

    @Override
    public void removeEntity(UUID id) {

    }
}
