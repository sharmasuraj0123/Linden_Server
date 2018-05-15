package com.linden.controllers;

import com.linden.models.accounts.Account;
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
    public SearchResponse getFeaturedMoviesRoute() {
        return getFeaturedMoviesRoute(1);
    }

    @RequestMapping(
            value = "/featured",
            params = {"page"},
            method = RequestMethod.GET
    )
    @ResponseBody
    public SearchResponse getFeaturedMoviesRoute(int page) {
        List<Movie> movies =  movieService.getFeaturedMovies();

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
            value = "/openingThisWeek",
            method = RequestMethod.GET
    )
    @ResponseBody
    public SearchResponse getOpeningThisWeek() {
        return getOpeningThisWeek(1);
    }


    @RequestMapping(
            value = "/openingThisWeek",
            params = {"page"},
            method = RequestMethod.GET
    )
    @ResponseBody
    public SearchResponse getOpeningThisWeek(int page) {
        List<Movie> movies = (List)movieService.getOpeningThisWeek(RESULT_LIMIT);

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
        HashMap<String, Serializable> response = new HashMap<>();
        if(request.getHeader("token") != null && !request.getHeader("token").equalsIgnoreCase("null")) {
            Account account = accountTokenService.getAccount(request.getHeader("token"));
            if(account instanceof User) {
                User user = (User) account;
                response.put("isWantToSee", user.getWantsToSee().stream().anyMatch(
                        wantsToSee -> (wantsToSee.getContentId() == movieId) && (wantsToSee.getContentType() == ContentType.MOVIE))
                );
                response.put("isNotInterested", user.getNotInterested().stream().anyMatch(
                        notInterested -> (notInterested.getContentId() == movieId) && (notInterested.getContentType() == ContentType.MOVIE))
                );
            }
        }
        response.put("movie", movie);
        return response;
    }

    @RequestMapping(
            value = "/getComingSoon"
    )
    @ResponseBody
    public SearchResponse getComingSoon(){
        return getComingSoon(1);
    }

    @RequestMapping(
            value = "/getComingSoon",
            params = {"page"},
            method = RequestMethod.GET
    )
    @ResponseBody
    public SearchResponse getComingSoon(int page){
        Date todaysDate = java.sql.Date.valueOf(java.time.LocalDate.now());
        List<Movie> movies = movieService.getUpcomingMovies(todaysDate);

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

    @RequestMapping(
            value = "/getLindenTopPicks",
            method = RequestMethod.GET
    )
    @ResponseBody
    public HashMap<String, ?> getLindenTopPicks(){
        HashMap<String, List<Movie>> result = new HashMap<>();
        result.put("topPicks", movieService.getLindenTopPicks());
        return result;
    }
}