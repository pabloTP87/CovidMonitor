package com.covid.covidmonitor.Interface;

import com.covid.covidmonitor.Model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceholderApi {

    @GET("posts") // annotation
    // método con una llamada de retrofit
    Call<List<Post>> getPost();
}
