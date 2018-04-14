package com.linden.services;

import com.linden.models.TvShow;
import com.linden.repositories.TvShowRepository;
import com.linden.util.search.rank.ContentRanker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TvShowService {

    @Autowired
    private TvShowRepository tvShowRepository;

    public List<TvShow> searchTvShow(String keywords) {
        return searchTvShow(keywords, new ContentRanker<>());
    }

    public List<TvShow> searchTvShow(String keywords, ContentRanker<TvShow> ranker){
        List<TvShow> tvShows = tvShowRepository.findTvShowByNameContains(keywords);
        // Return sorted list based on ranker supplied
        return ranker.order(tvShows);
    }
}
