package com.example.project_android.Service;

import com.example.project_android.Entities.ArticleDetail;
import com.example.project_android.Entities.CommentDetail;
import com.example.project_android.Entities.GameDetail;
import com.example.project_android.Entities.User;
import com.example.project_android.Response.CommentResponse;
import com.example.project_android.Response.LoginResponse;
import com.example.project_android.Response.RegisterResponse;
import com.example.project_android.Response.UpdatePasswordResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );
    @FormUrlEncoded
    @POST("register")
    Call<RegisterResponse> register(
            @Field("name") String name,
            @Field("password") String password,
            @Field("email") String email
    );
    @PUT("users/{user_id}")
    Call<User> updateUser(
                    @Path("user_id") String user_id,
                    @Body User updatedUser
    );
    @PUT("comments/{comment_id}")
    Call<CommentDetail> updateComment(
            @Path("comment_id") String comment_id,
            @Body CommentDetail updatedComment
    );
    @FormUrlEncoded
    @POST("user/update-password")
    Call<UpdatePasswordResponse> updatePassword(
            @Field("current_password") String currentPassword,
            @Field("new_password") String newPassword,
            @Field("user_id") String user_id
    );
    @GET("users/{userId}/comments")
    Call<List<CommentDetail>> getCommentsByUser(
            @Path("userId") String userId
    );
    @FormUrlEncoded
    @POST("comments")
    Call<CommentResponse> comment(
            @Field("user_id") String user_id,
            @Field("game_id") String game_id,
            @Field("comment") String comment,
            @Field("rating") String rating

    );
    @GET("search-game")
    Call<GameDetail> getGame(
            @Path("game_id") String game_id
    );
    @GET("games/random")
    Call<List<GameDetail>> getRandomGames(

    );
    @GET("games/top-rated")
    Call<List<GameDetail>> getTopRatedGames(

    );
    @GET("games/genre/{genre}")
    Call<List<GameDetail>> getGamesByGenre(
            @Path("genre") String genre
    );
    @GET("search-game")
    Call<List<GameDetail>> searchGames(
            @Query("name") String name
    );
    @GET("reviews/random")
    Call<List<ArticleDetail>> getRandomReviews(

    );
    @GET("reviews")
    Call<List<ArticleDetail>> getReviews(
            @Query("page") int page,
            @Query("limit") int limit
    );

    @GET("reviews/{review}")
    Call<ArticleDetail> getReviewDetails(
            @Path("review") int reviewId
    );

}
