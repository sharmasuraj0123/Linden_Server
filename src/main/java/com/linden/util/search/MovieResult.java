package com.linden.util.search;

import com.linden.models.content.Cast;
import com.linden.models.content.Genre;
import com.linden.models.content.Movie;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class MovieResult implements Serializable {
    private long contentId;
    private String name;
    private Date releaseDate;
    private String details;
    private Set<Genre> genre;
    private Set<Cast> cast;
    private double score;
    private double lindenMeter;

    public MovieResult(){

    }

    public MovieResult(long contentId, String name, Date releaseDate, String details,
                       Set<Genre> genre, Set<Cast> cast, double score) {
        this.contentId = contentId;
        this.name = name;
        this.releaseDate = releaseDate;
        this.details = details;
        this.genre = genre;
        this.cast = cast;
        this.score = score;
    }

    public MovieResult(Movie movie){
        this.contentId = movie.getId();
        this.name = movie.getName();
        this.releaseDate = movie.getReleaseDate();
        this.details = movie.getDetails();
        this.genre = movie.getGenre();
        this.cast = movie.getCast();
        this.score = movie.getScore();
    }

    public long getContentId() {
        return contentId;
    }

    public void setContentId(long contentId) {
        this.contentId = contentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Set<Genre> getGenre() {
        return genre;
    }

    public void setGenre(Set<Genre> genre) {
        this.genre = genre;
    }

    public Set<Cast> getCast() {
        return cast;
    }

    public void setCast(Set<Cast> cast) {
        this.cast = cast;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
