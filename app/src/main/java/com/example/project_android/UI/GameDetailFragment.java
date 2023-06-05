package com.example.project_android.UI;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_android.Entities.GameDetail;
import com.example.project_android.R;
import com.example.project_android.ViewModel.GameViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;


public class GameDetailFragment extends Fragment {

    GameViewModel mgam;
    TextView gameName, gameDev, gameRating, gameMeta, gameAge,gameRelease, gamePlatform, gameGenres, gamesDesc;
    ImageView gameImage;
    FloatingActionButton fab;

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

        mgam = new ViewModelProvider(getActivity()).get(GameViewModel.class);
        GameDetail gd = mgam.getMyGame();

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
        gameMeta.setText(gd.getMeta_rating()+"/100");
        gameAge.setText(gd.getAge_rating());
        gameRelease.setText(gd.getRelease());
        gamesDesc.setText(gd.getDescription());

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
            }
        });
    }
}