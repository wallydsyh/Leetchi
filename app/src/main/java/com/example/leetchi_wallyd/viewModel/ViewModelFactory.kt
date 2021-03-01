package com.example.leetchi_wallyd.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.leetchi_wallyd.api.ApiHelper
import com.example.leetchi_wallyd.repository.GiphyRepository

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(GiphyViewModel::class.java) -> {
                return GiphyViewModel(GiphyRepository(apiHelper)) as T
            }
            else -> throw IllegalArgumentException("Unknown class name")
        }
    }

}