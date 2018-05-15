package com.linden.services;

import com.linden.models.content.*;
import com.linden.repositories.CastRepository;
import com.linden.repositories.TvShowRepository;
import com.linden.util.search.rank.ContentRanker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class TvShowService {

    @Autowired
    private TvShowRepository tvShowRepository;

    @Autowired
    private CastRepository castRepository;

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
        List<TvShow> shows = new ArrayList<>();
        Cast cast = castRepository.getCastById(castId);
        List<Cast> duplicates = castRepository.getCastsByFirstNameAndLastName(cast.getFirstName(),cast.getLastName());

        for(Cast tempCast: duplicates){
            List<TvShow> tempMovies = tvShowRepository.getTvShowsByCastId(tempCast.getId());
            shows.addAll(tempMovies);
        }

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

    public List<TvShow> getHighestRatedTvShows(){
        return tvShowRepository.getTvShowsByOrderByScore();
    }

    public List<TvShow> getFreshTvShows(){
        return  tvShowRepository.findFreshTvShows();
    }

    public void updateLindenmeterForTvShow( TvShow show){

        List<Review> reviews = show.getReviews();

        int criticReviewCount = 0;
        int sumOfLindenmeter = 0;

        int audienceReviewCount = 0;
        int sumOfScores = 0;

        for(Review review: reviews){
            if(review.getReviewType().equals(ReviewType.CRITIC) || review.getReviewType().equals(ReviewType.TOP_CRITIC)){
                criticReviewCount++;

                if(review.getRating() >= 3){
                    sumOfLindenmeter += 100;
                }else{
                    sumOfLindenmeter += 0;
                }

            }else{
                audienceReviewCount++;

                if(review.getRating() >= 3){
                    sumOfScores += 100;
                }else{
                    sumOfScores += 0;
                }
            }
        }

        show.setLindenMeter((sumOfLindenmeter/criticReviewCount));
        show.setScore((sumOfScores/audienceReviewCount));

    }


}
