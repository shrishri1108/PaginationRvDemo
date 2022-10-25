package com.example.paginationrecyclerview.Model;

public class DataResponseModel {

    private String name,image;

    public DataResponseModel(String name, String image) {
        this.image = image;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
