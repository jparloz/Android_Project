package com.example.project_android.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.project_android.Entities.GameDetail;

import java.util.List;

public class ListTopGamesViewModel extends AndroidViewModel {

    private MutableLiveData<List<GameDetail>> topGameList;

    public ListTopGamesViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<GameDetail>> getMyListGame() {
        if(topGameList ==null){
            topGameList = new MutableLiveData<List<GameDetail>>();
        }
        return topGameList;
    }

    public void setMyListGame(List<GameDetail> gameList) {
        this.topGameList.setValue(gameList);
    }


}
