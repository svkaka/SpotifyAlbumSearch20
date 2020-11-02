package com.ovrbach.mvolvochallenge.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
    val height: Int,
    val url: String
) : Parcelable