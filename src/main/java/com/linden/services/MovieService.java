package com.linden.services;

import com.linden.models.Movie;
import com.linden.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> searchMovie(String keywords){

        List<Movie> movies = movieRepository.findMoviesByNameContains(keywords);

        return movies;
    }


}
