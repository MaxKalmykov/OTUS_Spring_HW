package ru.otus.hw.service;

public interface CrudService<T> extends NoCrudService<T>{
    void save(T entity);
    void removeById(Long id);
}
