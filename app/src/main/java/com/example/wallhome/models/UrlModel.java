package com.example.wallhome.models;

import java.io.Serializable;

public class UrlModel implements Serializable {

    private String portrait;
    public String original;
    public String large;

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait() {
        this.portrait = portrait;
    }


}
