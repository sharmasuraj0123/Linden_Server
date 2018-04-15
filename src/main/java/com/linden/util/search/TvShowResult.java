package com.linden.util.search;

import com.linden.models.TvShow;

import java.io.Serializable;

public class TvShowResult implements Serializable {

    private TvShow tvShow;

    public TvShowResult(TvShow tvShow){
        this.tvShow = tvShow;
    }

}
