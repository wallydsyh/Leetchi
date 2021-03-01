package com.example.leetchi_wallyd.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.leetchi_wallyd.R
import com.example.leetchi_wallyd.adapter.GiphyAdapter
import com.example.leetchi_wallyd.databinding.FragmentFirstBinding
import com.example.leetchi_wallyd.model.Gif
import com.example.leetchi_wallyd.util.Constant
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
                        giphyViewModel.getSearchedGifs(
                            newText.toString()
                        )
                    }
                }
            }
            return true
        }
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
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = giphyAdapter
            giphyAdapter.onGifClick = {
                Bundle().apply {
                    this.putParcelable(Constant.GIF, it)
                    findNavController().navigate(
                        R.id.action_FirstFragment_to_fullscreenFragment,
                        this
                    )
                }
            }
        }
    }

    private fun setUpObserver() {
        giphyViewModel.allGifs.observe(viewLifecycleOwner, {
            gifList.addAll(it.data)
            giphyAdapter.submitList(it.data)
        })

        giphyViewModel.searchedGifs.observe(viewLifecycleOwner, {
            giphyAdapter.submitList(it.data)
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