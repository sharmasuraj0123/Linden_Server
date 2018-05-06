package com.linden.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Season {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    protected long id;

    protected int seasonNumber;

    protected int numberOfEpisodes;

    protected Date releaseDate;

    protected String poster;

    @OneToMany
    protected List<Episode> episodes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
