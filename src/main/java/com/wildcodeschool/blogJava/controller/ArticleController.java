package com.wildcodeschool.blogJava.controller;

import com.wildcodeschool.blogJava.dao.ArticleDao;
import com.wildcodeschool.blogJava.model.Article;

import com.wildcodeschool.blogJava.model.Tag;
import com.wildcodeschool.blogJava.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class ArticleController {

    private static final String TEMPLATE_HOME = "index";
    private static final String TEMPLATE_ARTICLE = "article";
    private static final String TEMPLATE_ARTICLE_EDIT = "article-edit";
    private static final String TEMPLATE_SIGN_IN = "sign-in";

    @Autowired
    private ArticleDao articleDao;

    @GetMapping({ "/", "/articles" })
    public String getHome(Model model) {

        model.addAttribute("articles", articleDao.findAll());

        return TEMPLATE_HOME;
    }

    @GetMapping("/articles/{id}")
    public String getTemplateArticle(Model model, @PathVariable Long id) {

        Article article = articleDao.findById(id);

        if (article == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Article inconnu");

        model.addAttribute("article", article);

        return TEMPLATE_ARTICLE;
    }

    @GetMapping("/sign-in")
    public String getTemplateSignIn(Model model, @ModelAttribute User user) {

        model.addAttribute("user", user);

        return TEMPLATE_SIGN_IN;
    }

    @GetMapping("/edit-article")
    public String getTemplateArticleEdit(Model model, @ModelAttribute User user, @ModelAttribute Tag tag) {

        model.addAttribute("article", new Article());
        model.addAttribute("user", user);
        model.addAttribute("tag", tag);

        return TEMPLATE_ARTICLE_EDIT;
    }

    @GetMapping("/edit-article/{id}")
    public String getTemplateArticleEditById(Model model, @ModelAttribute Article article, @PathVariable Long id) {

        article = articleDao.findById(id);

        model.addAttribute("article", article);

        return TEMPLATE_ARTICLE_EDIT;
    }

    @PostMapping("/articles")
    public String saveArticle(Model model, @ModelAttribute Article article) {

        if (article.getId() == null) {
            articleDao.create(article);
        } else {
            articleDao.update(article);
        }

        model.addAttribute("article", article);

        return "redirect:" + TEMPLATE_ARTICLE + "/" + article.getId();
    }

    @DeleteMapping("/articles/{id}")
    public String deleteArticle(Model model, @PathVariable Long id) {

        articleDao.deleteById(id);

        return "redirect:" + TEMPLATE_HOME;
    }

}