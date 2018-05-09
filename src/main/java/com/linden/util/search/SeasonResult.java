package com.linden.util.search;

import com.linden.models.content.Season;

import java.io.Serializable;

public class SeasonResult implements Serializable{

    private Season season;

    public SeasonResult(){};

    public SeasonResult(Season season) {
        this.season = season;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }
}
