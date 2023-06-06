package com.example.project_android.Entities;

import java.util.ArrayList;
import java.util.List;

public class GameDetail {
    String id, name,description, age_rating, release, background_image, meta_rating, playtime, rating;
    Developer developer = new Developer(" ");
    List<Genres> genres = new ArrayList<>();
    List<Platforms> platforms = new ArrayList<>();

    public GameDetail() {

    }

    public GameDetail(String id, String name, String description, String age_rating, String release, String background_image, String meta_rating, String playtime, String rating, Developer developer, List<Genres> genres, List<Platforms> platforms) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.age_rating = age_rating;
        this.release = release;
        this.background_image = background_image;
        this.meta_rating = meta_rating;
        this.playtime = playtime;
        this.rating = rating;
        this.developer = developer;
        this.genres = genres;
        this.platforms = platforms;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public String getDescription() {
        return description;
    }

    public String getAge_rating() {
        return age_rating;
    }

    public String getRelease() {
        return release;
    }

    public String getBackground_image() {
        return background_image;
    }

    public String getRating() {
        return rating;
    }

    public String getMeta_rating() {
        return meta_rating;
    }

    public String getPlaytime() {
        return playtime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAge_rating(String age_rating) {
        this.age_rating = age_rating;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public void setBackground_image(String background_image) {
        this.background_image = background_image;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setMeta_rating(String meta_rating) {
        this.meta_rating = meta_rating;
    }

    public void setPlaytime(String playtime) {
        this.playtime = playtime;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }

    public List<Platforms> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Platforms> platforms) {
        this.platforms = platforms;
    }

    @Override
    public String toString() {
        return "GameDetail{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", age_rating='" + age_rating + '\'' +
                ", release='" + release + '\'' +
                ", background_image='" + background_image + '\'' +
                ", meta_rating='" + meta_rating + '\'' +
                ", playtime='" + playtime + '\'' +
                ", rating='" + rating + '\'' +
                ", developer=" + developer +
                ", genres=" + genres +
                ", platforms=" + platforms +
                '}';
    }
}
