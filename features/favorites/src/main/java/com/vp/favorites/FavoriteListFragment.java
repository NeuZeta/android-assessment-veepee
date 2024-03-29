package com.vp.favorites;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.vp.favorites.model.FavoriteMovie;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ViewAnimator viewAnimator;
    private TextView noFavsTextView;
    private RecyclerView.Adapter mAdapter;
    private SharedPreference sharedPreference;
    private List<FavoriteMovie> favorites;

    public FavoriteListFragment() {
        super();
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreference = new SharedPreference();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.idFavList);
        viewAnimator = view.findViewById(R.id.viewAnimator);
        noFavsTextView = view.findViewById(R.id.noFavoritesText);

        initList();
        handleResult();
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
        handleResult();
    }

    private void initList(){
        favorites = sharedPreference.getFavorites(getContext());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 3);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new FavListAdapter(favorites, new FavListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FavoriteMovie item) {
                String url = "app://movies/detail?imdbID=" + item.getImdbID();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    private void handleResult(){
        if (favorites == null || favorites.isEmpty()) {
            showNoFavsMessage();
        } else {

            if (favorites.size() == 0) {
                showNoFavsMessage();
            }

            showList();
        }
    }

    private void showList() {
        viewAnimator.setDisplayedChild(viewAnimator.indexOfChild(recyclerView));
    }

    private void showNoFavsMessage() {
        viewAnimator.setDisplayedChild(viewAnimator.indexOfChild(noFavsTextView));
    }


}
