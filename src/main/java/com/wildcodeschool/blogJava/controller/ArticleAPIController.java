package com.wildcodeschool.blogJava.controller;

import com.wildcodeschool.blogJava.dao.ArticleDao;
import com.wildcodeschool.blogJava.model.Article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class ArticleAPIController {

    @Autowired
    private ArticleDao articleDao;

    @GetMapping("/api/articles/{id}")
    @ResponseBody
    public Article getArticle(@PathVariable Long id) {

        Article article = articleDao.findById(id);

        if (article == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Article inconnu");

        return article;
    }

}