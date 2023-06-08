package com.example.project_android.Response;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.project_android.Entities.Comment;
import com.example.project_android.Entities.CommentDetail;
import com.example.project_android.Entities.GameDetail;
import com.example.project_android.Entities.User;
import com.example.project_android.MainActivity;
import com.example.project_android.R;
import com.example.project_android.Service.ApiService;
import com.example.project_android.UI.CommentsFragment;
import com.example.project_android.UI.GameDetailFragment;
import com.example.project_android.UI.SearchFragment;
import com.example.project_android.ViewModel.GameViewModel;
import com.example.project_android.ViewModel.ListCommentUserViewModel;
import com.example.project_android.ViewModel.ListCommentViewModel;
import com.example.project_android.ViewModel.ListGameViewModel;
import com.example.project_android.ViewModel.ListGamesGenreViewModel;
import com.example.project_android.ViewModel.ListSearchGamesViewModel;
import com.example.project_android.ViewModel.ListTopGamesViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DatabaseHandler {

    MainActivity activity;
    GameViewModel myGameDetail;
    ListGamesGenreViewModel myListGenreGame;
    ListTopGamesViewModel myListTopGame;
    ListCommentUserViewModel myListComment;
    ListGameViewModel myListGame;
    ListSearchGamesViewModel mySearchGame;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ApiService apiService;
    String baseUrl ="http://10.0.2.2:8089/api/";//"https://game-rate-production.up.railway.app/api/";
    Retrofit retrofit;


    public DatabaseHandler(MainActivity activity) {
        this.activity = activity;

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        this.apiService=retrofit.create(ApiService.class);
    }
    public void registerHandler(String name, String email, String password){
        Call<RegisterResponse> call = apiService.register(name,password,email);
        sharedPreferences = activity.getSharedPreferences("MODE", Context.MODE_PRIVATE);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                if (response.isSuccessful()) {
                  RegisterResponse rr = response.body();
                  User user = new User(rr.getId(), rr.getName(),rr.getEmail() ,rr.getPassword());

                  editor = sharedPreferences.edit();
                  editor.putString("name",user.getName());
                  Toast.makeText(activity, "Successfully registered", Toast.LENGTH_SHORT).show();

                } else {
                    Log.d("ERROR", String.valueOf(response.code()));
                    Log.d("ERROR", "FALLA AL LLEGAR, LA LLAMADA SE REALIZA");
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                t.printStackTrace();
                Log.d("ERROR", "FALLA AL LLAMAR");
            }

        });
    }
    public void loginHandler(String email, String password){
        Call<LoginResponse> call = apiService.login(email,password);
        sharedPreferences = activity.getSharedPreferences("MODE", Context.MODE_PRIVATE);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful()) {
                    LoginResponse lr = response.body();
                    User user = new User(lr.getId(), lr.getName(), lr.getEmail(), password);

                    editor = sharedPreferences.edit();
                    editor.putString("name", user.getName());
                    editor.putString("password", password);
                    editor.putString("email", user.getEmail());
                    editor.putString("user_id", user.getUser_id());

                    editor.apply();

                } else {
                    Log.d("ERROR", response.message());
                    Log.d("ERROR", "FALLA AL LLEGAR, LA LLAMADA SE REALIZA");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                Log.d("ERROR", "FALLA AL LLAMAR");
            }

        });
    }
    public void updatePasswordHandler(String current_password, String new_password){
        sharedPreferences = activity.getSharedPreferences("MODE", Context.MODE_PRIVATE);

        Call<UpdatePasswordResponse> call = apiService.updatePassword(current_password,new_password,sharedPreferences.getString("user_id"," "));
        call.enqueue(new Callback<UpdatePasswordResponse>() {
            @Override
            public void onResponse(Call<UpdatePasswordResponse> call, Response<UpdatePasswordResponse> response) {

                if (response.isSuccessful()) {
                    UpdatePasswordResponse ur = response.body();
                    if(ur.getMessage().equals("Contraseña actualizada correctamente")){
                        editor = sharedPreferences.edit();
                        editor.putString("password", new_password);
                        editor.apply();
                        Toast.makeText(activity, "Password updated successfully", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("ERROR", response.message());
                    Log.d("ERROR", "FALLA AL LLEGAR, LA LLAMADA SE REALIZA");
                }
            }
            @Override
            public void onFailure(Call<UpdatePasswordResponse> call, Throwable t) {
                t.printStackTrace();
                Log.d("ERROR", "FALLA AL LLAMAR");
            }

        });
    }
    public void updateUserHandler(String name, String email){
        sharedPreferences = activity.getSharedPreferences("MODE", Context.MODE_PRIVATE);
        User user = new User(sharedPreferences.getString("user_id"," "),name,email,sharedPreferences.getString("password"," "));
        Call<User> call = apiService.updateUser(sharedPreferences.getString("user_id"," "),user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {
                    User newUser = response.body();
                    editor = sharedPreferences.edit();

                    editor.putString("name", newUser.getName());
                    editor.putString("email", newUser.getEmail());

                } else {
                    Log.d("ERROR", response.message());
                    Log.d("ERROR", "FALLA AL LLEGAR, LA LLAMADA SE REALIZA");
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
                Log.d("ERROR", "FALLA AL LLAMAR");
            }

        });
    }

    public void getCommentsByUserHandler() {
        sharedPreferences = activity.getSharedPreferences("MODE", Context.MODE_PRIVATE);
        myListComment = new ViewModelProvider(activity).get(ListCommentUserViewModel.class);
        Call<List<CommentDetail>> call = apiService.getCommentsByUser(sharedPreferences.getString("user_id", " "));
        call.enqueue(new Callback<List<CommentDetail>>() {
            @Override
            public void onResponse(Call<List<CommentDetail>> call, Response<List<CommentDetail>> response) {
                if (response.isSuccessful()) {

                    myListComment.setMyListComment(response.body());

                } else {
                    Log.d("ERROR", response.message());
                    Log.d("ERROR", "FALLA AL LLEGAR, LA LLAMADA SE REALIZA");
                }
            }

            @Override
            public void onFailure(Call<List<CommentDetail>> call, Throwable t) {
                t.printStackTrace();
                Log.d("ERROR", "FALLA AL LLAMAR");
            }
        });
    }
    public void updateCommentHandler(CommentDetail acComment, String comment, String rating, int position){
        sharedPreferences = activity.getSharedPreferences("MODE", Context.MODE_PRIVATE);

        acComment.setComment(comment);
        acComment.setRating(rating);

        myListComment = new ViewModelProvider(activity).get(ListCommentUserViewModel.class);

        Call<CommentDetail> call = apiService.updateComment(acComment.getComment_id(),acComment);
        call.enqueue(new Callback<CommentDetail>() {
            @Override
            public void onResponse(Call<CommentDetail> call, Response<CommentDetail> response) {

                if (response.isSuccessful()) {

                    CommentDetail updatedComment =  response.body();
                    updatedComment.setGame_Title(acComment.getGame_Title());
                    myListComment.updateComment(position, updatedComment);

                } else {
                    Log.d("ERROR", response.message());
                    Log.d("ERROR", "FALLA AL LLEGAR, LA LLAMADA SE REALIZA");
                }
            }
            @Override
            public void onFailure(Call<CommentDetail> call, Throwable t) {
                t.printStackTrace();
                Log.d("ERROR", "FALLA AL LLAMAR");
            }

        });
    }
    public void getGameRandomHandle() {
        myListGame = new ViewModelProvider(activity).get(ListGameViewModel.class);
        Call<List<GameDetail>> call = apiService.getRandomGames();
        call.enqueue(new Callback<List<GameDetail>>() {
            @Override
            public void onResponse(Call<List<GameDetail>> call, Response<List<GameDetail>> response) {
                if (response.isSuccessful()) {
                    myListGame.setMyListGame(response.body());
                } else {
                    Log.d("ERROR", response.message());
                    Log.d("ERROR", "FALLA AL LLEGAR, LA LLAMADA SE REALIZA");
                }
            }

            @Override
            public void onFailure(Call<List<GameDetail>> call, Throwable t) {
                t.printStackTrace();
                Log.d("ERROR", "FALLA AL LLAMAR");
            }
        });
    }
    public void getGameTopRatedHandle() {
        myListTopGame = new ViewModelProvider(activity).get(ListTopGamesViewModel.class);
        Call<List<GameDetail>> call = apiService.getTopRatedGames();
        call.enqueue(new Callback<List<GameDetail>>() {
            @Override
            public void onResponse(Call<List<GameDetail>> call, Response<List<GameDetail>> response) {
                if (response.isSuccessful()) {
                    myListTopGame.setMyListGame(response.body());
                } else {
                    Log.d("ERROR", response.message());
                    Log.d("ERROR", "FALLA AL LLEGAR, LA LLAMADA SE REALIZA");
                }
            }

            @Override
            public void onFailure(Call<List<GameDetail>> call, Throwable t) {
                t.printStackTrace();
                Log.d("ERROR", "FALLA AL LLAMAR");
            }
        });
    }
    public void getGameDetailHandle(GameDetail game) {
        myGameDetail = new ViewModelProvider(activity).get(GameViewModel.class);
        Log.d("AAAAAAAAA",game.toString());
        Call<GameDetail> call = apiService.getGame(game.getId());
        call.enqueue(new Callback<GameDetail>() {
            @Override
            public void onResponse(Call<GameDetail> call, Response<GameDetail> response) {
                if (response.isSuccessful()) {
                    myGameDetail.setMyGame(response.body());
                    loadFragment(new GameDetailFragment());
                } else {
                    Log.d("ERROR", response.message());
                    Log.d("ERROR", "FALLA AL LLEGAR, LA LLAMADA SE REALIZA");
                }
            }

            @Override
            public void onFailure(Call<GameDetail> call, Throwable t) {
                t.printStackTrace();
                Log.d("ERROR", "FALLA AL LLAMAR");
            }
        });
    }
    public void getGameByGenreHandle(String genre_id) {
        myListGenreGame = new ViewModelProvider(activity).get(ListGamesGenreViewModel.class);
        Call<List<GameDetail>> call = apiService.getGamesByGenre(genre_id);
        call.enqueue(new Callback<List<GameDetail>>() {
            @Override
            public void onResponse(Call<List<GameDetail>> call, Response<List<GameDetail>> response) {
                if (response.isSuccessful()) {
                    myListGenreGame.setMyListGenreGame(response.body());
                } else {
                    Log.d("ERROR", response.message());
                    Log.d("ERROR", "FALLA AL LLEGAR, LA LLAMADA SE REALIZA");
                }
            }

            @Override
            public void onFailure(Call<List<GameDetail>> call, Throwable t) {
                t.printStackTrace();
                Log.d("ERROR", "FALLA AL LLAMAR");
            }
        });
    }

    public void commentHandler(String game_id, String comment, String rating){

        sharedPreferences = activity.getSharedPreferences("MODE", Context.MODE_PRIVATE);
        Call<CommentResponse> call = apiService.comment(sharedPreferences.getString("user_id"," "),game_id,comment,rating);
        call.enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(activity, "Comment completed successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("ERROR", response.message());
                    Log.d("ERROR", "FALLA AL LLEGAR, LA LLAMADA SE REALIZA");
                }
            }
            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                Toast.makeText(activity, "You already have a comment on this game", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                Log.d("ERROR", "FALLA AL LLAMAR");
            }
        });

    }
    public void getSearchGameHandler(String name) {

        mySearchGame = new ViewModelProvider(activity).get(ListSearchGamesViewModel.class);
        Call<List<GameDetail>> call = apiService.getSearchGame(name);

        call.enqueue(new Callback<List<GameDetail>>() {
            @Override
            public void onResponse(Call<List<GameDetail>> call, Response<List<GameDetail>> response) {

                if (response.isSuccessful()) {
                    mySearchGame.getMyListGame();
                    mySearchGame.setMyListGame(response.body());
                    loadFragment(new SearchFragment());

                } else {
                    Log.d("ERROR", response.message());
                    Log.d("ERROR", "FALLA AL LLEGAR, LA LLAMADA SE REALIZA");
                }
            }

            @Override
            public void onFailure(Call<List<GameDetail>> call, Throwable t) {
                Toast.makeText(activity, "You already have a comment on this game", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                Log.d("ERROR", "FALLA AL LLAMAR");
            }
        });
    }
    public void getCommentsWithUserHandle(String game_id) {
        ListCommentViewModel myListComment = new ViewModelProvider(activity).get(ListCommentViewModel.class);
        Call<List<Comment>> call = apiService.getCommentsWithUser(game_id);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>>response) {
                if (response.isSuccessful()) {
                    myListComment.setMyListComment(response.body());
                    loadFragment(new CommentsFragment(game_id));
                } else {
                    Log.d("ERROR", response.message());
                    Log.d("ERROR", "FALLA AL LLEGAR, LA LLAMADA SE REALIZA");
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                t.printStackTrace();
                Log.d("ERROR", "FALLA AL " +
                        "LLAMAR");
            }
        });
    }



    private void loadFragment(Fragment fragmento){
        activity
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout,fragmento)
                .addToBackStack(null)
                .commit();
    }
}