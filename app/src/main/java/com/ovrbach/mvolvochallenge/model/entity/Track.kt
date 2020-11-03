package com.ovrbach.mvolvochallenge.model.entity

data class Track(
    val artists: List<Artist>,
    val durationsString: String,
    val id: String,
    val name: String,
    val previewUrl: String?,
    val trackNumber: Int,
    val uri: String
)