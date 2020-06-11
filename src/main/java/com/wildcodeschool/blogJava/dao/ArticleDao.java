package com.wildcodeschool.blogJava.dao;

import java.util.List;
import com.wildcodeschool.blogJava.model.Article;

public interface ArticleDao {
    
    Article save(Article entity);

    Article findById(Long id);

    List<Article> findAll();

    Article update(Article entity);

    void deleteById(Long id);

}