package com.example.project_android.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import com.example.project_android.Adapters.Articles_Adapter;
import com.example.project_android.Entities.ArticleDetail;
import com.example.project_android.MainActivity;
import com.example.project_android.R;

import java.util.ArrayList;
import java.util.List;

public class ArticlesFragment extends Fragment {

    RecyclerView rv;
    List<ArticleDetail> Adata;
    Articles_Adapter Aadapter;
    ArticleDetail ad;
    SearchView sv;
    public ArticlesFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_articles, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        rv = getView().findViewById(R.id.recyclerView);
        sv = getView().findViewById(R.id.searchArticle);
        sv.clearFocus();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 1);
        rv.setLayoutManager(gridLayoutManager);
        Adata= new ArrayList<>();
        ad = new ArticleDetail("Camera", "Hola", "Java", R.drawable.logo);
        Adata.add(ad);
        ad = new ArticleDetail("RecyclerView", "Goosbay", "Kotlin", R.drawable.logo);
        Adata.add(ad);
        ad = new ArticleDetail("Date Picker", "Hola", "Java", R.drawable.logo);
        Adata.add(ad);
        ad = new ArticleDetail("EditText", "Goosbay", "Kotlin", R.drawable.logo);
        Adata.add(ad);
        ad = new ArticleDetail("Rating Bar", "Hola", "Java", R.drawable.logo);
        Adata.add(ad);

        for (ArticleDetail ad: Adata) {
            Log.d("dato",ad.toString());
        }

        Aadapter = new Articles_Adapter((MainActivity)this.getActivity(), Adata);
        rv.setAdapter(Aadapter);
    }
    private void searchList(String text){
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
    }
}