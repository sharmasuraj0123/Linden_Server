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
    private List<MovieResult> movies;
    private List<TvShowResult> tvShows;
    private List<ActorResult> actors;

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
        if(movies != null) {
            this.movies = movies.stream()
                    .map(MovieResult::new)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        if(tvShows != null) {
            this.tvShows = tvShows.stream()
                    .map(TvShowResult::new)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        if(actors != null) {
            this.actors = actors.stream()
                    .map(ActorResult::new)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
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

    public List<MovieResult> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieResult> movies) {
        this.movies = movies;
    }

    public List<TvShowResult> getTvShows() {
        return tvShows;
    }

    public void setTvShows(List<TvShowResult> tvShows) {
        this.tvShows = tvShows;
    }

    public List<ActorResult> getActors() {
        return actors;
    }

    public void setActors(List<ActorResult> actors) {
        this.actors = actors;
    }
}
