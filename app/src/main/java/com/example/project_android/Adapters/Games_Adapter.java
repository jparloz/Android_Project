package com.example.project_android.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_android.Entities.GameDetail;
import com.example.project_android.MainActivity;
import com.example.project_android.R;
import com.example.project_android.Response.DatabaseHandler;
import com.example.project_android.UI.GameDetailFragment;
import com.example.project_android.ViewModel.GameViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Games_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private MainActivity activity;
    private List<GameDetail> Gdata;
    private GameViewModel mgam;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private boolean isLoading = false;
    private OnLoadMoreListener onLoadMoreListener;

    public Games_Adapter(MainActivity activity, List<GameDetail> Gdata) {
        this.activity = activity;
        this.Gdata = Gdata;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
            return new MyViewHolderGame(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_item, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolderGame) {
            MyViewHolderGame itemHolder = (MyViewHolderGame) holder;
            Picasso.get().load(Gdata.get(position).getBackground_image()).into(itemHolder.recImage);
            itemHolder.recTitle.setText(Gdata.get(position).getName());
            itemHolder.recUser.setText(String.valueOf(Gdata.get(position).getRating()));
            itemHolder.recCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseHandler dH = new DatabaseHandler(activity);
                    dH.getGameDetailHandle(Gdata.get(holder.getLayoutPosition()));

                    loadFragment(new GameDetailFragment());
                }
            });
        } else if (holder instanceof LoadingViewHolder) {
            // Mostrar vista de carga
        }

        // Verificar si es el último elemento y no está cargando para activar la paginación
        if (position == getItemCount() - 1 && !isLoading && onLoadMoreListener != null) {
            isLoading = true;
            onLoadMoreListener.onLoadMore();
        }
    }

    @Override
    public int getItemCount() {
        return Gdata.size();
    }

    @Override
    public int getItemViewType(int position) {
        return Gdata.get(position) != null ? VIEW_TYPE_ITEM : VIEW_TYPE_LOADING;
    }

    public void addNewData(List<GameDetail> newData) {
        Gdata.addAll(newData);
        notifyDataSetChanged();
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    private void loadFragment(Fragment fragment) {
        activity
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout, fragment)
                .addToBackStack(null)
                .commit();
    }

    private static class MyViewHolderGame extends RecyclerView.ViewHolder {
        private CardView recCard;
        private ImageView recImage;
        private TextView recTitle;
        private TextView recUser;

        private MyViewHolderGame(@NonNull View itemView) {
            super(itemView);
            recCard = itemView.findViewById(R.id.recCard);
            recImage = itemView.findViewById(R.id.recImage);
            recTitle = itemView.findViewById(R.id.recTitle);
            recUser = itemView.findViewById(R.id.recUser);
        }
    }

    private static class LoadingViewHolder extends RecyclerView.ViewHolder {
        private LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}

