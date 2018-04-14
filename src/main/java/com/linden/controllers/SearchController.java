package com.linden.controllers;

import com.linden.models.Cast;
import com.linden.models.Movie;
import com.linden.models.TvShow;
import com.linden.services.CastService;
import com.linden.services.MovieService;
import com.linden.services.TvShowService;
import com.linden.util.search.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private TvShowService tvShowService;
    @Autowired
    private CastService castService;

    @RequestMapping(
        value = "/search",
        params = {"keywords", "page"}
    )
    @ResponseBody
    public SearchResponse search(String keywords){
        keywords = keywords.replaceAll("[+]", "[ ]");
        List<Movie> movies = movieService.seachMovie(keywords);
        List<TvShow> tvShows = tvShowService.searchTvShow(keywords);
        // TODO: implement cast search
        List<Cast> cast = castService.searchCast(keywords);

        return new SearchResponse(movies, tvShows, cast);
    }
}
