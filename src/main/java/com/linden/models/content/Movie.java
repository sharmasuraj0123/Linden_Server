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

    public boolean isTopBoxOffice;

    @Enumerated(EnumType.STRING)
    public MovieType movieType = MovieType.DEFAULT;

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
