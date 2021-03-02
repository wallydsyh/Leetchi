package com.example.leetchi_wallyd.model

import coil.map.Mapper

class GifMapper: Mapper<Gif, String> {
    override fun map(data: Gif) = data.images.preview_gif.url
}