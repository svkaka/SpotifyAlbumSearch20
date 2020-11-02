package com.ovrbach.mvolvochallenge.model.dto

data class AlbumShortDTO(
    val album_type: String,
    val artists: List<ArtistDTO>,
    val href: String,
    val id: String,
    val images: List<ImageDTO>,
    val name: String,
    val release_date: String,
    val type: String,
    val uri: String
)