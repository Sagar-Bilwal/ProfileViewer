package com.example.sagar.pascolan;

import com.example.sagar.pascolan.responses.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface PascolanApi
{
    @GET
    Call<UserResponse> getProfiles(@Url String url);
}
