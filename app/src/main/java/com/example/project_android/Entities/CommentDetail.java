package com.example.project_android.Entities;

public class CommentDetail {
    private String comment_id, comment, rating, game_Title;

    public CommentDetail(String comment_id, String comment, String rating, String gameTitle) {
        this.comment_id = comment_id;
        this.comment = comment;
        this.rating = rating;
        this.game_Title = gameTitle;
    }
    public CommentDetail() {

    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getGame_Title() {
        return game_Title;
    }

    public void setGame_Title(String game_Title) {
        this.game_Title = game_Title;
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

    public String getGameTitle() {
        return game_Title;
    }

    public void setGameTitle(String gameTitle) {
        this.game_Title = gameTitle;
    }

    @Override
    public String toString() {
        return "CommentDetail{" +
                "comment_id='" + comment_id + '\'' +
                ", comment='" + comment + '\'' +
                ", rating='" + rating + '\'' +
                ", game_Title='" + game_Title + '\'' +
                '}';
    }
}
