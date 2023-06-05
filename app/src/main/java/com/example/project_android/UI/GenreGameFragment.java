package com.example.project_android.UI;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.project_android.Adapters.Games_Adapter;
import com.example.project_android.Entities.Developer;
import com.example.project_android.Entities.GameDetail;
import com.example.project_android.MainActivity;
import com.example.project_android.R;
import com.example.project_android.Response.DatabaseHandler;
import com.example.project_android.ViewModel.ListGamesGenreViewModel;

import java.util.ArrayList;
import java.util.List;

public class GenreGameFragment extends Fragment {
    List<GameDetail> Gdata;
    Games_Adapter Gadapter;
    ListGamesGenreViewModel myListGenreGame;
    GameDetail gd;
    DatabaseHandler dH;
    RecyclerView rv;
    TextView nr;
    ImageView iv;
    int image;
    String name;

    public GenreGameFragment() {

    }
    public GenreGameFragment(int image, String name) {
        this.image=image;
        this.name=name;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_genre_game, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadXML();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void loadXML() {
        String fileName= getResources().getResourceName(image);
        fileName = fileName.substring(42,fileName.length());

        myListGenreGame = new ViewModelProvider(getActivity()).get(ListGamesGenreViewModel.class);

        dH = new DatabaseHandler((MainActivity) getActivity());
        dH.getGameByGenreHandle(fileName);

        rv = getView().findViewById(R.id.recyclerViewGame);
        iv = getView().findViewById(R.id.imageResult);
        iv.setImageResource(image);
        nr = getView().findViewById(R.id.nameResult);
        nr.setText(name);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 1);
        rv.setLayoutManager(gridLayoutManager);


        myListGenreGame.getMyListGenreGame().observe(this, new Observer<List<GameDetail>>() {
            @Override
            public void onChanged(List<GameDetail> gameDetails) {
                Gadapter = new Games_Adapter((MainActivity) getActivity(), gameDetails);
                rv.setAdapter(Gadapter);
            }
        });

    }

}