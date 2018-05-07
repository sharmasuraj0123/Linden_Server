package com.linden.models;

import javax.persistence.*;
import java.sql.Time;

@Entity
public class Showtime {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    protected long id;

    @OneToOne
    protected Movie movie;

    @OneToOne
    protected Theatre theatre;

    protected Time startTime;

    public Showtime showtime;

    public Showtime(Movie movie, Theatre theatre, Time startTime) {
        this.movie = movie;
        this.theatre = theatre;
        this.startTime = startTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public void setTheatre(Theatre theatre) {
        this.theatre = theatre;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
}
