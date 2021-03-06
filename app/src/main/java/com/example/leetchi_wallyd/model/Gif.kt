package com.example.leetchi_wallyd.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Gif(
    var id: String,
    var title: String,
    var images: GifImages

): Parcelable
