package com.example.leetchi_wallyd.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Gif(
    var type: String,
    var id: String,
    var username: String,
    var title: String,
    var images: GifImages

): Parcelable
