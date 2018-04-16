package com.linden.util.search;

import java.io.Serializable;

public class SearchResultCount implements Serializable {
    private int movies;
    private int tvShows;
    private int actors;
    private int all;

    public SearchResultCount(){

    }

    public SearchResultCount(int movies, int tvShows, int actors, int all) {
        this.movies = movies;
        this.tvShows = tvShows;
        this.actors = actors;
        this.all = all;
    }

    public int getMovies() {
        return movies;
    }

    public void setMovies(int movies) {
        this.movies = movies;
    }

    public int getTvShows() {
        return tvShows;
    }

    public void setTvShows(int tvShows) {
        this.tvShows = tvShows;
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
