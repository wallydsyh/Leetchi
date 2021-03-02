package com.example.leetchi_wallyd.repository

import com.example.leetchi_wallyd.api.ApiHelper
import com.example.leetchi_wallyd.model.GiphyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class GiphyRepository(private val apiHelper: ApiHelper) {

    suspend fun getGifs(): GiphyResponse {
        return withContext(Dispatchers.IO) {
            apiHelper.getGifs()
        }
    }

    suspend fun searchGifs(query: String): GiphyResponse {
        return withContext(Dispatchers.IO) {
            apiHelper.searchGif(query)
        }
    }
}