package com.example.project_android.Entities;

public class GameDetail {
    String id, name,description, age_rating, release, background_image, meta_rating, playtime, rating;
    Developer developer = new Developer(" ");

    public GameDetail() {

    }
    public GameDetail(String game_id, String name, Developer developer, String description, String age_rating, String release, String background_image, String rating, String meta_rating, String playtime) {
        this.id =game_id;
        this.name = name;
        this.developer = developer;
        this.description = description;
        this.age_rating = age_rating;
        this.release = release;
        this.background_image = background_image;
        this.rating = rating;
        this.meta_rating = meta_rating;
        this.playtime = playtime;
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

    @Override
    public String toString() {
        return "GameDetail{" +
                "name='" + name + '\'' +
                ", developer='" + developer + '\'' +
                ", description='" + description + '\'' +
                ", age_rating='" + age_rating + '\'' +
                ", release='" + release + '\'' +
                ", background_image='" + background_image + '\'' +
                ", game_id=" + id +
                ", meta_rating=" + meta_rating +
                ", playtime=" + playtime +
                ", rating=" + rating +
                '}';
    }
}
