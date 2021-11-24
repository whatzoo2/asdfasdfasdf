package com.gsh91.asdfasdfasdf;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RetrofitService {

    @Headers("Authorization: KakaoAK faf9110c878f1267aaf4aaea857762a2")
    @GET("/v2/local/search/keyword.json")
    Call<String> searchPlaceByString(@Query("query") String query, @Query("x") String longitude, @Query("y") String latitude);


    @Headers("Authorization: KakaoAK faf9110c878f1267aaf4aaea857762a2")
    @GET("/v2/local/search/keyword.json")
    Call<SearchLocalApiResponse> searchPlace(@Query("query") String query, @Query("x") String longitude, @Query("y") String latitude);
}
