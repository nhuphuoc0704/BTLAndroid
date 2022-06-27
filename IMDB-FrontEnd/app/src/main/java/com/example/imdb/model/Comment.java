package com.example.imdb.model;

import java.io.Serializable;

public class Comment implements Serializable {
    private int id;
    private String title,content,created_time;
    private float star;
    private User user;
    private Movie movie;

    public Comment(){
        super();
    }

    public Comment( String title, String content, String created_time, float star) {
        this.title = title;
        this.content = content;
        this.created_time = created_time;
        this.star = star;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
