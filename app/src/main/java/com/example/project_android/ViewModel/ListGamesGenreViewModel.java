package com.example.project_android.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.project_android.Entities.GameDetail;

import java.util.List;

public class ListGamesGenreViewModel extends AndroidViewModel {

    private MutableLiveData<List<GameDetail>> GenreGameList;

    public ListGamesGenreViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<GameDetail>> getMyListGenreGame() {
        if(GenreGameList ==null){
            GenreGameList = new MutableLiveData<List<GameDetail>>();
        }
        return GenreGameList;
    }


    public void setMyListGenreGame(List<GameDetail> gameList) {
        this.GenreGameList.setValue(gameList);
    }


}
