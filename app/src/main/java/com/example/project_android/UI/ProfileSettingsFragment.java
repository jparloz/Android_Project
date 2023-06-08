package com.example.project_android.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

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
import android.widget.TextView;

import com.example.project_android.Adapters.Comment_Adapter;
import com.example.project_android.Entities.CommentDetail;
import com.example.project_android.MainActivity;
import com.example.project_android.R;
import com.example.project_android.Response.DatabaseHandler;
import com.example.project_android.ViewModel.ListCommentUserViewModel;

import java.util.List;

public class ProfileSettingsFragment extends Fragment {

    DatabaseHandler dH;
    SharedPreferences sharedPreferences;

    SharedPreferences.Editor editor;
    Comment_Adapter Cadapter;
    RecyclerView rv;

    CardView cv_edit, cv_update;
    ListCommentUserViewModel myListComment;

    public ProfileSettingsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_settings, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        myListComment = new ViewModelProvider(getActivity()).get(ListCommentUserViewModel.class);
        dH = new DatabaseHandler((MainActivity) getActivity());
        dH.getCommentsByUserHandler();
        myListComment.getMyListComment().observe(this, new Observer<List<CommentDetail>>() {
            @Override
            public void onChanged(List<CommentDetail> commentDetails) {
                loadRecyclerComments(commentDetails);
            }
        });
        sharedPreferences = getContext().getSharedPreferences("MODE", Context.MODE_PRIVATE);

        CardControl();
    }



    private void CardControl() {
        cv_edit = getView().findViewById(R.id.cardProfile);
        cv_update = getView().findViewById(R.id.cardPassword);

        cv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    View dialogView = getLayoutInflater().inflate(R.layout.dialog_profile, null);

                    builder.setView(dialogView);
                    AlertDialog dialog = builder.create();
                    if (dialog.getWindow() != null){
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }
                    dialog.show();
                    Button btn = dialogView.findViewById(R.id.btnSubmit);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TextView name = dialogView.findViewById(R.id.editUsername);
                            TextView email = dialogView.findViewById(R.id.editUserEmail);

                            dH.updateUserHandler(name.getText().toString(),email.getText().toString());
                            dialog.hide();
                        }
                    });
                }
            }

        });
        cv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    View dialogView = getLayoutInflater().inflate(R.layout.dialog_profile, null);

                    builder.setView(dialogView);
                    AlertDialog dialog = builder.create();
                    if (dialog.getWindow() != null){
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }
                    changesValues(dialogView);

                    dialog.show();
                    Button btn = dialogView.findViewById(R.id.btnSubmit);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TextView new_password = dialogView.findViewById(R.id.editUserEmail);
                            TextView current_password = dialogView.findViewById(R.id.editUsername);
                            dH.updatePasswordHandler(current_password.getText().toString(),new_password.getText().toString());
                            dialog.hide();
                        }
                    });

                }
            }
        });
    }
    private void changesValues(View dialogView) {
        TextView edit_title = dialogView.findViewById(R.id.editTitle);
        TextView new_password = dialogView.findViewById(R.id.editUserEmail);
        TextView current_password = dialogView.findViewById(R.id.editUsername);
        TextView data = dialogView.findViewById(R.id.userDesc);
        Drawable drawable = getResources().getDrawable(R.drawable.baseline_password_24);

        data.setText(" ");
        edit_title.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
        edit_title.setText("Update Password");
        current_password.setHint("Current Password");
        new_password .setHint("New Password");
    }
    private void loadRecyclerComments(List<CommentDetail> commentList) {

        rv = getView().findViewById(R.id.recyclerViewComment);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 1);
        rv.setLayoutManager(gridLayoutManager);

        Cadapter = new Comment_Adapter((MainActivity)this.getActivity(), commentList);
        rv.setAdapter(Cadapter);

    }

}