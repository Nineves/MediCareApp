package com.example.medicare;

public class Clinic {

    private String clinicAddress;
    private String clinicContactNumber;
    private String clinicName;
    private String operatingHours;
    private String clinicWebsite;
    private String postalCode;
    private double averageRating;
    private double latitude;
    private double longitute;
    private double distance;

    public Clinic(){}

    public Clinic(String add, String cont, String name,String hours, String web, String post,
                  double rate, double lat, double log
                  ){
        this.clinicAddress=add;
        this.clinicContactNumber=cont;
        this.clinicName=name;
        this.operatingHours=hours;
        this.clinicWebsite=web;
        this.postalCode=post;
        this.averageRating=rate;
        this.latitude=lat;
        this.longitute=log;
        this.distance=0;
    }

    public String getClinicAddress(){ return clinicAddress;}

    public void setClinicAddress(String add){this.clinicAddress=add;}

    public String getClinicContactNumber(){ return clinicContactNumber;}

    public void setClinicContactNumber(String cont){this.clinicContactNumber=cont;}

    public String getClinicName(){return clinicName;}

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getOperatingHours() {
        return operatingHours;
    }

    public void setOperatingHours(String operatingHours) {
        this.operatingHours = operatingHours;
    }

    public String getClinicWebsite() {
        return clinicWebsite;
    }

    public void setClinicWebsite(String clinicWebsite) {
        this.clinicWebsite = clinicWebsite;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getLongitute() {
        return longitute;
    }

    public void setLongitute(double longitute) {
        this.longitute = longitute;
    }

}
