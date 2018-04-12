package com.linden.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@MappedSuperclass
public abstract class Content {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @NotNull
    private String id;

    private String name;

    private String details;

    private Date releaseDate;

    private String videos;

    @ElementCollection
    @CollectionTable(name="content_photos", joinColumns = @JoinColumn(name = "content_id"))
    @Column(name = "photos")
    private List<String> photos;

    @OneToMany
    private List<Cast> cast;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }
}
