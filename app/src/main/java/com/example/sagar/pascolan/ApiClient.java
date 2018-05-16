package com.example.sagar.pascolan;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient
{
    private static ApiClient INSTANCE;
    private PascolanApi pascolanApi;
    private ApiClient()
    {
        Retrofit retrofit= new Retrofit.Builder().baseUrl("http://pascolan.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        pascolanApi = retrofit.create(PascolanApi.class);
    }
    public static ApiClient getInstance() {
        if(INSTANCE == null){
            INSTANCE = new ApiClient();
        }
        return INSTANCE;
    }

    public PascolanApi getPascolanApi() {
        return pascolanApi;
    }
}
