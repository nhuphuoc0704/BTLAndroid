package com.example.imdb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private int id;
    private String username,email,password,fullname,avatar;
    private List<Movie> listUserWatchList;

    public User(){
        super();
    }
    public User(String username, String email, String password, String fullname, String avatar) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.avatar = avatar;
        listUserWatchList= new ArrayList<>();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public List<Movie> getUserWatchList() {
        return listUserWatchList;
    }

    public void setUserWatchList(List<Movie> userWatchList) {
        this.listUserWatchList = userWatchList;
    }
}
