package com.wildcodeschool.blogJava.dao;

import java.util.List;
import com.wildcodeschool.blogJava.model.Article;

public interface ArticleDao extends CrudDao<Article> {

    public List<Article> FindByIdTag(Long idTag);

}