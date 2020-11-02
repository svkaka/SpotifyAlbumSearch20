package com.ovrbach.mvolvochallenge.model.dto

data class TracksDTO(
    val items: List<TrackShort>
) {

    class TrackShort(
        val artists: List<ArtistDTO>,
        val duration_ms: Int,
        val id: String,
        val name: String,
        val preview_url: String?,
        val track_number: Int,
        val uri: String
    )
}