package com.example.project_android.UI;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.project_android.Adapters.Comment_Adapter;
import com.example.project_android.Adapters.ListCommentAdapter;
import com.example.project_android.Entities.Comment;
import com.example.project_android.Entities.CommentDetail;
import com.example.project_android.MainActivity;
import com.example.project_android.R;
import com.example.project_android.Response.DatabaseHandler;
import com.example.project_android.ViewModel.ListCommentUserViewModel;
import com.example.project_android.ViewModel.ListCommentViewModel;

import java.util.List;

public class CommentsFragment extends Fragment {

        RecyclerView rv;
        ListCommentViewModel Cdata;
        ListCommentAdapter Cadapter;
        String game_id;
        DatabaseHandler dH;

        public CommentsFragment() {

        }
        public CommentsFragment(String game_id) {
            this.game_id = game_id;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_comments, container, false);
        }

        @Override
        public void onStart() {
            super.onStart();

            dH = new DatabaseHandler((MainActivity) getActivity());

            rv = getView().findViewById(R.id.recyclerViewComment);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 1);
            rv.setLayoutManager(gridLayoutManager);

            Cdata = new ViewModelProvider(getActivity()).get(ListCommentViewModel.class);
            CardView cv = getView().findViewById(R.id.cardTitle);

            Cdata.getMyListComment().observe(this, new Observer<List<Comment>>() {
                @Override
                public void onChanged(List<Comment> commentDetails) {
                    Cadapter = new ListCommentAdapter((MainActivity) getActivity(), commentDetails);
                    rv.setAdapter(Cadapter);

                    cv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            View dialogView = getLayoutInflater().inflate(R.layout.dialog_comment, null);

                            builder.setView(dialogView);
                            AlertDialog dialog = builder.create();
                            if (dialog.getWindow() != null){
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                            }
                            dialog.show();
                            EditText rating = dialogView.findViewById(R.id.ratingBox);
                            EditText comment = dialogView.findViewById(R.id.commentBox);
                            Button btn = dialogView.findViewById(R.id.btnSubmit);

                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dH.commentHandler(game_id ,comment.getText().toString(), rating.getText().toString());
                                    dialog.hide();
                                }
                            });
                        }
                    });
                }
            });
        }
}