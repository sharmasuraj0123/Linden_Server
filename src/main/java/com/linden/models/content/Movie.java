package com.linden.models.content;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
public class Movie extends Content implements Serializable{

    private double revenue;

    private long duration;

    private boolean isFeatured;

    public boolean isAcademyWinner;

    @Enumerated(EnumType.STRING)
    public MovieType movieType = MovieType.DEFAULT;

    {
        this.contentType = ContentType.MOVIE;
    }

    public Movie(){
        this.contentType = ContentType.MOVIE;
    }

    public Movie(double score, Set<Genre> genre, List<Review> reviews, double revenue, long duration) {
        this.score = score;
        this.genre = genre;
        this.reviews = reviews;
        this.revenue = revenue;
        this.duration = duration;
        this.contentType = ContentType.MOVIE;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }

    public boolean isAcademyWinner() {
        return isAcademyWinner;
    }

    public void setAcademyWinner(boolean academyWinner) {
        isAcademyWinner = academyWinner;
    }

    public MovieType getMovieType() {
        return movieType;
    }

    public void setMovieType(MovieType movieType) {
        this.movieType = movieType;
    }

    public Set<Genre> getGenre() {
        return genre;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

}
