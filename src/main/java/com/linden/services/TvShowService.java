package com.linden.services;

import com.linden.models.content.Episode;
import com.linden.models.content.Season;
import com.linden.models.content.TvShow;
import com.linden.repositories.TvShowRepository;
import com.linden.util.search.rank.ContentRanker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class TvShowService {

    @Autowired
    private TvShowRepository tvShowRepository;

    public List<TvShow> searchTvShow(String keywords){
        return searchTvShow(keywords, new ContentRanker<>(), true);
    }

    public List<TvShow> searchTvShow(String keywords,
                                     Function<TvShow, ? extends Comparable> pairingFunction){
        return searchTvShow(keywords, new ContentRanker<>(pairingFunction), true);
    }

    public List<TvShow> searchTvShow(String keywords,
                                     Function<TvShow, ? extends Comparable> pairingFunction,
                                     boolean desc){
        return searchTvShow(keywords, new ContentRanker<>(pairingFunction), desc);
    }

    public List<TvShow> searchTvShow(String keywords,
                                     ContentRanker<TvShow> ranker,
                                     boolean desc){
        List<TvShow> TvShows = tvShowRepository.findTvShowsByNameContains(keywords);
        // Return sorted list based on ranker supplied
        return  ranker.order(TvShows, desc);
    }

    public List<TvShow> getTvShowsByCastId(long castId){
        List<TvShow> shows = tvShowRepository.getTvShowsByCastId(castId);
        return shows;
    }

    public TvShow getTvShow(long id){
        TvShow tvShow = tvShowRepository.getTvShowById(id);
        return tvShow;
    }

    public Season getSeasonByNumber(long tvShowId, int seasonNumber){
        TvShow tvShow = tvShowRepository.getTvShowById(tvShowId);
        List<Season> seasons = tvShow.getSeasons();
        Season result = new Season();
        for(Season season : seasons){
            if(season.getSeasonNumber() == seasonNumber){
                result = season;
                return result;
            }
        }

        return result;
    }

    public Episode getEpisodeByNumber(long tvShowId, int seasonNumber, int episodeNumber){

        TvShow tvShow = tvShowRepository.getTvShowById(tvShowId);
        List<Season> seasons = tvShow.getSeasons();

        Episode result = new Episode();
        for(Season season : seasons){
            if(season.getSeasonNumber() == seasonNumber){

                List<Episode> episodes = season.getEpisodes();
                for(Episode episode: episodes){
                    if(episode.getEpisodeNumber() ==  episodeNumber){
                        result = episode;
                        return result;
                    }
                }
            }
        }

        return result;

    }
}
