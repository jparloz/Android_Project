package com.example.project_android.Adapters;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_android.Entities.CommentDetail;
import com.example.project_android.MainActivity;
import com.example.project_android.R;
import com.example.project_android.Response.DatabaseHandler;

import java.util.List;

public class Comment_Adapter extends RecyclerView.Adapter<MyViewHolderComment> {

    private MainActivity activity;
    private List<CommentDetail> Cdata;

    public Comment_Adapter(MainActivity activity, List<CommentDetail> Cdata){
        this.activity=activity;
        this.Cdata = Cdata;
    }

    @NonNull
    @Override
    public MyViewHolderComment onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_comment_item, parent, false);
        return new MyViewHolderComment(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderComment holder, int position) {
        holder.recComment.setText(Cdata.get(position).getComment());
        holder.recRating.setText(Cdata.get(position).getRating());
        holder.recGameTitle.setText(Cdata.get(position).getGameTitle());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_comment, null);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                if (dialog.getWindow() != null){
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                TextView edit_title = dialogView.findViewById(R.id.commentTitle);
                TextView edit_comment = dialogView.findViewById(R.id.commentBox);
                TextView edit_rat = dialogView.findViewById(R.id.ratingBox);
                Button btn = dialogView.findViewById(R.id.btnSubmit);

                edit_title.setText("Edit Comment");
                edit_comment.setHint(Cdata.get(holder.getLayoutPosition()).getComment());
                edit_rat.setHint(Cdata.get(holder.getLayoutPosition()).getRating());

                dialog.show();
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String new_comment = edit_comment.getText().toString();
                        String new_rat = edit_rat.getText().toString();

                        DatabaseHandler dH = new DatabaseHandler(activity);
                        dH.updateCommentHandler(Cdata.get(holder.getLayoutPosition()),new_comment,new_rat,holder.getLayoutPosition());
                        dialog.hide();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return Cdata.size();
    }
}
class MyViewHolderComment extends RecyclerView.ViewHolder{
    TextView recGameTitle, recComment, recRating;
    CardView recCard;

    public MyViewHolderComment(@NonNull View itemView) {
        super(itemView);

        recGameTitle = itemView.findViewById(R.id.recGameTitle);
        recComment = itemView.findViewById(R.id.recText);
        recRating = itemView.findViewById(R.id.recRating);
        recCard = itemView.findViewById(R.id.recComment);
    }
}
