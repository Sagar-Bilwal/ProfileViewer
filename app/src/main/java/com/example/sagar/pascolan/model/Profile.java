package com.example.sagar.pascolan.model;

import com.google.gson.annotations.SerializedName;

public class Profile
{
    private String name;
    @SerializedName("image")
    private String userImage;
    private String verified;
    private String priority;
    public Profile()
    {

    }
    public Profile(String name, String userImage, String verified, String priority) {
        this.name = name;
        this.userImage = userImage;
        this.verified = verified;
        this.priority = priority;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
