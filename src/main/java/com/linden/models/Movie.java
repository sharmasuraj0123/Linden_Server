package com.linden.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Movie extends Content{

    private double revenue;

    private long duration;

    public Movie(){}

    public Movie(double score, Set<Genre> genre, List<Review> reviews, double revenue, long duration) {
        this.score = score;
        this.genre = genre;
        this.reviews = reviews;
        this.revenue = revenue;
        this.duration = duration;
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
