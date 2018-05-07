package com.linden.controllers;

import com.linden.models.Movie;
import com.linden.repositories.MovieRepository;
import com.linden.util.search.MovieResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/movie")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @RequestMapping(
            value = "/{movieId}"
    )
    @ResponseBody
    public MovieResult getMovie(@PathVariable(value = "movieId") String movieId){
        long id = Long.parseLong(movieId);
        Movie movie = movieRepository.getMovieById(id);
        MovieResult result = new MovieResult(movie);

        return result;

    }

}