package com.linden.models.accounts;

import com.linden.models.content.ContentType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class UserWantsToSee implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    private long contentId;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    public UserWantsToSee() {

    }

    public UserWantsToSee(long contentId, ContentType contentType) {
        this.contentId = contentId;
        this.contentType = contentType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getContentId() {
        return contentId;
    }

    public void setContentId(long contentId) {
        this.contentId = contentId;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }
}
