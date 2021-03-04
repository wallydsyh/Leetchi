package com.example.leetchi_wallyd.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.leetchi_wallyd.R
import com.example.leetchi_wallyd.api.ApiHelper
import com.example.leetchi_wallyd.api.ApiServiceImpl
import com.example.leetchi_wallyd.databinding.ActivityMainBinding
import com.example.leetchi_wallyd.model.GifLoadingState
import com.example.leetchi_wallyd.viewModel.GiphyViewModel
import com.example.leetchi_wallyd.viewModel.ViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var giphyViewModel: GiphyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        setUpViewModel()
    }

    private fun setUpViewModel() {
        giphyViewModel = ViewModelProvider(this, ViewModelFactory(ApiHelper(ApiServiceImpl()))).get(
            GiphyViewModel::class.java
        )
    }

    fun getAllGif() {
        lifecycleScope.launch {
            giphyViewModel.getGifs()
        }
    }


    fun onMovieLoadingStateChanged(loadingIndicator: ProgressBar, state: GifLoadingState) {
        when (state) {
            GifLoadingState.LOADING -> loadingIndicator.visibility = View.VISIBLE
            GifLoadingState.ERROR -> loadingIndicator.visibility = View.GONE
            GifLoadingState.LOADED -> loadingIndicator.visibility = View.GONE
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}