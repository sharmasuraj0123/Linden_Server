package com.linden.services;

import com.linden.models.Movie;
import com.linden.repositories.MovieRepository;
import com.linden.util.search.rank.ContentRanker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> seachMovie(String keywords){
        return searchMovie(keywords, new ContentRanker<>());
    }

    public List<Movie> searchMovie(String keywords, ContentRanker<Movie> ranker){
        List<Movie> movies = movieRepository.findMoviesByNameContains(keywords);
        // Return sorted list based on ranker supplied
        return  ranker.order(movies);
    }
}
