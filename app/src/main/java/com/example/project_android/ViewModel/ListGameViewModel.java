package com.example.project_android.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.project_android.Entities.GameDetail;

import java.util.ArrayList;
import java.util.List;

public class ListGameViewModel extends AndroidViewModel {

    private MutableLiveData<List<GameDetail>> gameList;

    public ListGameViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<GameDetail>> getMyListGame() {
        if(gameList ==null){
            gameList = new MutableLiveData<List<GameDetail>>();
        }
        return gameList;
    }

    public void setMyListGame(List<GameDetail> gameList) {
        if(this.gameList != null && this.gameList.getValue() != null){
            this.gameList = new MutableLiveData<List<GameDetail>>();
        }
        this.gameList.setValue(gameList);
    }

}
