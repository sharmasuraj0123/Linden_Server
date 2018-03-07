package models;

public class Movie {
    private String name;
    private int rating;
    private String imageURL;

    public Movie(String name, int rating, String imageURL) {
        this.name = name;
        this.rating = rating;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
