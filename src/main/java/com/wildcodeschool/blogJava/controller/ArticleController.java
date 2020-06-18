package com.wildcodeschool.blogJava.controller;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.wildcodeschool.blogJava.dao.ArticleDao;
import com.wildcodeschool.blogJava.dao.TagDao;
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
    @Autowired
    private TagDao tagDao;

    @GetMapping({ "/", "/articles" })
    public String getHome(Model model) {

        model.addAttribute("articles", articleDao.findAll());

        model.addAttribute("listTag", tagDao.findAll());

        return TEMPLATE_HOME;
    }

    @GetMapping("/articles/{id}")
    public String getTemplateArticle(Model model, @PathVariable Long id) {

        Article article = articleDao.findById(id);

        model.addAttribute("listTag", tagDao.findAll());

        if (article == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Article inconnu");

        model.addAttribute("article", article);
        model.addAttribute("listTag", tagDao.findAll());

        return TEMPLATE_ARTICLE;
    }

    @GetMapping("/articles/tag/{idTag}")
    public String getArticleByIdTag(Model model, @PathVariable Long idTag) {

        List<Article> articles = articleDao.FindByIdTag(idTag);

        model.addAttribute("listTag", tagDao.findAll());

        if (articles == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pas d'articles avec ce Tag");

        model.addAttribute("articles", articles);
        model.addAttribute("listTag", tagDao.findAll());

        return TEMPLATE_HOME;
    }

    @GetMapping("/sign-in")
    public String getTemplateSignIn(Model model, @ModelAttribute User user) {

        model.addAttribute("user", user);

        return TEMPLATE_SIGN_IN;
    }

    @GetMapping("/edit-article")
    public String getTemplateArticleEdit(Model model, @ModelAttribute User user, @ModelAttribute Tag tag) {

        model.addAttribute("article", new Article());
        model.addAttribute("listTag", tagDao.findAll());
        // model.addAttribute("user", user);

        model.addAttribute("tags", tagDao.findAll());

        return TEMPLATE_ARTICLE_EDIT;
    }

    @GetMapping("/edit-article/{id}")
    public String getTemplateArticleEditById(Model model, @ModelAttribute Article article, @PathVariable Long id) {

        article = articleDao.findById(id);

        model.addAttribute("article", article);
        model.addAttribute("listTag", tagDao.findAll());

        model.addAttribute("tags", tagDao.findAll());

        model.addAttribute("listTag", tagDao.findAll());

        return TEMPLATE_ARTICLE_EDIT;
    }

    @PostMapping("/articles")
    public String saveArticle(Model model, @ModelAttribute Article article, @RequestParam List<String> tagsDeLArticle) {

        if (article.getId() == null) {
            articleDao.create(article);
        } else {
            articleDao.update(article);

            // on enlève les tags de l'article
            articleDao.delAllArticleTag(article.getId());
        }

        // On parcours tous les tags cochés
        for (String tagValue : tagsDeLArticle) {
            if (!tagValue.equals("-1")) {
                // si c'est <> -1 on tague cet article
                articleDao.addArticleTag(article.getId(), Long.parseLong(tagValue));
            }
        }

        model.addAttribute("article", article);
        model.addAttribute("listTag", tagDao.findAll());

        return "redirect:" + "articles" + "/" + article.getId();
    }

    @GetMapping("/delete-article/{id}")
    public String deleteArticle(@PathVariable Long id) {

        articleDao.deleteById(id);

        return "redirect:" + "/";
    }

}
