package com.linden.controllers;

import com.linden.models.Movie;
import com.linden.repositories.MovieRepository;
import com.linden.util.search.MovieResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/movie")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @RequestMapping(
            value = "/getMovie",
            params = {"movieId"}
    )
    @ResponseBody
    public MovieResult getMovie(String movieId){
        System.out.println("lod");
        long id = Long.parseLong(movieId);
        Movie movie = movieRepository.getMovieById(id);
        MovieResult result = new MovieResult(movie);

        return result;

    }

    @RequestMapping(
            value = "/getUpcomingMovies"
    )
    @ResponseBody
    public List<MovieResult> getMovie(){
        Date todaysDate = java.sql.Date.valueOf(java.time.LocalDate.now());
        List<Movie> movies = movieRepository.getMovieByReleaseDateAfter(todaysDate);
        List<MovieResult> result = new ArrayList<MovieResult>();
        for (Movie movie: movies){
            result.add(new MovieResult(movie));
        }

        return result;

    }

}