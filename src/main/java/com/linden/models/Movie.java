package com.linden.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.util.List;

@Entity // Tell persistence layer to make a table for this class
public class Movie extends Content{

    @NotNull
    private int rating;

    private Genre genre;

    @OneToMany
    private List<Review> reviews;

    private double revenue;

    private Time duration;

    public Movie(){}

    public Movie(@NotNull int rating, Genre genre, List<Review> reviews, double revenue, Time duration) {
        this.rating = rating;
        this.genre = genre;
        this.reviews = reviews;
        this.revenue = revenue;
        this.duration = duration;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
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

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


}
