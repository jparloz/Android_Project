package com.example.project_android.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_android.Entities.ArticleDetail;
import com.example.project_android.MainActivity;
import com.example.project_android.R;
import com.example.project_android.UI.ArticleDetailFragment;
import com.example.project_android.ViewModel.ArticleViewModel;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class Articles_Adapter extends RecyclerView.Adapter<MyViewHolder> {

    private MainActivity activity;
    private List<ArticleDetail> Adata;

    private ArticleViewModel mart;


    public Articles_Adapter(MainActivity activity, List<ArticleDetail> Adata){
        this.activity = activity;
        this.Adata = Adata;
    }
    //public void setSearchList(List<ArticleDetail> dataSearchList){
        //this.Adata = dataSearchList;
        //notifyDataSetChanged();
    //}

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.recImage.setImageResource(Adata.get(position).getDataImage());
        holder.recTitle.setText(Adata.get(position).getDataTitle());
        holder.recUser.setText(Adata.get(position).getDataUser());
        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mart = new ViewModelProvider(activity).get(ArticleViewModel.class);
                mart.setMyArticle(Adata.get(holder.getAdapterPosition()));
                loadFragment(new ArticleDetailFragment());
            }
        });
    }
    @Override
    public int getItemCount() {
        return Adata.size();
    }
    private void loadFragment(Fragment fragmento){
        activity
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout,fragmento)
                .addToBackStack(null)
                .commit();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{

    ImageView recImage;
    TextView recTitle, recUser;
    CardView recCard;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        recImage = itemView.findViewById(R.id.recImage);
        recTitle = itemView.findViewById(R.id.recTitle);
        recUser = itemView.findViewById(R.id.recUser);
        recCard = itemView.findViewById(R.id.recCard);
    }


}
