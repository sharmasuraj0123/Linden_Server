package com.linden.models.content;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Cast implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private int rating;

    private String Gender;

    private String imageURL;

    public Cast(){}

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Cast) {
            Cast cast = (Cast) obj;
            return this.firstName.equalsIgnoreCase(cast.firstName) && this.lastName.equalsIgnoreCase(cast.lastName);
        }
        else {
            return false;
        }
    }

    public Cast(@NotNull String firstName, @NotNull String lastName, int rating, String gender, String imageURL) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.rating = rating;
        Gender = gender;
        this.imageURL = imageURL;
    }
}
