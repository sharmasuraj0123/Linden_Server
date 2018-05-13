package com.linden.models.content;

import com.linden.models.accounts.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class ReviewReport implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Date date;

    @OneToOne
    private Review review;

    @OneToOne
    private User reportedBy;

    private String justification;

    private String token;

    public ReviewReport(){}

    public ReviewReport(Date date, Review review, User reportedBy, String justification){
        this.date = date;
        this.reportedBy = reportedBy;
        this.review = review;
        this.justification = justification;
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

    public User getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(User reportedBy) {
        this.reportedBy = reportedBy;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
