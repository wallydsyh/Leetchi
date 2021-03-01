package com.example.leetchi_wallyd.api

import com.example.leetchi_wallyd.model.GiphyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("trending")
    suspend fun getGifs(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int
    ): GiphyResponse

    @GET("search")
    suspend fun searchGif(
        @Query("api_key") apiKey: String,
        @Query("q") query: String,
        @Query("limit") limit: Int
    ): GiphyResponse


}