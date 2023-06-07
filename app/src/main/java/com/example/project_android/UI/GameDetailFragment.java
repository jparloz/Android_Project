package com.example.project_android.UI;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_android.Entities.GameDetail;
import com.example.project_android.Entities.Genres;
import com.example.project_android.Entities.Platforms;
import com.example.project_android.MainActivity;
import com.example.project_android.R;
import com.example.project_android.Response.DatabaseHandler;
import com.example.project_android.ViewModel.GameViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import okhttp3.internal.platform.Platform;


public class GameDetailFragment extends Fragment {

    GameViewModel mgam;
    TextView gameName, gameRating, gameMeta, gameAge,gameRelease, gamePlatform, gameGenres, gamesDesc;
    ImageView gameImage;
    FloatingActionButton fab;

    DatabaseHandler dH;

    public GameDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        dH = new DatabaseHandler((MainActivity) getActivity());

        mgam = new ViewModelProvider(getActivity()).get(GameViewModel.class);
        GameDetail gd = mgam.getMyGame();

        iniciateXML(gd);

        fab = getView().findViewById(R.id.addComent);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        dH.commentHandler(gd.getId(),comment.getText().toString(), rating.getText().toString());
                        dialog.hide();
                    }
                });
            }
        });
    }

    private void iniciateXML(GameDetail gd) {
        gameImage=getView().findViewById(R.id.gameImage);
        gameName = getView().findViewById(R.id.gameName);
        gameRating = getView().findViewById(R.id.gameRating);
        gameMeta = getView().findViewById(R.id.gameMeta);
        gameAge = getView().findViewById(R.id.gameAge);
        gameRelease = getView().findViewById(R.id.gameRelease);
        gamePlatform = getView().findViewById(R.id.gamePlatform);
        gameGenres = getView().findViewById(R.id.gameGenres);
        gamesDesc = getView().findViewById(R.id.gDesc);

        Picasso.get().load(gd.getBackground_image()).into(gameImage);
        gameName.setText(gd.getName());
        gameRating.setText(gd.getRating() + "/5");
        ArrayList<String> platformNames = new ArrayList<>();
        if(gd.getPlatforms()!=null){
            for (Platforms platform : gd.getPlatforms()) {
                platformNames.add(platform.getName());
            }
            String concatenatedNames = TextUtils.join(", ", platformNames);
            gamePlatform.setText(concatenatedNames);
        }
        if(gd.getGenres()!=null){
            ArrayList<String> genreNames = new ArrayList<>();
            for (Genres genre : gd.getGenres()) {
                genreNames.add(genre.getName());
            }
            String concatenatedNames = TextUtils.join(", ", genreNames);
            gameGenres.setText(concatenatedNames);
        }
        if(gd.getMeta_rating()!=null){gameMeta.setText(gd.getMeta_rating()+"/100");}
        if(gd.getAge_rating()!=null){gameAge.setText(gd.getAge_rating());}
        gameRelease.setText(gd.getRelease());
        gamesDesc.setText(gd.getDescription());
    }
}