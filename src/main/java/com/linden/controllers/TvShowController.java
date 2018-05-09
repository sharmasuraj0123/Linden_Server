package com.linden.controllers;

import com.linden.models.content.Episode;
import com.linden.models.content.Season;
import com.linden.models.content.TvShow;
import com.linden.services.TvShowService;
import com.linden.util.search.EpisodeResult;
import com.linden.util.search.SeasonResult;
import com.linden.util.search.TvShowResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tvShow")
public class TvShowController {

    @Autowired
    private TvShowService tvShowService;

    @RequestMapping(value = "/{tvShowId}", method = RequestMethod.GET)
    @ResponseBody
    public TvShowResult getTvShow(@PathVariable(value = "tvShowId") String tvShowId){

        Long id = Long.parseLong(tvShowId);
        TvShow show = tvShowService.getTvShow(id);
        TvShowResult result = new TvShowResult(show);

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


}
