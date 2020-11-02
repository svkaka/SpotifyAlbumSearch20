package com.ovrbach.mvolvochallenge.model.entity

data class Track(
    val artists: List<Artist>,
    val durationMs: Int,
    val id: String,
    val name: String,
    val previewUrl: String?,
    val trackNumber: Int,
    val uri: String
)