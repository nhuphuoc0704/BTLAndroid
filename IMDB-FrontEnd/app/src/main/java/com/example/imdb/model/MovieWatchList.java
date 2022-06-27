package com.example.imdb.model;

import java.io.Serializable;

public class MovieWatchList implements Serializable {
    private int id;
    private String add_time;
    private Movie movie;
    private User user;

    public MovieWatchList(){
        super();
    }

    public MovieWatchList(String add_time, Movie movie, User user) {
        this.add_time = add_time;
        this.movie = movie;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
