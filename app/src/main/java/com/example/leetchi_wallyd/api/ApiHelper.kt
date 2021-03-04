package com.example.leetchi_wallyd.api

import com.example.leetchi_wallyd.utilities.Constant


open class ApiHelper(private val apiService: ApiService) {
    suspend fun getGifs() = apiService.getGifs(Constant.API_KEY, Constant.LIMIT)
    suspend fun searchGif(query: String) =
        apiService.searchGif(Constant.API_KEY, query, Constant.LIMIT)

}