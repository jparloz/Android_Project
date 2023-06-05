package com.example.project_android.UI;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.project_android.Entities.GameDetail;
import com.example.project_android.MainActivity;
import com.example.project_android.R;
import com.example.project_android.Response.DatabaseHandler;
import com.example.project_android.ViewModel.ListGameViewModel;
import com.example.project_android.ViewModel.ListTopGamesViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    ListGameViewModel myListGame;
    ListTopGamesViewModel myListTopGame;
    DatabaseHandler dH;

    CardView cv1,cv2,cv3,cv4,cv5,cv6;
    public HomeFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        dH = new DatabaseHandler((MainActivity) getActivity());
        dH.getGameRandomHandle();

        myListGame = new ViewModelProvider(getActivity()).get(ListGameViewModel.class);
        myListTopGame = new ViewModelProvider(getActivity()).get(ListTopGamesViewModel.class);

        randomGames();
        changeCards();
    }

    private void randomGames() {
        ImageSlider is = getView().findViewById(R.id.imageSliderHome);
        ArrayList<SlideModel> sM = new ArrayList<>();

        myListGame.getMyListGame().observe(this, new Observer<List<GameDetail>>() {
            @Override
            public void onChanged(List<GameDetail> gameDetails) {
                for (GameDetail g : gameDetails) {
                    sM.add(new SlideModel(g.getBackground_image(),ScaleTypes.FIT));
                }
                is.setImageList(sM, ScaleTypes.FIT);
                is.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onItemSelected(int i) {
                        dH.getGameDetailHandle(gameDetails.get(i));
                    }
                });
            }
        });
    }

    public void changeCards(){

        cv1 = getView().findViewById(R.id.game_card1);
        cv2 = getView().findViewById(R.id.game_card2);
        cv3 = getView().findViewById(R.id.game_card3);
        cv4 = getView().findViewById(R.id.game_card4);
        cv5 = getView().findViewById(R.id.game_card5);
        cv6 = getView().findViewById(R.id.game_card6);

        TextView [] tv = new TextView[6];

        tv[0] = getView().findViewById(R.id.textView1);
        tv[1] = getView().findViewById(R.id.textView2);
        tv[2] = getView().findViewById(R.id.textView3);
        tv[3] = getView().findViewById(R.id.textView4);
        tv[4] = getView().findViewById(R.id.textView5);
        tv[5] = getView().findViewById(R.id.textView6);

        dH.getGameTopRatedHandle();

        myListTopGame.getMyListGame().observe(this, new Observer<List<GameDetail>>() {
            @Override
            public void onChanged(List<GameDetail> gameDetails) {
              for(int i = 0; i<6 ;i++){
                tv[i].setText(gameDetails.get(i).getName() + " / " +gameDetails.get(i).getRating());
              }
                setOnclick(cv1,0);
                setOnclick(cv2,1);
                setOnclick(cv3,2);
                setOnclick(cv4,3);
                setOnclick(cv5,4);
                setOnclick(cv6,5);
            }
        });

    }

    private void setOnclick(CardView cv, int posicion) {
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dH.getGameDetailHandle(myListTopGame.getMyListGame().getValue().get(posicion));
            }
        });
    }
}