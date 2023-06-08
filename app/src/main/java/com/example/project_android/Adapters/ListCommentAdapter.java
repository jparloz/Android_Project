package com.example.project_android.Adapters;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_android.Entities.Comment;
import com.example.project_android.Entities.CommentDetail;
import com.example.project_android.MainActivity;
import com.example.project_android.R;
import com.example.project_android.Response.DatabaseHandler;

import java.util.List;

public class ListCommentAdapter extends RecyclerView.Adapter<MyViewHolderCom> {

    private MainActivity activity;
    private List<Comment> Cdata;

    public ListCommentAdapter(MainActivity activity, List<Comment> Cdata){
        this.activity=activity;
        this.Cdata = Cdata;
    }

    @NonNull
    @Override
    public MyViewHolderCom onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_comment_item, parent, false);
        return new MyViewHolderCom(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderCom holder, int position) {
            holder.recComment.setText(Cdata.get(position).getComment());
            holder.recRating.setText(String.valueOf(Cdata.get(position).getRating()));
            holder.recGameTitle.setText(Cdata.get(position).getUser().getName());
    }

    @Override
    public int getItemCount() {
        return Cdata.size();
    }
}
class MyViewHolderCom extends RecyclerView.ViewHolder{
    TextView recGameTitle, recComment, recRating;
    CardView recCard;

    public MyViewHolderCom(@NonNull View itemView) {
        super(itemView);

        recGameTitle = itemView.findViewById(R.id.recGameTitle);
        recComment = itemView.findViewById(R.id.recText);
        recRating = itemView.findViewById(R.id.recRating);
        recCard = itemView.findViewById(R.id.recComment);
    }
}
