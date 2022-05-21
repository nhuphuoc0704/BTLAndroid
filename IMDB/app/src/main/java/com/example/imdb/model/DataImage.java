package com.example.imdb.model;

import java.io.Serializable;

public class DataImage implements Serializable {
    private String avt;

    public DataImage(String avt) {
        this.avt = avt;
    }

    public DataImage() {
        super();
    }

    public String getAvt() {
        return avt;
    }

    public void setAvt(String avt) {
        this.avt = avt;
    }
}
