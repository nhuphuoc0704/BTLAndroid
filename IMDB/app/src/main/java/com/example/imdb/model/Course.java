package com.example.imdb.model;

import java.io.Serializable;

public class Course implements Serializable {
    private int id,price;
    private String title,content;

    public Course(int id, int price, String title, String content) {
        this.id = id;
        this.price = price;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
