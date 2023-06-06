package com.example.project_android.UI;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.project_android.Adapters.GenreAdapter;
import com.example.project_android.Entities.GenreDetail;

import com.example.project_android.R;

import java.util.ArrayList;

public class GamesFragment extends Fragment {

    GenreAdapter genreAdapter;
    ArrayList<GenreDetail> dataArrayList = new ArrayList<>();
    GenreDetail genreDetail;
    ListView listView;
    SearchView sv;
    public GamesFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_games, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        listView = getView().findViewById(R.id.listview);
        int[] imageList = {R.drawable.genre1, R.drawable.genre2, R.drawable.genre3, R.drawable.genre4, R.drawable.genre5, R.drawable.genre6, R.drawable.genre7, R.drawable.genre8, R.drawable.genre9, R.drawable.genre10, R.drawable.genre11, R.drawable.genre12, R.drawable.genre13, R.drawable.genre14, R.drawable.genre15, R.drawable.genre16, R.drawable.genre17, R.drawable.genre18, R.drawable.genre19};
        String[] nameList = {"Action", "Adventure", "RPG", "Shooter", "Puzzle", "Indie", "Platformer", "Massively Multiplayer", "Sports", "Racing", "Simulation", "Arcade", "Casual", "Fighting", "Strategy", "Family", "Educational", "Board_Games", "Card"};
        for (int i = 0; i < imageList.length; i++) {
            genreDetail = new GenreDetail(nameList[i], imageList[i]);
            dataArrayList.add(genreDetail);
        }
        sv = getView().findViewById(R.id.searchGame);
        sv.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //searchList(newText);
                return true;
            }
        });

        genreAdapter = new GenreAdapter(requireContext(), dataArrayList);
        listView.setAdapter(genreAdapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadFragment(new GenreGameFragment(imageList[position],nameList[position]));
            }
        });

    }
    private void loadFragment(Fragment fragmento){
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout,fragmento)
                .commit();
    }
   /* private void searchList(String text) {
        List<ArticleDetail> dataSearchList = new ArrayList<>();
        for (ArticleDetail data : Adata){
            if (data.getDataTitle().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(data);
            }
        }
        if (dataSearchList.isEmpty()){
            Toast.makeText((MainActivity)this.getActivity(), "Not Found", Toast.LENGTH_SHORT).show();
        } else {
            //Aadapter.setSearchList(dataSearchList);
        }
    }*/
}