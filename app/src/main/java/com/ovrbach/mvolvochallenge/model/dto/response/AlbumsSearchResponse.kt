package com.ovrbach.mvolvochallenge.model.dto.response

import com.ovrbach.mvolvochallenge.model.dto.AlbumShortDTO


data class AlbumsSearchResponse(
    val albums: Albums
) {
    data class Albums(
        val href: String,
        val items: List<AlbumShortDTO>,
        val total: Long,
        val next: String,
        val offset: Int
    )
}
