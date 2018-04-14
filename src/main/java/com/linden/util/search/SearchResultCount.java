package com.linden.util.search;

import java.io.Serializable;

public class SearchResultCount implements Serializable {
    private long movies;
    private long tvShow;
    private long actors;
    private long all;

    public SearchResultCount(){

    }

    public SearchResultCount(long movies, long tvShow, long actors) {
        this.movies = movies;
        this.tvShow = tvShow;
        this.actors = actors;
        this.all = movies + tvShow + actors;
    }

    public long getMovies() {
        return movies;
    }

    public void setMovies(long movies) {
        this.movies = movies;
    }

    public long getTvShow() {
        return tvShow;
    }

    public void setTvShow(long tvShow) {
        this.tvShow = tvShow;
    }

    public long getActors() {
        return actors;
    }

    public void setActors(long actors) {
        this.actors = actors;
    }

    public long getAll() {
        return all;
    }

    public void setAll(long all) {
        this.all = all;
    }
}
