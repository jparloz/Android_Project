package com.example.project_android.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.project_android.Entities.ArticleDetail;

public class ArticleViewModel extends AndroidViewModel {

    private ArticleDetail myart;

    public ArticleViewModel(@NonNull Application application) {
        super(application);
    }

    public ArticleDetail getMyArticle() {
        if(myart==null)

            myart=new ArticleDetail();

        return myart;
    }

    public void setMyArticle(ArticleDetail myart) {
        this.myart = myart;
    }
}
