package com.ovrbach.mvolvochallenge.model.entity

data class AlbumDetails(
    val albumType: String,
    val artists: List<Artist>,
    val images: List<Image>,
    val label: String,
    val name: String,
    val releaseDate: String,
    val tracks: List<Track>,
    val uri: String
)
