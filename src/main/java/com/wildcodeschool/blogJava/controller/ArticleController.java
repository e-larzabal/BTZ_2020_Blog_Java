package com.wildcodeschool.blogJava.controller;

import com.wildcodeschool.blogJava.dao.ArticleDao;
import com.wildcodeschool.blogJava.model.Article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class ArticleController {

    private static final String TEMPLATE_HOME = "home";
    private static final String TEMPLATE_ARTICLE = "article";
    // private static final String TEMPLATE_ARTICLE_EDIT = "article-edit";

    @Autowired
    private ArticleDao articleDao;

    @GetMapping({ "/", "/articles" })
    public String getHome(Model model) {

        model.addAttribute("articles", articleDao.findAll());

        return TEMPLATE_HOME;
    }

    @GetMapping("/article/{id}")
    public String getArticle(Model model, @PathVariable Integer id) {

        Article article = articleDao.findById(id);

        if (article == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Article inconnu");

        model.addAttribute("article", article);

        return TEMPLATE_ARTICLE;
    }

}