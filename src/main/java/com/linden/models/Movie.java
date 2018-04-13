package com.linden.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity // Tell persistence layer to make a table for this class
public class Movie extends Content{

    @NotNull
    private int rating;

    @ElementCollection(targetClass=Genre.class)
    @Enumerated(EnumType.STRING)
    private Set<Genre> genre;

    @OneToMany
    private List<Review> reviews;

    private double revenue;

    private long duration;

    public Movie(){}

    public Movie(@NotNull int rating, Set<Genre> genre, List<Review> reviews, double revenue, long duration) {
        this.rating = rating;
        this.genre = genre;
        this.reviews = reviews;
        this.revenue = revenue;
        this.duration = duration;
    }

    public Set<Genre> getGenre() {
        return genre;
    }

    public void setGenre(Set<Genre> genre) {
        this.genre = genre;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


}
