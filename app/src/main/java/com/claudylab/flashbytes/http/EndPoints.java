package com.claudylab.flashbytes.http;

import com.claudylab.flashbytes.models.sources.Sources;
import com.claudylab.flashbytes.models.topnews.Datum;
import com.claudylab.flashbytes.models.topnews.TopNews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EndPoints {

    @GET("top")
    Call<TopNews> getTopNews(@Query("api_token") String token, @Query("language") String language, @Query("page") int page);

    @GET("all")
    Call<TopNews> getAllNews(@Query("api_token") String token, @Query("language") String language, @Query("categories") String categories, @Query("page") int limit);


    @GET("sources")
    Call<Sources> getSources(@Query("api_token") String token, @Query("language") String language);

    @GET("all")
    Call<TopNews> searchNews(@Query("api_token") String token, @Query("language") String language, @Query("search") String query, @Query("page") int limit);


}

