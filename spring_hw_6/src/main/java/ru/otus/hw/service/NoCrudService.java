package ru.otus.hw.service;

import java.util.List;

public interface NoCrudService<T> {
    T findById(Long id);
    List<T> findAll();
    long count();
}
