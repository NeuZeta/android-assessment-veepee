package com.vp.favorites;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vp.favorites.model.FavoriteMovie;
import com.vp.list.GlideApp;

import java.util.List;

public class FavListAdapter extends RecyclerView.Adapter<FavListAdapter.MyViewHolder> {

    private List<FavoriteMovie> favsList;
    private final OnItemClickListener listener;
    private static final String NO_IMAGE = "N/A";

    public FavListAdapter(List<FavoriteMovie> myDataset, OnItemClickListener listener) {
        favsList = myDataset;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder (LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FavoriteMovie favoriteMovie = favsList.get(position);

        if (favoriteMovie.getPoster() != null && !NO_IMAGE.equals(favoriteMovie.getPoster())) {
            final float density = holder.image.getResources().getDisplayMetrics().density;
            GlideApp
                    .with(holder.image)
                    .load(favoriteMovie.getPoster())
                    .override((int) (300 * density), (int) (600 * density))
                    .into(holder.image);
        }

        holder.bind(favsList.get(position), listener);


    }


    @Override
    public int getItemCount() {
        return favsList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        MyViewHolder(View itemView) {
            super(itemView);
            //itemView.setOnClickListener(this);
            image = itemView.findViewById(R.id.poster);
        }

        public void bind(final FavoriteMovie item, final OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }

    }

    public interface OnItemClickListener {
        void onItemClick(FavoriteMovie item);
    }

}