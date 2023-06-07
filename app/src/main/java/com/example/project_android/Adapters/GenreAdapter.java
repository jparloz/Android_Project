package com.example.project_android.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project_android.Entities.GameDetail;
import com.example.project_android.Entities.GenreDetail;
import com.example.project_android.MainActivity;
import com.example.project_android.R;

public class GenreAdapter extends ArrayAdapter<GenreDetail> {

        public GenreAdapter(Context context, ArrayList<GenreDetail> dataArrayList) {
            super(context, R.layout.genre_list, dataArrayList);
        }

        @Override
        public View getView(int position, @Nullable View view,  ViewGroup parent) {
            GenreDetail genreDetail = getItem(position);
            if (view == null){
                view = LayoutInflater.from(getContext()).inflate(R.layout.genre_list, parent, false);
            }
            ImageView listImage = view.findViewById(R.id.listImage);
            TextView listName = view.findViewById(R.id.listName);

            listImage.setImageResource(genreDetail.getImage());
            listName.setText(genreDetail.getName());

            return view;
        }


}
