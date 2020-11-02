package com.ovrbach.mvolvochallenge.model.dto.search


data class AlbumsSearchResponse(
    val albums: Albums
) {
    data class Albums(
        val href: String,
        val items: List<AlbumShort>,
        val total: Long
    ) {
        data class AlbumShort(
            val album_type: String,
            val artists: List<Artist>,
            val href: String,
            val id: String,
            val images: List<Image>,
            val name: String,
            val release_date: String,
            val type: String,
            val uri: String
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
    }
}
