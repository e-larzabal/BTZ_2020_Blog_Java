package com.wildcodeschool.blogJava.dao;

import java.util.List;

import com.wildcodeschool.blogJava.model.Tag;

public interface TagDao extends CrudDao<Tag> {

    List<Tag> findAllInArticle(Long id);

}