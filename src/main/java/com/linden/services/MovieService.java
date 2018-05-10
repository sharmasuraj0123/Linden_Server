package com.linden.services;

import com.linden.models.content.Movie;
import com.linden.models.content.MovieType;
import com.linden.repositories.MovieRepository;
import com.linden.util.search.rank.ContentRanker;
import com.linden.util.search.rank.Ranker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie findById(long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.orElse(null);
    }

    public List<Movie> searchMovie(String keywords){
        return searchMovie(keywords, new ContentRanker<Movie>(), true);
    }

    public List<Movie> searchMovie(Function<Movie, ? extends  Comparable> rankingFunction) {
        return searchMovie(rankingFunction, true);
    }

    public List<Movie> searchMovie(Function<Movie, ? extends  Comparable> rankingFunction, boolean desc) {
        Ranker<Movie> ranker = new Ranker<>(rankingFunction);
        return ranker.order(movieRepository.findAll());
    }

    public List<Movie> searchMovie(String keywords,
                                   Function<Movie, ? extends Comparable> rankingFunction){
        return searchMovie(keywords, new ContentRanker<>(rankingFunction), true);
    }

    public List<Movie> searchMovie(String keywords,
                                   Function<Movie, ? extends Comparable> rankingFunction,
                                   boolean desc){
        return searchMovie(keywords, new ContentRanker<>(rankingFunction), desc);
    }

    public List<Movie> searchMovie(String keywords,
                                   ContentRanker<Movie> ranker,
                                   boolean desc){
        List<Movie> movies = movieRepository.findMoviesByNameContains(keywords);
        // Return sorted list based on ranker supplied
        return  ranker.order(movies, desc);
    }

    public List<Movie> getFeaturedMovies(){
        return movieRepository.findByIsFeaturedTrue();
    }

    public Collection<Movie> getOpeningThisWeek(){
        return movieRepository.findByMovieType(MovieType.COMING_SOON).parallelStream().filter(
            movie -> {
                Duration duration = Duration.between(movie.getReleaseDate().toInstant(), Instant.now());
                System.out.println(duration.toDays());
                return duration.toDays() <= 7;
            }
        ).collect(Collectors.toCollection(HashSet::new));
    }

    public Collection<Movie> getOpeningThisWeek(int limit){
        return movieRepository.findByMovieType(MovieType.COMING_SOON).parallelStream().filter(
                movie -> {
                    Duration duration = Duration.between(movie.getReleaseDate().toInstant(), Instant.now());
                    System.out.println(duration.toDays());
                    return duration.toDays() <= 7;
                }
        ).limit(limit).collect(Collectors.toCollection(HashSet::new));
    }

    public List<Movie> getTopBoxOffice() {
        return movieRepository.findByOrderByBoxOfficeDesc();
    }

    public List<Movie> getHighestRated() {
        return movieRepository.findByOrderByScoreDesc();
    }

    public Movie getMovie(long movieId){
        Optional<Movie> movie = movieRepository.findById(movieId);
        System.out.println(movie);
        return movie.orElse(null);
    }

    public List<Movie> getUpcomingMovies(Date today){
        List<Movie> movies = movieRepository.getMovieByReleaseDateAfter(today);
        return movies;
    }

    public List<Movie> getMoviesByCastId(long castId){
        List<Movie> movies = movieRepository.getMoviesByCastId(castId);
        return movies;
    }
}
