package com.ovrbach.mvolvochallenge.model.mapper

import com.ovrbach.mvolvochallenge.model.dto.search.AlbumsSearchResponse
import com.ovrbach.mvolvochallenge.model.entity.AlbumItem
import javax.inject.Inject

class AlbumResponseMapper @Inject constructor(
    private val artistMapper: ArtistMapper,
    private val imageMapper: ImageMapper
) {

    fun toDomain(albumsSearchResponse: AlbumsSearchResponse): List<AlbumItem> =
        albumsSearchResponse.albums.items.map { album ->
            AlbumItem(
                id = album.id,
                name = album.name,
                releaseDate = album.release_date,
                type = album.type,
                uri = album.uri,
                artists = album.artists.map { artist -> artistMapper.toDomain(artist) },
                images = album.images.map { image -> imageMapper.toDomain(image) }
            )
        }
}

