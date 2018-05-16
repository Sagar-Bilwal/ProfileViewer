package com.example.sagar.pascolan.responses;

import com.example.sagar.pascolan.model.Profile;

import java.util.ArrayList;

public class UserResponse
{
    private ArrayList<Profile> SampleUsers;

    public ArrayList<Profile> getSampleUsers() {
        return SampleUsers;
    }

    public void setSampleUsers(ArrayList<Profile> sampleUsers) {
        SampleUsers = sampleUsers;
    }
}
