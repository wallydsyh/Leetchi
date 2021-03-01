package com.example.leetchi_wallyd.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leetchi_wallyd.model.GiphyResponse
import com.example.leetchi_wallyd.repository.GiphyRepository
import kotlinx.coroutines.launch

class GiphyViewModel(private val repository: GiphyRepository): ViewModel(){

    var allGifs = MutableLiveData<GiphyResponse>()
    var searchedGifs = MutableLiveData<GiphyResponse>()

    suspend fun getGifs() {
        viewModelScope.launch {
            repository.getGifs().run {
                allGifs.postValue(this)
            }
        }
    }

    suspend fun getSearchedGifs(query: String) {
        viewModelScope.launch {
            repository.searchGifs(query).run {
                searchedGifs.postValue(this)
            }
        }
    }
}