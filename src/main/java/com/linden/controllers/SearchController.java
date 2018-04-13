package com.linden.controllers;

import com.linden.models.Movie;
import com.linden.models.TvShow;
import com.linden.services.CastService;
import com.linden.services.MovieService;
import com.linden.services.TvShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

public class SearchController {

    @Autowired
    private MovieService movieService;
    @Autowired
    private TvShowService tvShowService;
    @Autowired
    private CastService castService;

    @GetMapping("/search/{keywords}")
    @ResponseBody
    public void search(@PathVariable("keyword") String keywords){

        List<Movie> movies = movieService.searchMovie(keywords);
        List<TvShow> tvShows = tvShowService.searchTvShow(keywords);
//        List<Cast> cast = castService.searchCast(keywords);

        //List<List<Object>> searchResults = new List<List<Object>>;
        //searchResults.add(movies);


    }
}
