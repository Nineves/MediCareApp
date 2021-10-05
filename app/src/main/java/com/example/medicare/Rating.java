package com.example.medicare;

public class Rating {
    private double rating;
    private String user_id;
    private long timestamp;

    public Rating(){}

    public Rating(double rating,String user_id,long timestamp){
        this.rating=rating;
        this.user_id=user_id;
        this.timestamp=timestamp;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
