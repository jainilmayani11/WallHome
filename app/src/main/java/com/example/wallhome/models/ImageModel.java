package com.example.wallhome.models;

import java.io.Serializable;

public class ImageModel implements Serializable {

    public UrlModel src;
    public String photographer;



    public String getPhotographer() {
        return photographer;
    }


    public UrlModel getSrc() {
        return src;
    }



}
