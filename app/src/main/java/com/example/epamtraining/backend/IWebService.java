package com.example.epamtraining.backend;

import com.example.epamtraining.entities.Student;
import com.example.epamtraining.util.ICallback;

import java.util.List;

public interface IWebService<T> {

    void getEntities(final ICallback<List<T>> callback);

    void getEntities(final int startRange,
                     final int endRange,
                     final ICallback<List<T>> callback);

    void removeEntity(final Long id);

    void addEntity(final Student student);
}
