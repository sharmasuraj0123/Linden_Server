package com.linden.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@MappedSuperclass
public abstract class Content {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    protected long id;
    protected String name;
    protected String details;
    protected Date releaseDate;
    protected String videos;
    protected double score;
    @ElementCollection
    @CollectionTable(
        name="content_photos",
        joinColumns = @JoinColumn(
            name = "content_id", referencedColumnName = "id"
        )
    )
    @Column(name = "photos")
    protected Set<String> photos;
    @OneToMany
    protected Set<Cast> cast;
    @ElementCollection(targetClass=Genre.class)
    @Enumerated(EnumType.STRING)
    protected Set<Genre> genre;
    @OneToMany
    protected List<Review> reviews;
    protected String poster;

    public Content(){

    }

    public Content(String name, String details, Date releaseDate, double score,
                   Set<Cast> cast, Set<Genre> genre) {
        this.name = name;
        this.details = details;
        this.releaseDate = releaseDate;
        this.score = score;
        this.cast = cast;
        this.genre = genre;
    }



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
}
