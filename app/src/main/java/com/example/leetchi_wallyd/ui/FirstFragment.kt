package com.example.leetchi_wallyd.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
import com.example.leetchi_wallyd.app.App.Companion.application
import com.example.leetchi_wallyd.R
import com.example.leetchi_wallyd.adapter.GiphyAdapter
import com.example.leetchi_wallyd.utilities.ConnectivityLiveData
import com.example.leetchi_wallyd.databinding.FragmentFirstBinding
import com.example.leetchi_wallyd.model.Gif
import com.example.leetchi_wallyd.utilities.Constant
import com.example.leetchi_wallyd.utilities.Resource
import com.example.leetchi_wallyd.viewModel.GiphyViewModel
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private val giphyViewModel: GiphyViewModel by activityViewModels()
    private var giphyAdapter = GiphyAdapter()
    private lateinit var binding: FragmentFirstBinding
    private var gifList = ArrayList<Gif>()
    private lateinit var connectivityLiveData: ConnectivityLiveData
    private lateinit var mainActivity: MainActivity

    private var searchCallback = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            lifecycleScope.launch {
                when {
                    newText.toString().isBlank() -> {
                        giphyAdapter.submitList(gifList)
                    }
                    else -> {
                        if (connectivityLiveData.value == true) {
                            giphyViewModel.getSearchedGifs(newText.toString())
                        }
                    }
                }
            }
            return true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityLiveData = ConnectivityLiveData(application)
        mainActivity = activity as MainActivity

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        setUpListener()
        setUpObserver()
        return binding.root

    }

    private fun setUpListener() {
        binding.recyclerView.apply {
            val staggeredGridLayoutManager =
                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            staggeredGridLayoutManager.gapStrategy = GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            layoutManager = staggeredGridLayoutManager
            adapter = giphyAdapter
            giphyAdapter.onGifClick = {
                displayGifFullScreen(it)
            }
        }
    }

    private fun displayGifFullScreen(gif: Gif) {
        Bundle().apply {
            this.putParcelable(Constant.GIF, gif)
            findNavController().navigate(
                R.id.action_FirstFragment_to_fullscreenFragment,
                this
            )
        }
    }

    private fun setUpObserver() {
        giphyViewModel.allGifs.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    gifList.addAll(it.data.data)
                    giphyAdapter.submitList(it.data.data)
                }
                is Resource.Error -> Toast.makeText(context, getString(R.string.error), Toast.LENGTH_LONG)
                    .show()
            }

        })

        giphyViewModel.searchedGifs.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> giphyAdapter.submitList(it.data.data)
                is Resource.Error -> Toast.makeText(context, getString(R.string.error), Toast.LENGTH_LONG)
                    .show()
            }
        })

        giphyViewModel.gifLoadingStateLiveData.observe(viewLifecycleOwner, {
            mainActivity.onMovieLoadingStateChanged(binding.loadingIndicator, it)
        })

        connectivityLiveData.observe(viewLifecycleOwner, { isAvailable ->
            when (isAvailable) {
                true -> {
                    if (giphyViewModel.allGifs.value == null) {
                        mainActivity.getAllGif()
                    }
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.statusButton.visibility = View.GONE

                }
                false -> {
                    binding.statusButton.visibility = View.VISIBLE
                    context?.let {
                        binding.statusButton.setCompoundDrawables(
                            null, ContextCompat.getDrawable(it, R.drawable.no_internet), null,
                            null
                        )
                    }
                    binding.recyclerView.visibility = View.GONE
                    binding.loadingIndicator.visibility = View.GONE
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        // Get the SearchView and set the searchable configuration
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.menu_search).actionView as SearchView).apply {
            // Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
            setIconifiedByDefault(false)
            setOnQueryTextListener(searchCallback)
        }
    }

}