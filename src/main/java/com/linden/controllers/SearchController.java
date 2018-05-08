package com.linden.controllers;

import com.linden.models.content.Cast;
import com.linden.models.content.Movie;
import com.linden.models.content.TvShow;
import com.linden.services.CastService;
import com.linden.services.MovieService;
import com.linden.services.TvShowService;
import com.linden.util.search.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class SearchController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private TvShowService tvShowService;

    @Autowired
    private CastService castService;

    @Value("${search.result.limit:10}")
    private int RESULT_LIMIT;

    @RequestMapping(
            value = "/search",
            params = {"keywords"}
    )
    public SearchResponse search(String keywords){
        return search(keywords, 1);
    }

    @RequestMapping(
        value = "/search",
        params = {"keywords", "page"}
    )
    @ResponseBody
    public SearchResponse search(String keywords, int page){
        int movieCount = 0, tvCount = 0, castCount = 0, totalResultCount = 0;
        if(page > 0) {
            keywords = keywords.replaceAll("[+]", "[ ]");
            List<Movie> movies = movieService.searchMovie(keywords);
            List<TvShow> tvShows = tvShowService.searchTvShow(keywords);
            List<Cast> cast = castService.searchCast(keywords);

            movies = (movies == null) ? Collections.EMPTY_LIST : movies;
            tvShows = (tvShows == null) ? Collections.EMPTY_LIST : tvShows;
            cast = (cast == null) ? Collections.EMPTY_LIST : cast;
            movieCount = movies.size();
            tvCount = tvShows.size();
            castCount = cast.size();
            totalResultCount = movieCount + tvCount + castCount;

            if ((page - 1) * RESULT_LIMIT <= movies.size()) {
                movies = movies.subList(
                    (page - 1) * RESULT_LIMIT,
                    (page * RESULT_LIMIT > movies.size()) ?
                        movies.size() : page * RESULT_LIMIT
                );
            }
            if ((page - 1) * RESULT_LIMIT <= tvShows.size()) {
                tvShows = tvShows.subList(
                    (page - 1) * RESULT_LIMIT,
                    (page * RESULT_LIMIT > tvShows.size()) ?
                        tvShows.size() : page * RESULT_LIMIT
                );
            }
            if ((page - 1) * RESULT_LIMIT <= cast.size()) {
                cast = cast.subList(
                    (page - 1) * RESULT_LIMIT,
                    (page * RESULT_LIMIT > cast.size()) ?
                        cast.size() : page * RESULT_LIMIT
                );
            }

            return new SearchResponse(
                movies,
                tvShows,
                cast,
                movieCount,
                tvCount,
                castCount,
                totalResultCount
            );
        }
        else {
            return new SearchResponse(
                Collections.EMPTY_LIST,
                Collections.EMPTY_LIST,
                Collections.EMPTY_LIST,
                movieCount,
                tvCount,
                castCount,
                totalResultCount
            );
        }
    }
}
