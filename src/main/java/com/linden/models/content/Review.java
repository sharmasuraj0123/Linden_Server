package com.linden.models.content;

import com.linden.models.accounts.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * {
 *     "contentId" : 1093092,
 *     "contentType" : "MOVIE" or "TVSHOW",
 *     "rating" : 1-5,
 *     "details" : ""
 *
 * }
 */

@Entity
public class Review implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private Date date;

    @OneToOne
    @NotNull
    private User postedBy;

    private long contentId;

    @NotNull
    private ContentType contentType;

    private String details;

    private int rating;

    @Enumerated(EnumType.STRING)
    private ReviewType reviewType;

    private String accountToken;

    public Review(){}

    public Review(Date date, @NotNull User postedBy, @NotNull ContentType contentType, long contentId, String details,
                  int rating) {
        this.date = date;
        this.postedBy = postedBy;
        this.details = details;
        this.rating = rating;
        this.contentType = contentType;
        this.contentId = contentId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User postedBy) {
        this.postedBy = postedBy;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public ReviewType getReviewType() {
        return reviewType;
    }

    public void setReviewType(ReviewType reviewType) {
        this.reviewType = reviewType;
    }

    public String getAccountToken() {
        return accountToken;
    }

    public void setAccountToken(String accountToken) {
        this.accountToken = accountToken;
    }
}
