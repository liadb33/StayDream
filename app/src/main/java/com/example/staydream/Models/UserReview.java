package com.example.staydream.Models;

public class UserReview {

    private String userNickname;
    private String description;
    private String userPhotoUrl;


    public UserReview(){

    }
    public UserReview(String userNickname, String description,String userPhotoUrl) {
        this.userNickname = userNickname;
        this.description = description;
        this.userPhotoUrl = userPhotoUrl;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public UserReview setUserNickname(String userNickname) {
        this.userNickname = userNickname;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public UserReview setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public UserReview setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
        return this;
    }
}
