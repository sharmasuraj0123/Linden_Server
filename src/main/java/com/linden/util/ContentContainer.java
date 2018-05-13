package com.linden.util;

import com.linden.models.content.Content;
import com.linden.models.content.Movie;
import com.linden.models.content.TvShow;

public class ContentContainer {

    private String token;

    private ContentContainerObject obj;

    public ContentContainer() {

    }

    public ContentContainer(String token, Content content) {
        this.token = token;
        this.obj = new ContentContainerObject();
        if (content != null) {
            this.obj.setId(content.getId());
            this.obj.setBoxOffice(content.getBoxOffice());
            this.obj.setCast(content.getCast());
            this.obj.setContentType(content.getContentType());
            this.obj.setDetails(content.getDetails());
            this.obj.setGenre(content.getGenre());
            this.obj.setLindenMeter(content.getLindenMeter());
            this.obj.setName(content.getName());
            this.obj.setPhotos(content.getPhotos());
            this.obj.setPoster(content.getPoster());
            this.obj.setReleaseDate(content.getReleaseDate());
            this.obj.setReviews(content.getReviews());
            this.obj.setScore(content.getScore());
            this.obj.setVideos(content.getVideos());
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ContentContainerObject getObj() {
        return obj;
    }

    public void setObj(ContentContainerObject obj) {
        this.obj = obj;
    }

    public Content getContent() {
        Content content = null;
        if(obj != null) {
            switch (obj.getContentType()){
                default:
                case MOVIE:
                    content = new Movie();
                    break;
                case TVSHOW:
                    content = new TvShow();
                    break;
            }
            content.setId(this.obj.getId());
            content.setBoxOffice(this.obj.getBoxOffice());
            content.setCast(this.obj.getCast());
            content.setContentType(this.obj.getContentType());
            content.setDetails(this.obj.getDetails());
            content.setGenre(this.obj.getGenre());
            content.setLindenMeter(this.obj.getLindenMeter());
            content.setName(this.obj.getName());
            content.setPhotos(this.obj.getPhotos());
            content.setPoster(this.obj.getPoster());
            content.setReleaseDate(this.obj.getReleaseDate());
            content.setReviews(this.obj.getReviews());
            content.setScore(this.obj.getScore());
            content.setVideos(this.obj.getVideos());
        }
        return content;
    }

    public void setContent(Content content) {
        this.obj = new ContentContainerObject();
        if (content != null) {
            this.obj.setId(content.getId());
            this.obj.setBoxOffice(content.getBoxOffice());
            this.obj.setCast(content.getCast());
            this.obj.setContentType(content.getContentType());
            this.obj.setDetails(content.getDetails());
            this.obj.setGenre(content.getGenre());
            this.obj.setLindenMeter(content.getLindenMeter());
            this.obj.setName(content.getName());
            this.obj.setPhotos(content.getPhotos());
            this.obj.setPoster(content.getPoster());
            this.obj.setReleaseDate(content.getReleaseDate());
            this.obj.setReviews(content.getReviews());
            this.obj.setScore(content.getScore());
            this.obj.setVideos(content.getVideos());
        }
    }
}
