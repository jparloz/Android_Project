package com.example.project_android.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_android.Entities.ArticleDetail;
import com.example.project_android.R;
import com.example.project_android.ViewModel.ArticleViewModel;


public class ArticleDetailFragment extends Fragment {
    TextView detailDesc, detailTitle;
    ImageView detailImage;
    public ArticleDetailFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        ArticleViewModel mart = new ViewModelProvider(getActivity()).get(ArticleViewModel.class);
        ArticleDetail ad = mart.getMyArticle();
        
        detailDesc = getView().findViewById(R.id.detailDesc);
        detailTitle = getView().findViewById(R.id.detailTitle);
        detailImage = getView().findViewById(R.id.detailImage);
        
        if (ad != null){
            detailDesc.setText(ad.getDataDesc());
            detailImage.setImageResource(ad.getDataImage());
            detailTitle.setText(ad.getDataTitle());
        }
    }
}