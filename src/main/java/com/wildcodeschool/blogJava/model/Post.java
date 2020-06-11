package com.wildcodeschool.blogJava.model;

import java.util.Date;

public class Post {

    private Integer id;
    private String title;
    private String content;
    private String image;
    private Date published;
    private User user;

    public Post() {}

    public Post(Integer id, String title, String content, String image, Date published, User user) { 
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.published = published;
        this.user = user;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
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
   
}