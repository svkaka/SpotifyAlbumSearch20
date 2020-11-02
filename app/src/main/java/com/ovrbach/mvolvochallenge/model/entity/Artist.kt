package com.ovrbach.mvolvochallenge.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Artist(
    val name: String,
    val uri: String
) : Parcelable