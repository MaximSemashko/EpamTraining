package com.example.epamtraining.backend;

import com.example.epamtraining.util.ICallback;

import java.util.List;
import java.util.UUID;

public interface IWebService<T> {

    void getEntities(final ICallback<List<T>> callback);

    void getEntities(final int startRange,
                     final int endRange,
                     final ICallback<List<T>> callback);

    void removeEntity(final UUID id);
}
