package com.linden.models;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@MappedSuperclass
public abstract class Content {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String name;

    private String details;

    private Date releaseDate;

    private String videos;

    @ElementCollection
    @CollectionTable(name="content_photos", joinColumns = @JoinColumn(name = "content_id"))
    @Column(name = "photos")
    private Set<String> photos;

    @OneToMany
    private Set<Cast> cast;

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

    public String getvideos() {
        return videos;
    }

    public void setvideos(String videos) {
        this.videos = videos;
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
}
