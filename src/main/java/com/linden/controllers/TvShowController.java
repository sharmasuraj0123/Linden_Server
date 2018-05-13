package com.linden.controllers;

import com.linden.models.accounts.User;
import com.linden.models.content.*;
import com.linden.services.AccountTokenService;
import com.linden.services.TvShowService;
import com.linden.util.search.EpisodeResult;
import com.linden.util.search.SearchResponse;
import com.linden.util.search.SeasonResult;
import com.linden.util.search.TvShowResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/tvShow")
public class TvShowController {

    @Autowired
    private TvShowService tvShowService;

    @Autowired
    private AccountTokenService accountTokenService;

    @Value("${tvShow.result.limit:10}")
    private int RESULT_LIMIT;

    @RequestMapping(value = "/{tvShowId}", method = RequestMethod.GET)
    @ResponseBody
    public TvShowResult getTvShow(@PathVariable(value = "tvShowId") String tvShowId, HttpServletRequest request, HttpServletResponse response){

        Long id = Long.parseLong(tvShowId);
        TvShow show = tvShowService.getTvShow(id);
        TvShowResult result = new TvShowResult(show);
        if(request.getHeader("token") != null) {
            User user = (User) accountTokenService.getAccount(request.getHeader("token"));
            response.setHeader("isWatchList", Boolean.valueOf(user.getWantsToSee().stream().anyMatch(
                    userWantsToSee -> (userWantsToSee.getContentId() == id) && (userWantsToSee.getContentType() == ContentType.TVSHOW))
            ).toString());
        }
        return result;
    }

    @RequestMapping(value = "/{tvShowId}/season/{seasonNumber}", method = RequestMethod.GET)
    @ResponseBody
    public SeasonResult getSeason(@PathVariable(value = "tvShowId") String tvShowId,@PathVariable(value = "seasonNumber") String seasonNumber){

        long id = Long.parseLong(tvShowId);
        int seasonNum = Integer.parseInt(seasonNumber);

        Season season = tvShowService.getSeasonByNumber(id,seasonNum);

        SeasonResult result = new SeasonResult(season);

        return result;
    }

    @RequestMapping(value = "/{tvShowId}/season/{seasonId}/episode/{episodeNumber}", method = RequestMethod.GET)
    @ResponseBody
    public EpisodeResult getEpisode(@PathVariable(value = "tvShowId") String tvShowId,@PathVariable(value = "seasonId") String seasonNumber, @PathVariable(value = "episodeNumber") String episodeNumber){

        long id = Long.parseLong(tvShowId);
        int seasonNum = Integer.parseInt(seasonNumber);
        int episodeNum = Integer.parseInt(episodeNumber);

        Episode episode = tvShowService.getEpisodeByNumber(id,seasonNum,episodeNum);

        EpisodeResult result = new EpisodeResult(episode);

        return result;
    }

    @RequestMapping(
            value = "/getHighestRatedMovies",
            method = RequestMethod.GET
    )
    @ResponseBody
    public SearchResponse getHighestRatedTvShows() {
        return getHighestRatedTvShows(1);
    }

    @RequestMapping(
            value = "/getHighestRatedTvShows",
            params = {"page"},
            method = RequestMethod.GET
    )
    @ResponseBody
    public SearchResponse getHighestRatedTvShows(int page) {
        List<TvShow> tvShows = tvShowService.getHighestRatedTvShows();
        tvShows = (tvShows == null) ? Collections.EMPTY_LIST : tvShows;
        if (page > 0) {
            if ((page - 1) * RESULT_LIMIT <= tvShows.size()) {
                tvShows = tvShows.subList(
                        (page - 1) * RESULT_LIMIT,
                        (page * RESULT_LIMIT > tvShows.size()) ?
                                tvShows.size() : page * RESULT_LIMIT
                );
            }
        }
        return new SearchResponse(
                Collections.EMPTY_LIST,
                tvShows,
                Collections.EMPTY_LIST,
                0,
                tvShows.size(),
                0,
                tvShows.size()
        );
    }

    @RequestMapping(
            value = "/getFreshTvShows",
            method = RequestMethod.GET
    )
    @ResponseBody
    public SearchResponse getFreshTvShows(){
        return getFreshTvShows(1);
    }

    @RequestMapping(
            value = "/getFreshTvShows",
            params = {"page"},
            method = RequestMethod.GET
    )
    @ResponseBody
    public SearchResponse getFreshTvShows(int page) {
        List<TvShow> tvShows = tvShowService.getFreshTvShows();
        tvShows = (tvShows == null) ? Collections.EMPTY_LIST : tvShows;

        if (page > 0) {
            if ((page - 1) * RESULT_LIMIT <= tvShows.size()) {
                tvShows = tvShows.subList(
                        (page - 1) * RESULT_LIMIT,
                        (page * RESULT_LIMIT > tvShows.size()) ?
                                tvShows.size() : page * RESULT_LIMIT
                );
            }
        }

        return new SearchResponse(
                Collections.EMPTY_LIST,
                tvShows,
                Collections.EMPTY_LIST,
                0,
                tvShows.size(),
                0,
                tvShows.size()
        );
    }

}
