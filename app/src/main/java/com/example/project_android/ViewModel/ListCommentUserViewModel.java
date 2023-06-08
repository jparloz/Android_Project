package com.example.project_android.ViewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.project_android.Entities.CommentDetail;

import java.util.List;


public class ListCommentUserViewModel extends AndroidViewModel {

    private MutableLiveData<List<CommentDetail>> commentList = new MutableLiveData<>();

    public ListCommentUserViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<CommentDetail>> getMyListComment() {
        if(commentList==null){
            commentList = new MutableLiveData<List<CommentDetail>>();
        }
        return  commentList;
    }

    public void setMyListComment(List<CommentDetail> myListComment) {
        this.commentList.setValue(myListComment);
    }

    public void updateComment(int pos, CommentDetail updatedComment){
        List<CommentDetail> myCommentList = commentList.getValue();

        myCommentList.set(pos, updatedComment);

        commentList.setValue(myCommentList);
    }
}
