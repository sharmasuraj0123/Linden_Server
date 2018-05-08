package com.linden.services;

import com.linden.models.Movie;
import com.linden.repositories.MovieRepository;
import com.linden.util.search.rank.ContentRanker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> searchMovie(String keywords){
        return searchMovie(keywords, new ContentRanker<>(), true);
    }

    public List<Movie> searchMovie(String keywords,
                                   Function<Movie, ? extends Comparable> pairingFunction){
        return searchMovie(keywords, new ContentRanker<>(pairingFunction), true);
    }

    public List<Movie> searchMovie(String keywords,
                                   Function<Movie, ? extends Comparable> pairingFunction,
                                   boolean desc){
        return searchMovie(keywords, new ContentRanker<>(pairingFunction), desc);
    }

    public List<Movie> searchMovie(String keywords,
                                   ContentRanker<Movie> ranker,
                                   boolean desc){
        List<Movie> movies = movieRepository.findMoviesByNameContains(keywords);
        // Return sorted list based on ranker supplied
        return  ranker.order(movies, desc);
    }

    public Movie getMovie(long movieId){
        Movie movie = movieRepository.getMovieById(movieId);
        return movie;
    }

    public List<Movie> getUpcomingMovies(Date today){
        List<Movie> movies = movieRepository.getMovieByReleaseDateAfter(today);
        return movies;
    }
}
