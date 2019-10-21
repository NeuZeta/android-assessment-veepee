package com.vp.favorites;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.vp.favorites.model.FavoriteMovie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreference {

    public static final String PREFS_NAME = "MOVIES_APP";
    public static final String FAVORITES = "Movie_Favorite";

    public SharedPreference() {
        super();
    }

    public void saveFavorites(Context context, List<FavoriteMovie> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, FavoriteMovie favoriteMovie) {
        List<FavoriteMovie> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<FavoriteMovie>();
        if (!favorites.contains(favoriteMovie)){
            favorites.add(favoriteMovie);
        }
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, String id) {
        ArrayList<FavoriteMovie> favorites = getFavorites(context);
        FavoriteMovie favoriteMovie = null;

        if (favorites != null) {
            for (FavoriteMovie fm : favorites){
                if (fm.getImdbID().equals(id)){
                    favoriteMovie = fm;
                }
            }
            favorites.remove(favoriteMovie);
        }
        saveFavorites(context, favorites);
    }

    public ArrayList<FavoriteMovie> getFavorites(Context context) {

        SharedPreferences settings;

        List<FavoriteMovie> favoriteMovies;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {

            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            FavoriteMovie[] favoriteItems = gson.fromJson(jsonFavorites,
                    FavoriteMovie[].class);

            favoriteMovies = Arrays.asList(favoriteItems);
            favoriteMovies = new ArrayList<FavoriteMovie>(favoriteMovies);
        } else{
            return null;
        }

        return (ArrayList<FavoriteMovie>) favoriteMovies;
    }

    public ArrayList<String> getFavoritesIds (Context context) {

        ArrayList<FavoriteMovie> favoriteMovies = getFavorites(context);

        ArrayList<String> favoriteMoviesIds = new ArrayList<>();
        if (favoriteMovies != null){
            for (FavoriteMovie fm : favoriteMovies){
                favoriteMoviesIds.add(fm.getImdbID());
            }
        }

        return favoriteMoviesIds;
    }

}
