package com.example.wallhome;

import com.example.wallhome.Interface.ApiInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtilities {

    private static Retrofit retrofit=null;
    public static final String API="gI7CeXrgrgoCrDR8B9db23icK1ZdbdsTNe3EUxUR2wEw0Qa1uoE1a8kS";

    public static ApiInterface getApiInterface()
    {

        if (retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(ApiInterface.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(ApiInterface.class);


    }
}
