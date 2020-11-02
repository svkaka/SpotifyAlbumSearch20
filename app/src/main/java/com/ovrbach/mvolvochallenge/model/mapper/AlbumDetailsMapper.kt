package com.ovrbach.mvolvochallenge.model.mapper

import com.ovrbach.mvolvochallenge.model.dto.response.AlbumDetailsResponse
import com.ovrbach.mvolvochallenge.model.entity.AlbumDetails
import javax.inject.Inject

class AlbumDetailsMapper @Inject constructor(
    private val artistMapper: ArtistMapper,
    private val imageMapper: ImageMapper,
    private val trackMapper: TrackMapper
) {
    fun toDomain(albumDetailsResponse: AlbumDetailsResponse): AlbumDetails = AlbumDetails(
        albumType = albumDetailsResponse.album_type,
        artists = albumDetailsResponse.artists.map { artistMapper.toDomain(it) },
        images = albumDetailsResponse.images.map { imageMapper.toDomain(it) },
        label = albumDetailsResponse.label,
        name = albumDetailsResponse.name,
        releaseDate = albumDetailsResponse.release_date,
        tracks = albumDetailsResponse.tracks.items.map { trackMapper.toDomain(it) }.sortedBy { it.trackNumber },
        uri = albumDetailsResponse.uri
    )
}
