package com.example.leetchi_wallyd.mapper

import coil.map.Mapper
import com.example.leetchi_wallyd.model.Gif

class GifMapper: Mapper<Gif, String> {
    override fun map(data: Gif) = data.images.preview_gif.url
}