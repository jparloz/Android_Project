package com.example.project_android.Entities;

import com.example.project_android.Entities.User;
import com.google.gson.annotations.SerializedName;

public class Comment{
    @SerializedName("game_id")
    private int gameId;

    @SerializedName("comment")
    private String comment;

    @SerializedName("rating")
    private int rating;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("user")
    private User user;

    // Getter methods

    public int getGameId() {
        return gameId;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public User getUser() {
        return user;
    }
}

