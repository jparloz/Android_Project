package com.example.project_android.Entities;

import java.util.List;

public class GenreDetail {
    String name;
    int image;
    List<GameDetail> games;

    public GenreDetail(String name, int image, List<GameDetail> games) {
        this.name = name;
        this.image = image;
        this.games=games;
    }
    public GenreDetail(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
