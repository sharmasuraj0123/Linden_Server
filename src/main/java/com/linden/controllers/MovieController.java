package com.linden.controllers;

import com.linden.models.accounts.User;
import com.linden.models.content.ContentType;
import com.linden.models.content.Movie;
import com.linden.services.AccountTokenService;
import com.linden.services.MovieService;
import com.linden.util.search.MovieResult;
import com.linden.util.search.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;

@RestController
@RequestMapping(value = "/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private AccountTokenService accountTokenService;

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
    public HashMap<String, Serializable> getMovie(@PathVariable(value = "movieId") long movieId, HttpServletRequest request){
        Movie movie = movieService.getMovie(movieId);
        MovieResult result = new MovieResult(movie);
        HashMap<String, Serializable> response = new HashMap<>();
        if(request.getHeader("token") != null) {
            User user = (User) accountTokenService.getAccount(request.getHeader("token"));
            response.put("isWantToSee", user.getWantsToSee().stream().anyMatch(
                wantsToSee -> (wantsToSee.getContentId() == movieId) && (wantsToSee.getContentType() == ContentType.MOVIE))
            );
            response.put("isNotInterested", user.getNotInterested().stream().anyMatch(
                notInterested -> (notInterested.getContentId() == movieId) && (notInterested.getContentType() == ContentType.MOVIE))
            );
        }
        response.put("movie", result);
        return response;
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

    @RequestMapping(
            value = "/getTopBoxOffice",
            method = RequestMethod.GET
    )
    @ResponseBody
    public SearchResponse getTopBoxOffice(){
        return getTopBoxOffice(1);
    }

    @RequestMapping(
            value = "/getTopBoxOffice",
            params = {"page"},
            method = RequestMethod.GET
    )
    @ResponseBody
    public SearchResponse getTopBoxOffice(int page) {
        List<Movie> movies = movieService.getTopBoxOffice();
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
            value = "/getAcademyAwardWinners",
            method = RequestMethod.GET
    )
    @ResponseBody
    public SearchResponse getAcademyAwardWinners(){
        return getAcademyAwardWinners(1);
    }

    @RequestMapping(
            value = "/getAcademyAwardWinners",
            params = {"page"},
            method = RequestMethod.GET
    )
    @ResponseBody
    public SearchResponse getAcademyAwardWinners(int page) {
        List<Movie> movies = movieService.getAcademyAwardWinner();
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


}