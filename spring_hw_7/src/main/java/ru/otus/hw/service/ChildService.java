package ru.otus.hw.service;

import java.util.List;

public interface ChildService<T> extends CrudService<T>{
    List<T> findAllByParent(Long parentId);
    void removeAllByParent(Long bookId);
}
