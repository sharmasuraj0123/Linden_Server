package com.linden.controllers;

import com.linden.models.Cast;
import com.linden.models.Movie;
import com.linden.models.TvShow;
import com.linden.repositories.CastRepository;
import com.linden.repositories.MovieRepository;
import com.linden.repositories.TvShowRepository;
import com.linden.services.MovieService;
import com.linden.util.search.MovieResult;
import com.linden.util.search.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/cast")
public class CastController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private TvShowRepository tvShowRepository;

    @Autowired
    private CastRepository castRepository;

    @RequestMapping(
            value = "/{castId}"
    )
    @ResponseBody
    public SearchResponse getFullCastDetails(@PathVariable(value = "castId") String castId){
        long id = Long.parseLong(castId);

        List<Movie> movies = movieService.getMoviesByCastId(id);
        List<TvShow> shows = tvShowRepository.getTvShowsByCastId(id);

        List<Cast> listOfCast = new ArrayList<Cast>();
        Cast  cast = castRepository.getCastById(id);
        listOfCast.add(cast);

        int numOfMovies = movies.size();
        int numOfShows = shows.size();
        int numOfCast = 1;

        int total = numOfMovies + numOfShows + numOfCast;

        SearchResponse response = new SearchResponse(movies,shows,listOfCast,numOfMovies,numOfShows,numOfCast,total);

        return response;

    }
}
