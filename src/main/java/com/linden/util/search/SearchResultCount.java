package com.linden.util.search;

import java.io.Serializable;

public class SearchResultCount implements Serializable {
    private int movies;
    private int tvShow;
    private int actors;
    private int all;

    public SearchResultCount(){

    }

    public SearchResultCount(int movies, int tvShow, int actors, int all) {
        this.movies = movies;
        this.tvShow = tvShow;
        this.actors = actors;
        this.all = all;
    }

    public int getMovies() {
        return movies;
    }

    public void setMovies(int movies) {
        this.movies = movies;
    }

    public int getTvShow() {
        return tvShow;
    }

    public void setTvShow(int tvShow) {
        this.tvShow = tvShow;
    }

    public int getActors() {
        return actors;
    }

    public void setActors(int actors) {
        this.actors = actors;
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }
}
