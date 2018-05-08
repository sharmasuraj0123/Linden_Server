package com.linden.models.content;

import com.linden.models.accounts.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private Date date;

    @OneToOne
    @NotNull
    private User postedBy;

    @OneToOne
    @NotNull
    private Content item;

    private String details;

    private int rating;

    @Enumerated(EnumType.STRING)
    private ReviewType reviewType;

    public Review(){}

    public Review(Date date, @NotNull User postedBy, @NotNull Content item, String details, int rating) {
        this.date = date;
        this.postedBy = postedBy;
        this.details = details;
        this.rating = rating;
        this.item = item;
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

    public Content getItem() {
        return item;
    }

    public void setItem(Content item) {
        this.item = item;
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
}
