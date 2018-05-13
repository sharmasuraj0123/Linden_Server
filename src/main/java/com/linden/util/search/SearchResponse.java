package com.linden.util.search;

import com.linden.models.content.Cast;
import com.linden.models.content.Movie;
import com.linden.models.content.TvShow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchResponse implements Serializable {
    private SearchResultCount resultCount;
    private List<Movie> movies;
    private List<TvShow> tvShows;
    private List<Cast> actors;

    public SearchResponse(){
        resultCount = new SearchResultCount();
        movies = new ArrayList<>();
        tvShows = new ArrayList<>();
        actors = new ArrayList<>();
    }

    public SearchResponse(List<Movie> movies,
                          List<TvShow> tvShows,
                          List<Cast> actors,
                          int movieCount,
                          int tvCount,
                          int castCount,
                          int totalResultCount) {
        this();
        this.movies = movies;
        this.tvShows = tvShows;
        this.actors = actors;
        this.resultCount = new SearchResultCount(
                movieCount, tvCount, castCount, totalResultCount
        );
    }

    public SearchResultCount getResultCount() {
        return resultCount;
    }

    public void setResultCount(SearchResultCount resultCount) {
        this.resultCount = resultCount;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public List<TvShow> getTvShows() {
        return tvShows;
    }

    public void setTvShows(List<TvShow> tvShows) {
        this.tvShows = tvShows;
    }

    public List<Cast> getActors() {
        return actors;
    }

    public void setActors(List<Cast> actors) {
        this.actors = actors;
    }
}
