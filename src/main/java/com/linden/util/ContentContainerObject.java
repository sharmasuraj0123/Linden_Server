package com.linden.util;

import com.linden.models.content.Cast;
import com.linden.models.content.ContentType;
import com.linden.models.content.Genre;
import com.linden.models.content.Review;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class ContentContainerObject implements Serializable {
    
    private long id;
    private String name;
    private String details;
    private Date releaseDate;
    private String videos;

    private double score;

    private double lindenMeter;

    private double boxOffice;

    private Set<String> photos;


    private Set<Cast> cast;

    private Set<Genre> genre;


    private List<Review> reviews;
    private String poster;


    private ContentType contentType;

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

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
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
}
