package com.wildcodeschool.blogJava.model;

import java.util.Objects;
import java.awt.Color;

public class Tag {

    private Integer id;
    private String tagName;
    private Color color;

    public Tag() {
    }

    public Tag(Integer id, String tagName, Color color) {
        this.id = id;
        this.tagName = tagName;
        this.color = color;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTagName() {
        return this.tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Tag id(Integer id) {
        this.id = id;
        return this;
    }

    public Tag tagName(String tagName) {
        this.tagName = tagName;
        return this;
    }

    public Tag color(Color color) {
        this.color = color;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Tag)) {
            return false;
        }
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id) && Objects.equals(tagName, tag.tagName) && Objects.equals(color, tag.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tagName, color);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", tagName='" + getTagName() + "'" +
            ", color='" + getColor() + "'" +
            "}";
    }


    
}