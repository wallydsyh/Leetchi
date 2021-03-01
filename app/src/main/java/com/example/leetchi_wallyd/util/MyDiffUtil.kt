package com.example.leetchi_wallyd.util

import androidx.recyclerview.widget.DiffUtil
import com.example.leetchi_wallyd.model.Gif

class MyDiffUtil : DiffUtil.ItemCallback<Gif>() {
    override fun areItemsTheSame(oldItem: Gif, newItem: Gif): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Gif, newItem: Gif): Boolean {
        return oldItem == newItem
    }

}
