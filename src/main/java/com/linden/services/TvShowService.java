package com.linden.services;

import com.linden.models.TvShow;
import com.linden.repositories.TvShowRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TvShowService {

    @Autowired
    private TvShowRepository tvShowRepository;

    public List<TvShow> searchTvShow(String keywords){

        List<TvShow> tvShows = tvShowRepository.findTvShowByNameContains(keywords);

        return tvShows;
    }
}
