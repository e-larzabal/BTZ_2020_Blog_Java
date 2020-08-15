package com.wildcodeschool.blogJava.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Article {

    private Long id;
    private String title;
    private String content;
    private String image;
    private Date published;
    private User user = new User();
    
    private List<Tag> listTag = new ArrayList<>();

    public Article() {}

    public Article(Long id, String title, String content, String image, Date published, User user, List<Tag> listTag) { 
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.published = published;
        this.user = user;
        this.listTag = listTag;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public Date getPublished() {
        return this.published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tag> getListTag() {
        return this.listTag;
    }

    public void setListTag(List<Tag> listTag) {
        this.listTag = listTag;
    }

   
}