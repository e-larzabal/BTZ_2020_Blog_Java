package com.wildcodeschool.blogJava.repository;

import java.util.List;

import com.wildcodeschool.blogJava.dao.ArticleDao;
import com.wildcodeschool.blogJava.model.Article;

import org.springframework.stereotype.Repository;

@Repository
public class ArticleRepository implements ArticleDao {

    @Override
    public Article save(Article entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Article findById(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Article> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Article update(Article entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        // TODO Auto-generated method stub

    }
    
}