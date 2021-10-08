package com.example.medicare;

public class Clinic {

    private String access_code;
    private String address;
    private String contact_number;
    private Long distance;
    private double latitude;
    private double longitude;
    private String name;
    private String opening_hours;
    private String rating;
    private String website;

    public Clinic(){

    }

    public Clinic(String access_code, String address, String contact_number, Long distance, double latitude, double longitude, String name, String opening_hours, String rating, String website) {
        this.access_code = access_code;
        this.address = address;
        this.contact_number = contact_number;
        this.distance = distance;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.opening_hours = opening_hours;
        this.rating = rating;
        this.website = website;
    }

    public String getAccess_code() {
        return access_code;
    }

    public void setAccess_code(String access_code) {
        this.access_code = access_code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpening_hours() {
        return opening_hours;
    }

    public void setOpening_hours(String opening_hours) {
        this.opening_hours = opening_hours;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
