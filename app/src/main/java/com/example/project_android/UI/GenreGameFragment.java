package com.example.project_android.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_android.Adapters.Games_Adapter;
import com.example.project_android.Entities.GameDetail;
import com.example.project_android.MainActivity;
import com.example.project_android.R;
import com.example.project_android.Response.DatabaseHandler;
import com.example.project_android.ViewModel.ListGamesGenreViewModel;

import java.util.List;

public class GenreGameFragment extends Fragment {
    NestedScrollView nestedScrollView;
    Games_Adapter Gadapter;
    ListGamesGenreViewModel myListGenreGame;
    GameDetail gd;
    DatabaseHandler dH;
    RecyclerView rv;
    TextView nr;
    ImageView iv;
    int image;
    String name;

    int currentPage = 1;
    int visibleThreshold = 9; // Cuando estés en la posición 9, cargarás la siguiente página
    int lastVisibleItem, totalItemCount;
    boolean loading = false;

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

        nestedScrollView = getView().findViewById(R.id.scrollView);
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
                List<GameDetail> initialData;
                if (gameDetails.size() > 10) {
                    initialData = gameDetails.subList(0, 10);
                } else {
                    initialData = gameDetails;
                }
                Gadapter = new Games_Adapter((MainActivity) getActivity(), initialData);
                Gadapter.setOnLoadMoreListener(new Games_Adapter.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                            currentPage++;

                            loadMoreData(gameDetails);
                            loading = true;
                        }
                    }
                });

                rv.setAdapter(Gadapter);

                rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        totalItemCount = layoutManager.getItemCount();
                        lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                        if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                            Gadapter.showLoading();
                            currentPage++;
                            loadMoreData(gameDetails);
                            loading = true;
                        }

                    }
                });
            }
        });

    }

    private void loadMoreData(List<GameDetail> gameDetails) {
        int startIndex = (currentPage - 1) * 10;
        int endIndex = startIndex + 10;

        if (endIndex > gameDetails.size()) {
            // Si endIndex excede el tamaño de gameDetails, no hay más datos para cargar
            Gadapter.hideLoading();
            loading = false;
            return;
        }

        List<GameDetail> newData = gameDetails.subList(startIndex, endIndex);
        currentPage++; // Incrementa el valor de currentPage para la próxima carga de datos

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Gadapter.addNewData(newData);
                Gadapter.hideLoading();
                loading = false;
            }
        }, 3000);
    }

}