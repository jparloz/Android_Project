package com.example.project_android.Response;

import com.google.gson.annotations.SerializedName;

public class CommentResponse {
    @SerializedName("user_id")
    private String user_id;

    @SerializedName("game_id")
    private String game_id;

    @SerializedName("comment")
    private String comment;

    @SerializedName("rating")
    private String rating;

    private String updatedAt;

    private String createdAt;

    public CommentResponse(String userId, String gameId, String comment, String rating) {
        this.user_id = userId;
        this.game_id = gameId;
        this.comment = comment;
        this.rating = rating;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}