package com.ovrbach.mvolvochallenge.model.mapper

import com.ovrbach.mvolvochallenge.model.dto.TracksDTO
import com.ovrbach.mvolvochallenge.model.entity.Track
import javax.inject.Inject

class TrackMapper @Inject constructor(
    private val artistMapper: ArtistMapper
) {
    fun toDomain(item: TracksDTO.TrackShort) =
        Track(
            artists = item.artists.map { artistMapper.toDomain(it) },
            durationsString = toMinutesAndSeconds(item.duration_ms),
            id = item.id,
            name = item.name,
            previewUrl = item.preview_url,
            trackNumber = item.track_number,
            uri = item.uri
        )

    private fun toMinutesAndSeconds(millis: Int): String {
        val seconds = (millis / 1000 % 60)
        val minutes = (millis / 1000 / 60)
        return "$minutes:$seconds"
    }
}