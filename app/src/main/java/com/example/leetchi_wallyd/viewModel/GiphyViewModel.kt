package com.example.leetchi_wallyd.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leetchi_wallyd.model.GifLoadingState
import com.example.leetchi_wallyd.model.GiphyResponse
import com.example.leetchi_wallyd.repository.GiphyRepository
import com.example.leetchi_wallyd.util.Resource
import kotlinx.coroutines.launch

open class GiphyViewModel(private val repository: GiphyRepository) : ViewModel() {

    var allGifs = MutableLiveData<Resource<GiphyResponse>>()
    var searchedGifs = MutableLiveData<Resource<GiphyResponse>>()
    val gifLoadingStateLiveData = MutableLiveData<GifLoadingState>()

    suspend fun getGifs() {
        viewModelScope.launch {
            gifLoadingStateLiveData.postValue(GifLoadingState.LOADING)
            repository.getGifs().run {
                try {
                    allGifs.postValue(Resource.Success(this))
                    gifLoadingStateLiveData.postValue(GifLoadingState.LOADED)

                } catch (exception: Exception) {
                    allGifs.postValue(Resource.Error(exception))
                    gifLoadingStateLiveData.postValue(GifLoadingState.ERROR)
                }
            }
        }
    }

    suspend fun getSearchedGifs(query: String) {
        viewModelScope.launch {
            gifLoadingStateLiveData.postValue(GifLoadingState.LOADING)
            repository.searchGifs(query).run {
                try {
                    searchedGifs.postValue(Resource.Success(this))
                    gifLoadingStateLiveData.postValue(GifLoadingState.LOADED)
                } catch (exception: Exception) {
                    searchedGifs.postValue(Resource.Error(exception))
                    gifLoadingStateLiveData.postValue(GifLoadingState.ERROR)
                }
            }
        }
    }
}