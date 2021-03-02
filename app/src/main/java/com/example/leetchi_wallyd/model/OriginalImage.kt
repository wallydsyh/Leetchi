package com.example.leetchi_wallyd.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class OriginalImage(
    var url: String,
    var webp: String
): Parcelable

@Parcelize
class Preview(
    var url: String,
): Parcelable
