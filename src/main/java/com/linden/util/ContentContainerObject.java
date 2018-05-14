package com.linden.util;

import com.linden.models.content.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class ContentContainerObject implements Serializable {
    
    private long id;
    private String name;
    private String details;
    private Date releaseDate;
    private List<String> videos;

    private double score;

    private double lindenMeter;

    private double boxOffice;

    private Set<String> photos;


    private Set<Cast> cast;

    private Set<Genre> genre;


    private List<Review> reviews;
    private String poster;


    private ContentType contentType;

    // Movie Fields

    private double revenue;

    private long duration;

    private boolean isFeatured;

    private boolean isAcademyWinner;

    // TV Fields

    private List<Season> seasons;

    private int numberOfSeasons;

    public ContentContainerObject() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<String> getVideos() {
        return videos;
    }

    public void setVideos(List<String> videos) {
        this.videos = videos;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getLindenMeter() {
        return lindenMeter;
    }

    public void setLindenMeter(double lindenMeter) {
        this.lindenMeter = lindenMeter;
    }

    public double getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(double boxOffice) {
        this.boxOffice = boxOffice;
    }

    public Set<String> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<String> photos) {
        this.photos = photos;
    }

    public Set<Cast> getCast() {
        return cast;
    }

    public void setCast(Set<Cast> cast) {
        this.cast = cast;
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

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
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

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }
}
