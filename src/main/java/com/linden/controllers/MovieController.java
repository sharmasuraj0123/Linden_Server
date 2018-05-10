package com.linden.controllers;

import com.linden.models.content.Movie;
import com.linden.services.MovieService;
import com.linden.util.search.SearchResponse;
import com.linden.repositories.MovieRepository;
import com.linden.services.MovieService;
import com.linden.util.search.MovieResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Value("${movie.result.limit:10}")
    private int RESULT_LIMIT;

    @RequestMapping(value = "/featured", method = RequestMethod.GET)
    @ResponseBody
    public List<Movie> getFeaturedMoviesRoute() {
        return movieService.getFeaturedMovies();
    }

    @RequestMapping(
            value = "/openingThisWeek",
            params = {"limit"},
            method = RequestMethod.GET
    )
    @ResponseBody
    public Collection<Movie> getOpeningThisWeek(int limit) {
        return movieService.getOpeningThisWeek(limit);
    }

    @RequestMapping(
            value = "/openingThisWeek",
            method = RequestMethod.GET
    )
    @ResponseBody
    public Collection<Movie> getOpeningThisWeek() {
        return movieService.getOpeningThisWeek(RESULT_LIMIT);
    }

    @RequestMapping(
            value = "/getHighestRatedMovies",
            method = RequestMethod.GET
    )
    @ResponseBody
    public SearchResponse getHighestRatedMovies() {
        return getHighestRatedMovies(1);
    }

    @RequestMapping(
            value = "/getHighestRatedMovies",
            params = {"page"},
            method = RequestMethod.GET
    )
    @ResponseBody
    public SearchResponse getHighestRatedMovies(int page) {
        List<Movie> movies = movieService.getHighestRated();
        movies = (movies == null) ? Collections.EMPTY_LIST : movies;
        if (page > 0) {
            if ((page - 1) * RESULT_LIMIT <= movies.size()) {
                movies = movies.subList(
                        (page - 1) * RESULT_LIMIT,
                        (page * RESULT_LIMIT > movies.size()) ?
                                movies.size() : page * RESULT_LIMIT
                );
            }
        }
        return new SearchResponse(
                movies,
                Collections.EMPTY_LIST,
                Collections.EMPTY_LIST,
                movies.size(),
                0,
                0,
                movies.size()
        );
    }
    
    @RequestMapping(
            value = "/{movieId}"
    )
    @ResponseBody
    public MovieResult getMovie(@PathVariable(value = "movieId") long movieId){
        Movie movie = movieService.getMovie(movieId);
        MovieResult result = new MovieResult(movie);

        return result;

    }

    @RequestMapping(
            value = "/getComingSoon"
    )
    @ResponseBody
    public List<MovieResult> getComingSoon(){
        Date todaysDate = java.sql.Date.valueOf(java.time.LocalDate.now());
        List<Movie> movies = movieService.getUpcomingMovies(todaysDate);
        List<MovieResult> result = new ArrayList<>();
        System.out.println("Printing coming soon ...");
        for (Movie movie: movies){
            result.add(new MovieResult(movie));
            System.out.println(movie.getName());
        }
        return result;

    }

}