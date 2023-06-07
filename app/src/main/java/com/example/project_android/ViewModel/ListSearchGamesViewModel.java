package com.example.project_android.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.project_android.Entities.GameDetail;

import java.util.List;

public class ListSearchGamesViewModel extends AndroidViewModel {

    private MutableLiveData<List<GameDetail>> searchGameList;

    public ListSearchGamesViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<GameDetail>> getMyListGame() {
        if(searchGameList ==null){
            searchGameList = new MutableLiveData<List<GameDetail>>();
        }
        return searchGameList;
    }

    public void setMyListGame(List<GameDetail> gameList) {
        this.searchGameList.setValue(gameList);
    }


}
