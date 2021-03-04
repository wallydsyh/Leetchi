package com.example.leetchi_wallyd.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import coil.request.ImageRequest
import com.example.leetchi_wallyd.databinding.GifItemBinding
import com.example.leetchi_wallyd.model.Gif
import com.example.leetchi_wallyd.utilities.MyDiffUtil

class GiphyAdapter :
    androidx.recyclerview.widget.ListAdapter<Gif, GiphyAdapter.GiphyViewHolder>(MyDiffUtil()) {

    private lateinit var binding: GifItemBinding
    var onGifClick: ((Gif) -> Unit)? = null

    class GiphyViewHolder(private var binding: GifItemBinding, onGifClick: ((Gif) -> Unit)?) :
        RecyclerView.ViewHolder(binding.root) {
        private val context: Context = binding.root.context
        private val imageLoader = binding.imageView.context.imageLoader
        private var gif: Gif? = null

        init {
            binding.root.setOnClickListener {
                gif?.let { it1 -> onGifClick?.invoke(it1) }
            }
        }

        fun bindData(gif: Gif) {
            this.gif = gif
            val request = ImageRequest.Builder(context)
                .data(gif)
                .target(binding.imageView)
                .build()
            imageLoader.enqueue(request)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GiphyViewHolder {
        binding = GifItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GiphyViewHolder(binding, onGifClick)

    }

    override fun onBindViewHolder(holder: GiphyViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}