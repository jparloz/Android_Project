package com.example.project_android.Adapters;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.squareup.picasso.Picasso;

import java.util.List;

public class Games_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private MainActivity activity;
    private List<GameDetail> gameData;
    private boolean isLoading = false;
    private int loadingPosition = -1;
    private OnLoadMoreListener onLoadMoreListener;

    private boolean showLoadingProgress = false;
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public Games_Adapter(MainActivity activity, List<GameDetail> gameData) {
        this.activity = activity;
        this.gameData = gameData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
            return new ItemViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_item, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {

            ItemViewHolder itemHolder = (ItemViewHolder) holder;

            Picasso.get().load(gameData.get(position).getBackground_image()).resize(385,0).placeholder(com.denzcoskun.imageslider.R.drawable.loading).into(itemHolder.recImage);
            itemHolder.recTitle.setText(gameData.get(position).getName());
            itemHolder.recUser.setText(String.valueOf(gameData.get(position).getRating()));
            itemHolder.recCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseHandler databaseHandler = new DatabaseHandler(activity);
                    databaseHandler.getGameDetailHandle(gameData.get(holder.getLayoutPosition()));
                }
            });

            if (position == 9) {
                loadMoreData();
            }
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            if (showLoadingProgress) {
                loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
            } else {
                loadingViewHolder.progressBar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return gameData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == gameData.size() - 1 && isLoading ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void addNewData(List<GameDetail> newData) {
        gameData.addAll(newData);
        notifyDataSetChanged();
    }

    public void showLoading() {
        if (!showLoadingProgress) {
            showLoadingProgress = true;
            notifyItemInserted(gameData.size());
        }
    }

    public void hideLoading() {
        if (showLoadingProgress) {
            showLoadingProgress = false;
            notifyItemRemoved(gameData.size());
        }
    }

    private void loadMoreData() {
        if (onLoadMoreListener != null) {
            onLoadMoreListener.onLoadMore();
        }
    }

    private void loadFragment(Fragment fragment) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout, fragment)
                .addToBackStack(null)
                .commit();
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        private CardView recCard;
        private TextView recTitle;
        private TextView recUser;
        private ImageView recImage;

        private ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            recImage = itemView.findViewById(R.id.recImage);
            recCard = itemView.findViewById(R.id.recCard);
            recTitle = itemView.findViewById(R.id.recTitle);
            recUser = itemView.findViewById(R.id.recUser);
        }
    }

    private static class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        private LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_bar2);
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
