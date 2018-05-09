package com.linden.util.search;

import com.linden.models.content.TvShow;

import java.io.Serializable;

public class TvShowResult implements Serializable {

    private TvShow tvShow;

    public TvShowResult(TvShow tvShow){
        this.tvShow = tvShow;
    }

    public TvShow getTvShow() {
        return tvShow;
    }

    public void setTvShow(TvShow tvShow) {
        this.tvShow = tvShow;
    }
}
