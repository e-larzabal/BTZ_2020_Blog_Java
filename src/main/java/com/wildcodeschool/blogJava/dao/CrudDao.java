package com.wildcodeschool.blogJava.dao;

import java.util.List;

public interface CrudDao<T> {

    T create(T entity);

    T findById(Long id);

    List<T> findAll();

    T update(T entity);

    void deleteById(Long id);
}
