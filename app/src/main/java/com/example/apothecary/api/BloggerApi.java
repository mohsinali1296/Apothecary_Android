package com.example.apothecary.api;

import com.example.apothecary.models.articles.Articles;
import com.example.apothecary.models.articles.Articles;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface BloggerApi {
    final static String KEY = "AIzaSyB_iuyunEB_kl3DNCPcwrRK3OR6WZpvFwQ";

    @GET
    Call<Articles> getAllArticles(@Url String url);

    @GET("(postId)/?key="+ KEY)
    Call<Articles> getArticlesById( @Path("postId") String id);


}
