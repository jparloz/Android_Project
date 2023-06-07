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
import com.example.project_android.ViewModel.ListSearchGamesViewModel;

import java.util.List;

public class SearchFragment extends Fragment {
    NestedScrollView nestedScrollView;
    Games_Adapter Gadapter;
    ListSearchGamesViewModel myListSearchGame;
    RecyclerView rv;

    int currentPage = 1;
    int visibleThreshold = 9;
    int lastVisibleItem, totalItemCount;
    boolean loading = false;

    public SearchFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
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

        myListSearchGame = new ViewModelProvider(getActivity()).get(ListSearchGamesViewModel.class);
        nestedScrollView = getView().findViewById(R.id.scrollView);
        rv = getView().findViewById(R.id.recyclerViewGame);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 1);
        rv.setLayoutManager(gridLayoutManager);


        myListSearchGame.getMyListGame().observe(this, new Observer<List<GameDetail>>() {
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
        int endIndex = Math.min(startIndex + 10, gameDetails.size());

        if (endIndex <= startIndex) {
            // No hay más datos para cargar
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
        }, 5000);
    }
}

