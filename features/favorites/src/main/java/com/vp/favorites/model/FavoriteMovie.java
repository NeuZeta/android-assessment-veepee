package com.vp.favorites.model;

public class FavoriteMovie {

    private String imdbID;
    private String poster;

    public FavoriteMovie() {
    }

    public FavoriteMovie(String imdbID, String poster) {
        this.imdbID = imdbID;
        this.poster = poster;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public String toString() {
        return "FavoriteMovie{" +
                "imdbID='" + imdbID + '\'' +
                ", poster='" + poster + '\'' +
                '}';
    }

}
