package com.example.leetchi_wallyd.api

import com.example.leetchi_wallyd.model.GiphyResponse


open class ApiServiceImpl(
    private val apiService: ApiService = ApiClient().getClient()
) : ApiService {

    override suspend fun getGifs(apiKey: String, limit: Int): GiphyResponse {
        return apiService.getGifs(apiKey, limit)
    }

    override suspend fun searchGif(apiKey: String, query: String, limit: Int): GiphyResponse {
        return apiService.searchGif(apiKey, query, limit)
    }
}