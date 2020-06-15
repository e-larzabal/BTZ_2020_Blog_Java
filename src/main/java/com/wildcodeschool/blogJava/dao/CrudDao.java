package com.wildcodeschool.blogJava.dao;

import java.util.List;

public interface CrudDao<T> {

    T create();

    T findById(Long id);

    List<T> findAll();

    T update();

    void deleteById(Long id);
}