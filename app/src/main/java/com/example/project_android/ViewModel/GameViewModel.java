package com.example.project_android.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.project_android.Entities.GameDetail;

public class GameViewModel extends AndroidViewModel {

    private GameDetail mgam;

    public GameViewModel(@NonNull Application application) {
        super(application);
    }

    public GameDetail getMyGame() {
        if(mgam==null)

            mgam=new GameDetail();

        return mgam;
    }

    public void setMyGame(GameDetail mgam) {
        this.mgam = mgam;
    }
}
