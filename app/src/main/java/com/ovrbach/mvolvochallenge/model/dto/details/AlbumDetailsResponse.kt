package com.ovrbach.mvolvochallenge.model.dto.details

data class AlbumDetailsResponse(
    val album_type: String,
    val artists: List<Artist>,
    val images: List<Image>,
    val label: String,
    val name: String,
    val release_date: String,
    val tracks: Tracks,
    val uri: String
) {
    data class Tracks(
        val items: List<TrackShort>
    ) {
        class TrackShort(
            val artists: List<Artist>,
            val duration_ms: Int,
            val id: String,
            val name: String,
            val preview_url: String?,
            val track_number: Int,
            val uri: String
        )
    }

    data class Artist(
        val name: String,
        val uri: String
    )

    data class Image(
        val height: Int,
        val url: String
    )
}