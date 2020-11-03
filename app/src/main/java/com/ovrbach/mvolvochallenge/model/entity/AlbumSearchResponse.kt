package com.ovrbach.mvolvochallenge.model.entity

data class AlbumSearchResponse(
        val albums: List<AlbumItem>,
        val next: Int?,
        val offset: Int
)