package com.linden.util.search;

import com.linden.models.content.Episode;

import java.io.Serializable;

public class EpisodeResult implements Serializable {

    private Episode episode;

    public EpisodeResult(){}

    public EpisodeResult(Episode episode) {
        this.episode = episode;
    }

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }
}
