package com.example.leetchi_wallyd.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class GifImages(
    var original: OriginalImage,
    var preview_gif: Preview
): Parcelable


