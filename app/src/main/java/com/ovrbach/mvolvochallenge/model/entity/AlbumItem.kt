package com.ovrbach.mvolvochallenge.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlbumItem(
    val id: String,
    val name: String,
    val releaseDate: String,
    val type: String,
    val uri: String,
    val artists: List<Artist>,
    val images: List<Image>
) : Parcelable {


}
