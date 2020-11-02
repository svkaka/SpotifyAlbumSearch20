package com.ovrbach.mvolvochallenge.model.entity

data class AlbumItem(
    val id: String,
    val name: String,
    val releaseDate: String,
    val type: String,
    val uri: String,
    val artists: List<Artist>,
    val images: List<Image>
) {
    data class Artist(
        val name: String,
        val uri: String
    )

    data class Image(
        val height: Int,
        val url: String
    )
}
