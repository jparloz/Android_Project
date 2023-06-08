package com.example.project_android.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.project_android.Entities.Comment;
import com.example.project_android.Entities.CommentDetail;

import java.util.List;

public class ListCommentViewModel extends AndroidViewModel {

    private MutableLiveData<List<Comment>> commentList = new MutableLiveData<>();

    public ListCommentViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Comment>> getMyListComment() {
        if(commentList==null){
            commentList = new MutableLiveData<List<Comment>>();
        }
        return  commentList;
    }

    public void setMyListComment(List<Comment> myListComment) {
        this.commentList.setValue(myListComment);
    }


}
