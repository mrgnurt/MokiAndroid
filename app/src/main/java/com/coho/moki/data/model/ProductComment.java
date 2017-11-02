package com.coho.moki.data.model;

/**
 * Created by Khanh Nguyen on 10/13/2017.
 */

public class ProductComment {

    private String name;
    private String comment;

    public ProductComment(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
