package com.example.mypets.database;

import java.util.List;

public interface DatabaseInterface<T> {

    boolean create(T category);

    T getOneById(int id) throws IndexOutOfBoundsException;

    List<T> getAll();

    boolean update(T category);
}
