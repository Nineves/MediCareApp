package com.example.medicare.user;

public class User {
    private String email;
    private String username;
    private int profilePicChoice;

    public User(String email, String username, int profilePicChoice) {
        this.email = email;
        this.username = username;
        this.profilePicChoice = profilePicChoice;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getProfilePicChoice() {
        return profilePicChoice;
    }

    public void setProfilePicChoice(int profilePicChoice) {
        this.profilePicChoice = profilePicChoice;
    }
}
