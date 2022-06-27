package com.example.imdb.model;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {
    private int id;
    private String title;
    private List<Movie> movies;

    public Category() {
        super();
    }

    public Category(int id, String title) {
        this.id = id;
        this.title = title;
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

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
